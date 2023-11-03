#!/usr/bin/env python3

import argparse
import os
import shutil
import time
import subprocess as subp
from androguard.core.bytecodes.apk import APK

def parse_logs():
    with open('./whereareyou_logs.txt') as f:
        print(f.read())

def print_logs():
    f = open("whereareyou_logs.txt", "w")
    subp.call(["adb", "logcat", "-c"])
    try:
        subp.call(["adb", "logcat", "-s", "MOBIOTSEC"], stdout=f, timeout=5)
    except subp.TimeoutExpired:
        parse_logs()

def launch_app(apk):
    print("Launching the app")
    mainactivity = "{}/{}".format(apk.get_package(), apk.get_main_activity())
    os.system("adb shell am start -n {act}".format(act=mainactivity))

def uninstall(apk):
    if (os.system("adb shell pm list packages | grep {package}".format(package=apk.get_package())) == 0):
        print("Uninstalling the app")
        subp.call(["adb", "uninstall", apk.get_package()], stdout=subp.DEVNULL)

def install(apk):
    print("Installing the app")
    while True:
        try:
            os.system("adb install -g {apk}".format(apk=apk.get_filename()))
            break
        except subp.CalledProcessError as err:
            print('[!] install failed')
            print(err)
            print('[!] retrying')

def parse_args():
    parser = argparse.ArgumentParser()
    parser.add_argument("victimapp_apk_path", help="path to the victim app apk file")
    parser.add_argument("malapp_apk_path", help="path to the malicious app apk file")
    args = parser.parse_args()
    return args

def main(args):
    victimApk = APK(args.victimapp_apk_path)
    malApk = APK(args.malapp_apk_path)
    uninstall(victimApk)
    install(victimApk)
    uninstall(malApk)
    install(malApk)
    os.system("adb emu geo fix -88.45356 88.51119 4392")
    launch_app(victimApk)
    os.system("adb emu geo fix -50.45356 50.51119 4392")
    print_logs()

if __name__ == "__main__":
    main(parse_args())
