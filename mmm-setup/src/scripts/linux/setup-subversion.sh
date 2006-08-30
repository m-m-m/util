#!/usr/bin/env bash
#$Id$

cd `dirname "${0}"`
. include.inc
cd ../../settings/subversion
SVN_DIR=~/.subversion/

if [ ! -d "${SVN_DIR}" ]
then
  echo "Creating subversion configuration directory ${SVN_DIR}"
  mkdir -p "${SVN_DIR}"
fi

SVN_CFG=${SVN_DIR}/config
MMM_CFG=${PWD}/config
if [ -e "${SVN_CFG}" ]
then
  if [ -L "${SVN_CFG}" ]
  then
    LINK=`readlink "${SVN_CFG}"`
    if [ "${LINK}" == "${MMM_CFG}" ]
    then
      echo "Subversion already set up"
      exit 0
    fi
  fi
  echo "Renaming subversion config to config.bak"
  mv "${SVN_CFG}" "${SVN_CFG}.bak"
fi

echo "creating symlink: ln -s ${MMM_CFG} ${SVN_CFG}"
ln -s "${MMM_CFG}" "${SVN_CFG}"
echo "Subversion successfully setup for mmm."
