#!/usr/bin/python
# deploy the WAR file to a local TOMCAT, requiring
# TOMCAT_HOME to be defined

import os
import platform
import shutil
import sys

tomcat_home = os.environ['TOMCAT_HOME']
pltfrm = platform.system()


def _stop_local_tomcat():
    global tomcat_home, pltfrm
    if pltfrm == 'Windows':
        os.system("{0}/bin/shutdown.bat".format(tomcat_home).replace('/', '\\'))
    else:
        os.system("{0}/bin/shutdown.sh".format(tomcat_home))


def _start_local_tomcat():
    global tomcat_home, pltfrm
    if pltfrm == 'Windows':
        os.system("{0}/bin/startup.bat".format(tomcat_home).replace('/', '\\'))
    else:
        os.system("{0}/bin/startup.sh".format(tomcat_home))


if tomcat_home is None:
    print("TOMCAT_HOME is not defined")
    sys.exit(1)
war_name = 'cmdq.war'
war_file = 'target/{0}'.format(war_name)

if not os.path.isfile(war_file):
    print("WAR file does not exist")
    sys.exit(2)

shutil.rmtree('{0}/webapps/cmdq'.format(tomcat_home), ignore_errors=True)
shutil.copyfile(war_file, '{0}/webapps/{1}'.format(tomcat_home, war_name))
print("WAR file copied")

