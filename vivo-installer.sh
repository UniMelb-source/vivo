#!/bin/bash

if [ ${#} != 1 ]
then
	echo "Usage: ${BASH_SOURCE[0]} <mysql-password>"
	exit
fi

SCRIPT_PATH=$(pwd)

BUILD_DIR=$(mktemp -d)

MYSQL_ADMIN_USER="root"
MYSQL_ADMIN_PASSWORD=${1}

VITRO_DB="vivo"
VITRO_DB_USERNAME="vivo"
VITRO_DB_PASSWORD="vivo"

TOMCAT_USER="tomcat6"
TOMCAT_GROUP="tomcat6"

echo $(date +%s%N | cut -b1-13) "- starting"

START_TIME=$(date +%s%N | cut -b1-13)
echo "${START_TIME} - preparing database"
echo "DROP DATABASE IF EXISTS ${VITRO_DB}; CREATE DATABASE IF NOT EXISTS ${VITRO_DB}; GRANT ALL PRIVILEGES ON ${VITRO_DB}.* TO ${VITRO_DB_USERNAME}@localhost IDENTIFIED BY '${VITRO_DB_PASSWORD}';" | mysql --user=${MYSQL_ADMIN_USER} --password=${MYSQL_ADMIN_PASSWORD}

VIVO_GIT="git://github.com/UniMelb-source/vivo.git"
VIVO_GIT_BRANCH="vivo-crdr"
VIVO_DIR=${BUILD_DIR}/vivo

NETWORK_START_TIME=$(date +%s%N | cut -b1-13)
echo "${NETWORK_START_TIME} - cloning VIVO"
git clone ${VIVO_GIT} ${VIVO_DIR} 1>${SCRIPT_PATH}/vivo-installer.log 2>${SCRIPT_PATH}/vivo-installer.err
pushd ${VIVO_DIR} 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}vivo-installer.err
git checkout ${VIVO_GIT_BRANCH} 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err

echo $(date +%s%N | cut -b1-13) "- fetching VITRO"
VITRO_TARBALL="https://github.com/downloads/vivo-project/Vitro/vitro-rel-1.4.1.tar.gz"
VITRO_DIR=${BUILD_DIR}/vitro

mkdir -p ${VITRO_DIR}
pushd ${VITRO_DIR} 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err
curl -L ${VITRO_TARBALL} 2>>${SCRIPT_PATH}/vivo-installer.err | tar --strip-components=1 -xzf -

# Prepare VIVO properties
ADMIN_EMAIL="tsullivan@unimelb.edu.au"
VITRO_DB_URL="jdbc:mysql://localhost/${VITRO_DB}"
TOMCAT_HOME="/var/lib/tomcat6"
VITRO_HOME="/usr/local/vivo/data"
VIVO_LOG_DIR="/usr/share/tomcat6/logs"

NETWORK_END_TIME=$(date +%s%N | cut -b1-13)
echo "${NETWORK_END_TIME} - preparing settings"
cp ${VIVO_DIR}/example.deploy.properties ${VIVO_DIR}/deploy.properties
sed -i -e "s#^vitro.core.dir = .*\$#vitro.core.dir = ${VITRO_DIR}#g" ${VIVO_DIR}/deploy.properties
sed -i -e "s#^tomcat.home = .*\$#tomcat.home = ${TOMCAT_HOME}#g" ${VIVO_DIR}/deploy.properties
sed -i -e "s#^VitroConnection.DataSource.url = .*\$#VitroConnection.DataSource.url = ${VITRO_DB_URL}#g" ${VIVO_DIR}/deploy.properties
sed -i -e "s#^VitroConnection.DataSource.username = .*\$#VitroConnection.DataSource.username = ${VITRO_DB_USERNAME}#g" ${VIVO_DIR}/deploy.properties
sed -i -e "s#^VitroConnection.DataSource.password = .*\$#VitroConnection.DataSource.password = ${VITRO_DB_PASSWORD}#g" ${VIVO_DIR}/deploy.properties
sed -i -e "s#^rootUser.emailAddress = .*\$#rootUser.emailAddress = ${ADMIN_EMAIL}#g" ${VIVO_DIR}/deploy.properties

# Pre-build setup
mkdir -p ${VITRO_HOME}
mkdir -p ${VIVO_LOG_DIR}
touch ${VIVO_LOG_DIR}/vivo.all.log

pushd ${VIVO_DIR} 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err
echo $(date +%s%N | cut -b1-13) "- cleaning and deploying"
ant clean deploy 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err

# Post-build setup
chown -R ${TOMCAT_USER}:${TOMCAT_GROUP} ${VITRO_HOME}
chown -R ${TOMCAT_USER}:${TOMCAT_GROUP} ${VIVO_LOG_DIR}
echo $(date +%s%N | cut -b1-13) "- restarting Tomcat"
/etc/init.d/tomcat6 restart 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err

# CLean up
dirs -c
rm -rf ${BUILD_DIR}
echo $(date +%s%N | cut -b1-13) "- fetching homepage"
curl http://localhost:8080/vivo 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err
END_TIME=$(date +%s%N | cut -b1-13)
echo "${END_TIME} - done"
echo "$((END_TIME - START_TIME))ms elapsed"
echo "- $((NETWORK_START_TIME - START_TIME))ms setup"
echo "- $((NETWORK_END_TIME - NETWORK_START_TIME))ms network"
echo "- $((END_TIME - NETWORK_END_TIME))ms build and deploy"
