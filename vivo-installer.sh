#!/bin/bash

set -e

DB_CLEAN=false
LEAVE_BUILD_DIR=false

while getopts ':DL' OPT
do
  case ${OPT} in
    D)
      DB_CLEAN=true
      ;;
    L)
      LEAVE_BUILD_DIR=true
      ;;
  esac
done

if [ -e vivo-installer.conf ]
then
  . vivo-installer.conf
fi

SCRIPT_PATH=$(pwd)

BUILD_DIR=${BUILD_DIR:=$(mktemp -d)}

VITRO_DB="${VITRO_DB:=vivo}"
VITRO_DB_USERNAME="${VITRO_DB_USERNAME:=vivo}"
VITRO_DB_PASSWORD="${VITRO_DB_PASSWORD:=vivo}"

TOMCAT_USER="${TOMCAT_USER:=tomcat6}"
TOMCAT_GROUP="${TOMCAT_GROUP:=tomcat6}"

VITRO_VERSION="${VITRO_VERSION:=1.4.1}"

VIVO_URL="${VIVO_URL:=http://localhost}"

WWW_DIR="${WWW_DIR:=/var/www}"

echo $(date +%s%N | cut -b1-13) "- starting"

START_TIME=$(date +%s%N | cut -b1-13)
if ${DB_CLEAN}
then
  echo "${START_TIME} - preparing database"
  ${SCRIPT_PATH}/prepare_database.sh ${VITRO_DB} ${VITRO_DB_USERNAME} ${VITRO_DB_PASSWORD} ${MYSQL_ADMIN_USER} ${MYSQL_ADMIN_PASSWORD}
fi

VIVO_GIT="${VIVO_GIT:=git://github.com/UniMelb-source/vivo.git}"
VIVO_GIT_BRANCH="${VIVO_GIT_BRANCH:=vivo-crdr}"
VIVO_BUILD_DIR=${BUILD_DIR}/vivo
VITRO_BUILD_DIR=${BUILD_DIR}/vitro

NETWORK_START_TIME=$(date +%s%N | cut -b1-13)
echo "${NETWORK_START_TIME} - cloning VIVO"
${SCRIPT_PATH}/clone_vivo.sh ${VIVO_GIT} ${VIVO_BUILD_DIR} ${SCRIPT_PATH} ${VIVO_GIT_BRANCH}

echo $(date +%s%N | cut -b1-13) "- fetching VITRO"
${SCRIPT_PATH}/fetch_vitro.sh ${VITRO_VERSION} ${VIVO_BUILD_DIR} ${VITRO_BUILD_DIR} ${SCRIPT_PATH}

# Prepare VIVO properties
ADMIN_EMAIL="${ADMIN_EMAIL:=tsullivan@unimelb.edu.au}"
VITRO_DB_URL="${VITRO_DB_URL:=jdbc:mysql://localhost/${VITRO_DB}}"
TOMCAT_HOME="${TOMCAT_HOME:=/var/lib/tomcat6}"
VITRO_HOME="${VITRO_HOME:=/usr/local/vivo/data}"
VIVO_LOG_DIR="${VIVO_LOG_DIR:=/usr/share/tomcat6/logs}"

NETWORK_END_TIME=$(date +%s%N | cut -b1-13)
echo "${NETWORK_END_TIME} - preparing settings"
cp ${VIVO_BUILD_DIR}/example.deploy.properties ${VIVO_BUILD_DIR}/deploy.properties
sed -i -e "s#^vitro.core.dir = .*\$#vitro.core.dir = ${VITRO_BUILD_DIR}#g" ${VIVO_BUILD_DIR}/deploy.properties
sed -i -e "s#^tomcat.home = .*\$#tomcat.home = ${TOMCAT_HOME}#g" ${VIVO_BUILD_DIR}/deploy.properties
sed -i -e "s#^VitroConnection.DataSource.url = .*\$#VitroConnection.DataSource.url = ${VITRO_DB_URL}#g" ${VIVO_BUILD_DIR}/deploy.properties
sed -i -e "s#^VitroConnection.DataSource.username = .*\$#VitroConnection.DataSource.username = ${VITRO_DB_USERNAME}#g" ${VIVO_BUILD_DIR}/deploy.properties
sed -i -e "s#^VitroConnection.DataSource.password = .*\$#VitroConnection.DataSource.password = ${VITRO_DB_PASSWORD}#g" ${VIVO_BUILD_DIR}/deploy.properties
sed -i -e "s#^rootUser.emailAddress = .*\$#rootUser.emailAddress = ${ADMIN_EMAIL}#g" ${VIVO_BUILD_DIR}/deploy.properties

# Pre-build setup
mkdir -p ${VITRO_HOME}
mkdir -p ${VIVO_LOG_DIR}
touch ${VIVO_LOG_DIR}/vivo.all.log

pushd ${VIVO_BUILD_DIR} 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err
echo $(date +%s%N | cut -b1-13) "- cleaning and deploying"
ant clean deploy 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err

touch /etc/authbind/byport/{80,443} 2>>${SCRIPT_PATH}/vivo-installer.err
chown ${TOMCAT_USER} /etc/authbind/byport/{80,443} 2>>${SCRIPT_PATH}/vivo-installer.err
chmod 775 /etc/authbind/byport/{80,443} 2>>${SCRIPT_PATH}/vivo-installer.err

sed -i -e "s/^#AUTHBIND=no/AUTHBIND=yes/g" /etc/default/tomcat6

# Post-build setup
chown -R ${TOMCAT_USER}:${TOMCAT_GROUP} ${VITRO_HOME} 2>>${SCRIPT_PATH}/vivo-installer.err
chown -R ${TOMCAT_USER}:${TOMCAT_GROUP} ${VIVO_LOG_DIR} 2>>${SCRIPT_PATH}/vivo-installer.err
echo $(date +%s%N | cut -b1-13) "- restarting Tomcat"
/etc/init.d/tomcat6 restart 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err

echo $(date +%s%N | cut -b1-13) "- deploying LDAP login"
${SCRIPT_PATH}/deploy_ldap_login.sh ${WWW_DIR} ${VIVO_BUILD_DIR} ${SCRIPT_PATH}
/etc/init.d/apache2 restart 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err

# CLean up
dirs -c
if ! ${LEAVE_BUILD_DIR}
then
  rm -rf ${BUILD_DIR}
fi
echo $(date +%s%N | cut -b1-13) "- fetching homepage"
curl ${VIVO_URL} 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err
END_TIME=$(date +%s%N | cut -b1-13)
echo "${END_TIME} - done"
echo "$((END_TIME - START_TIME))ms elapsed"
echo "- $((NETWORK_START_TIME - START_TIME))ms setup"
echo "- $((NETWORK_END_TIME - NETWORK_START_TIME))ms network"
echo "- $((END_TIME - NETWORK_END_TIME))ms build and deploy"
