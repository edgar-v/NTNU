from skumleskogen2 import *
import sys
import time

def testNode():
    global nokler
    if er_nokkel():
        plukk_opp()
        nokler += 1
    elif er_stank():
        return False 
    elif er_superlaas() and nokler >= 2:
        nokler -= 2
        laas_opp()
    elif er_laas() and nokler >= 1:
        nokler -= 1
        laas_opp()
    elif er_utgang():
        gaa_ut()
        sys.exit()

def findUtgang():
    ret = testNode()
    if ret == False:
        gaa_tilbake()
        return
    if gaa_venstre() == True:
        findUtgang()
    if gaa_hoyre() == True:
        findUtgang()
    if gaa_venstre() == True:
        findUtgang()
    if gaa_hoyre() == True:
        findUtgang()
    gaa_tilbake()

nokler = 0
while True:
    findUtgang()
    print(er_inngang(), nokler)
    time.sleep(5)
