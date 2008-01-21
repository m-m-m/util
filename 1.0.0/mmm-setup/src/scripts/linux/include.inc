#$Id: $

# This file is included to activate the custom settings

MMM_BASE=../../../..
if [ -x settings.sh ]
then
  ./settings.sh
else
  if [ ! -f settings.sh ]
  then
    cp settings.sh.template settings.sh
  fi
  chmod a+x settings.sh
  echo "Welcome to the Multi-Media-Manager project!"
  echo "This seems to be the first time you invoked a setup script."
  echo "The file ${PWD}/settings.sh has been created for you."
  echo "Please edit the file now and make your individual changes as needed."
  echo "Now checking your installation..."
  echo "If this fails restart ${PWD}/check-installation.sh after fixing your settings.sh"
  ./check-installation.sh
  exit ${?}
fi
