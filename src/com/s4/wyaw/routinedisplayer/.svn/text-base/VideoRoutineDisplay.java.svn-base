//
// file: RoutineDisplay.java
// owner: gsn
//
// TODO: Use themes for look http://developer.android.com/guide/topics/ui/themes.html,
//       http://developer.android.com/resources/articles/timed-ui-updates.html
//
package com.s4.wyaw.routinedisplayer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import android.widget.VideoView;

import com.s4.wyaw.R;

// KEY = WC41REE2S5H3HCRR3I4F
// Class: RoutineDisplay. 
// Description: Used to display an entire workout routine. 
// A clock counts down the seconds of a routine while we display a picture and text.
// Also. Sound is displayed for the start of each new exercise.
//

public class VideoRoutineDisplay extends Activity  {
	
	   	
   	//
   	// Display the routine image and clock. Setup threads.
   	//
   	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
    	Log.d("RoutineDisplay::onCreate", "Enter");
    	
    	setContentView(R.layout.videoroutinedisplay);
    	
    	//
        // Video work.
        //
		VideoView video = (VideoView)getWindow().findViewById(R.id.routinevideo);  
		String urlpath = "android.resource://" + getPackageName() + "/" + R.raw.corkscrew;
		Toast.makeText(this, "Path = " + urlpath, Toast.LENGTH_LONG).show();
		video.setVideoURI(Uri.parse(urlpath));
		video.start();
	    
 		Log.d("RoutineDisplay::onCreate", "Exit");
   	}
}