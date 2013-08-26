#!/bin/bash

set -e

WWW_DIR=${1}
VIVO_BUILD_DIR=${2}
SCRIPT_PATH=${3}

apt-get install -y libapache2-mod-jk libapache2-mod-php5 php5-ldap php5-curl

cp ${VIVO_BUILD_DIR}/ldap/ldaplogin.* ${WWW_DIR} 2>>${SCRIPT_PATH}/vivo-installer.err
