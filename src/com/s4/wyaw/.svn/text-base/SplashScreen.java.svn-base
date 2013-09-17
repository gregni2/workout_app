package com.s4.wyaw;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.text.format.Time;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.s4.wyaw.exercise.Trainer;


public class SplashScreen extends Activity {
	boolean displayFinished_ = false;
    
    //
    // Cut it short if the viewer clicks.
	//
    private android.view.View.OnClickListener onClickListener_ = new android.view.View.OnClickListener() {
		public void onClick(View v) {
			Trainer.getTrainer(getBaseContext());
        	
			//
			// Disallow back button to return to splash screen
			//
			finish();
    	
			//
            // Run next activity
            //
			displayFinished_ = true;	
			Intent intent = new Intent();
          //  intent.setClass(SplashScreen.this, WorkoutChooser.class);
			  intent.setClass(SplashScreen.this, DashboardActivity.class);
	            
			startActivity(intent);
       }	
	};
	
	String experiationDate = "2011-12-01";

	//
	// Display the splash screen image.
	//
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("SplashScreen", "Displaying Splash");
        
        // GSN. Beta
        Time now = new Time();
        now.setToNow();
        Log.i("SplashScreen on create", "DATE = " + now);
        Time time2 = new Time();
        time2.parse3339(experiationDate);
    /*    if (Time.compare(now, time2) > 0) {
        	Toast.makeText(getBaseContext(), "Free Beta version: 0 Days left ",  Toast.LENGTH_LONG).show();
        	finish();
        	return;
        }
        else {
           	Toast.makeText(getBaseContext(), "Free Beta version: expires 2011-12-01",  Toast.LENGTH_SHORT).show();
        }
    	*/
       
        //
	    // Splash layout.
        //
    	setContentView(R.layout.splash);
    	
    	//
    	// Listener to short circit it.
    	// 
    	final ImageView splashImageView = (ImageView) findViewById(R.id.splashImageView);
        splashImageView.setOnClickListener((android.view.View.OnClickListener) onClickListener_);
	    	
         // New Handler to start the WorkoutChooser
	     // and close this Splash-Screen after some seconds.
         new Handler().postDelayed(new Runnable(){

                @Override
                public void run() {
                	Trainer.getTrainer(getBaseContext());
                	
                	if (!displayFinished_) {
                	 		((BitmapDrawable)splashImageView.getBackground()).getBitmap().recycle();
                			displayFinished_ = true;
                		//	Intent mainIntent = new Intent(SplashScreen.this, WorkoutChooser.class);
                			Intent mainIntent = new Intent(SplashScreen.this, DashboardActivity.class);
                			
                			SplashScreen.this.startActivity(mainIntent);
                			SplashScreen.this.finish();
                		}
                }
        }, 3000);
         

         
   //      MemoryInfo mi = new MemoryInfo();
     //    ActivityManager activityManager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
       //  activityManager.getMemoryInfo(mi);
       //  long availableMegs = mi.availMem / 1048576L;
        // Toast.makeText(this, "Memory available = " + availableMegs, Toast.LENGTH_LONG).show();
  
    }
}



