import os
import pyautogui as pe
import time
import subprocess
''''
os.startfile('C:/Program Files (x86)/hide.me VPN/Hide.me.exe')
time.sleep(2)
pe.moveTo(1542,1053,2)
pe.click(1542,1053)
time.sleep(2)

pe.moveTo(1463,901,2)
pe.click(1463,901,2)


x, y = pe.locateCenterOnScreen('./active.PNG')
pe.click(x, y,1)
'''


### clic on all process
pointOfProsses = pe.locateCenterOnScreen('./Allprocess.PNG')
pe.click(pointOfProsses.x, pointOfProsses.y,1)

#### show process

hideProcessOff = pe.locateCenterOnScreen('./botoff1.png')
hideProcessOn = pe.locateCenterOnScreen('./boton1.png')

if hideProcessOff is None : 
    pe.click(hideProcessOn.x, hideProcessOn.y,2)
    active = pe.locateCenterOnScreen('./active.PNG')
    desactive = pe.locateCenterOnScreen('./desactive.PNG')
    if active is None :
       pe.click(desactive.x, desactive.y,1)
       time.sleep(5)
       print("hello2")
       pe.moveTo(1463,901,2)
       active2 = pe.locateCenterOnScreen('./active.PNG')
       pe.click(active2.x, active2.y,1)
       time.sleep(5)
    else :
        print("hello")
        pe.click(active.x, active.y,1)
        time.sleep(5)

else :
    pe.click(hideProcessOff.x, hideProcessOff.y,2)
    active = pe.locateCenterOnScreen('./active.PNG')
    desactive = pe.locateCenterOnScreen('./desactive.PNG')
    if active is None :
       pe.click(desactive.x, desactive.y,1)
       time.sleep(5)
       pe.moveTo(1463,901,2)
       active2 = pe.locateCenterOnScreen('./active.PNG')
       pe.click(active2.x, active2.y,1)
       time.sleep(5)
    else :
        pe.click(active.x, active.y,1)
        time.sleep(5)

         




