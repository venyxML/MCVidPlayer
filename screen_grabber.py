#!/usr/bin/env python
# coding: utf-8

import numpy as np
import cv2
from mss import mss
from PIL import Image
import time
from os import path


def grab_frame():
    with mss() as sct:
        monitor_number = 2
        mon = sct.monitors[monitor_number]
        screen_dims = {'top': mon["top"] + 53, 'left': mon["left"] + 0, 'width': 958, 'height': 539}
        sct_img = sct.grab(screen_dims)
        
        w = 128+1
        h = 96+1

        img_corr = cv2.cvtColor(np.array(sct_img), cv2.COLOR_BGRA2RGB)
        resized = cv2.resize(img_corr,(w,h))
        resized = cv2.rotate(resized, cv2.ROTATE_90_CLOCKWISE)
        resized = cv2.flip(resized, 1)
        
        RGB_arr = np.empty(w*h, dtype=object)
        
        for x in range (0,w):
            for y in range (0,h):
                RGB_arr[x*h+y] = (str(resized[x,y][0]) + "," + str(resized[x,y][1])  + "," +  str(resized[x,y][2])+ "\n")
    
        output = open("./plugins/depot/frame.txt", "w")
        
        output.writelines(RGB_arr)

        output.close()

        cv2.imwrite("./plugins/depot/frame.png", resized)

key = "./plugins/depot/run.venyx"
frame = "./plugins/depot/frame.txt"

while(path.exists(key)):
    grab_frame()
    time.sleep(0.1)