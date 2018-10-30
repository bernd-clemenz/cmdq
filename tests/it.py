#
# integration tests for cmdq
#

import requests
import configparser
import os

CFG = None
config_name = 'it.ini'


def setup_module(module):
    global CFG
    # 1. Configuration
    CFG = configparser.ConfigParser()

    if not os.path.isfile(config_name):
        raise Exception('Config file not found: ' + config_name)

    CFG.read(config_name)


def test_version():
    global CFG
    url = '{0}://{1}:{2}/cmdq/v1/internal/version'.format(CFG['cmdq']['scheme'],
                                                           CFG['cmdq']['host'],
                                                           CFG['cmdq']['port'])
