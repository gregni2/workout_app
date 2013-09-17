/*
 * Copyright (c) 2009 OTAMate Technology Ltd. All Rights Reserved.
 * http://www.androidacademy.com
 */

package com.s4.wyaw.routinedisplayer;
import android.os.Handler;
import android.os.Message;

//
// Routine Sounder Thread. Loads the sounds one bit at a time.
//
public class SoundLoaderThread implements Runnable {
    private RoutineDisplay routineDisplay_;
    boolean loadedSounds_ = false;

    SoundLoaderThread(RoutineDisplay routineDisplay) {
    	routineDisplay_ = routineDisplay;
    }

    //
    // Send a message to either load sounds bit by bit. 
    //
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                Thread.sleep(1500);
            } catch (Exception e) {
            	return;
            }

            // Update the counter
            Handler handler = routineDisplay_.getHandler();

            // Tell the main view it needs updating
            Message msg = Message.obtain();
            
            if (!loadedSounds_) {
            	msg.arg1 = RoutineDisplay.MSG_LOAD_SOUND;
            	handler.sendMessageDelayed(msg, RoutineDisplay.MSG_LOAD_SOUND);
            	loadedSounds_ = true;
            }
            else {
            	loadedSounds_ = false;	
            }
        }
    }
}
