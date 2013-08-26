#!/bin/bash

set -e

VIVO_GIT=${1}
VIVO_BUILD_DIR=${2}
SCRIPT_PATH=${3}
VIVO_GIT_BRANCH=${4}

git clone ${VIVO_GIT} ${VIVO_BUILD_DIR} 1>${SCRIPT_PATH}/vivo-installer.log 2>${SCRIPT_PATH}/vivo-installer.err
pushd ${VIVO_BUILD_DIR} 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err
git checkout ${VIVO_GIT_BRANCH} 1>>${SCRIPT_PATH}/vivo-installer.log 2>>${SCRIPT_PATH}/vivo-installer.err
