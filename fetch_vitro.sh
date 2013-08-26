#!/bin/bash

set -e

VITRO_VERSION=${1}
VIVO_BUILD_DIR=${2}
VITRO_BUILD_DIR=${3}
SCRIPT_PATH=${4}

VITRO_TARBALL="https://github.com/downloads/vivo-project/Vitro/vitro-rel-${VITRO_VERSION}.tar.gz"

mkdir -p ${VITRO_BUILD_DIR}
pushd ${VITRO_BUILD_DIR} 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err
curl -L ${VITRO_TARBALL} 2>>${SCRIPT_PATH}/vivo-installer.err | tar --strip-components=1 -xzf -
for PATCH_FILE in ${VIVO_BUILD_DIR}/patches/vitro-${VITRO_VERSION}/*.patch
do  
  patch -p1 < ${PATCH_FILE}
done  
