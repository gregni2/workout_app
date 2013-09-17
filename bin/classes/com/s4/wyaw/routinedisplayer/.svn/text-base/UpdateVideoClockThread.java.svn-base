/*
 * Copyright (c) 2009 OTAMate Technology Ltd. All Rights Reserved.
 * http://www.androidacademy.com
 */

package com.s4.wyaw.routinedisplayer;

import android.os.Handler;
import android.os.Message;

//
// Sends update messages.
// 
public class UpdateVideoClockThread implements Runnable {
    private VideoRoutineDisplayer routineDisplay_;

    UpdateVideoClockThread(VideoRoutineDisplayer routineDisplay) {
    	routineDisplay_ = routineDisplay;
    }

    int run = 0;
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
         
           	int msgType = RoutineDisplay.MSG_UPDATE;
            
        	try {
            	if (run == 0 ) {
            		Thread.sleep(5000);
            		msgType = RoutineDisplay.MSG_FIRST_UPDATE;
            		run++;
            	}
            	else if (run == 1) {
            		Thread.sleep(6000);
            		run++;
            	}
            	else {
            		Thread.sleep(1000);   		
            	}
            } catch (Exception e) {
            	return;
            }

            //
            // Update the counter
            //
            Handler handler = routineDisplay_.getHandler();

            //
            // Tell the main view it needs updating
            //
            Message msg = Message.obtain();
            msg.arg1 = msgType;
            handler.sendMessageDelayed(msg, msgType);
        }
    }
}
