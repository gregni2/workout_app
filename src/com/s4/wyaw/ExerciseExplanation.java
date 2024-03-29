package com.s4.wyaw;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.s4.wyaw.routinedisplayer.RoutineDisplay;

public class ExerciseExplanation extends Activity 
{
	
	private class AdClickListener implements OnClickListener {
	    @Override
	    public void onClick(View v) {
	    	FlurryAgent.onEvent("Aylio Add Click");
	    	startActivity( new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.aylio.com/?utm_source=workoutapp&utm_medium=banner&utm_campaign=workout%2Bapp")) );
        }
	}
	
	
    // Called when the activity is first created.
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        Log.d("ExeerciseExplanation", "enter onCreate");
        
        //
        // Set UI in window
        //
        setContentView(R.layout.explanationtest);
        
        //
        // Pull exercise info from bundle
        //
        Bundle bundle = getIntent().getExtras();
        String exerciseName =  bundle.getString("name");
        String description = bundle.getString("description");
        String videoUrl = bundle.getString("videoUrl");
        
        //
        // Get explanation title view and include name in title
        //
        TextView view = (TextView) findViewById(R.id.explanation_title);
        String newText = view.getText() + System.getProperty("line.separator") + exerciseName;
        view.setText(newText);
        
        //
        // Set description
        //
        view = (TextView) findViewById(R.id.exercise_description);
        newText = description;
        view.setText(newText);
        
        //
        // Set video URL
        //
        /*
        if ( (videoUrl != null) && (videoUrl.length() > 0) )
        {
        	view = (TextView) findViewById(R.id.exercise_video_url);
            newText = videoUrl;
            view.setText(newText);
        }
        */
        
        //
        // Show all the pictures from 1 - 4 before the text.
        //
        displayPictureList(bundle);
        
        // Setup button listener
        Button startButton = (Button) findViewById (R.id.resumeButton);
        startButton.setOnClickListener (new View.OnClickListener()
        {
            public void onClick(View view)
            {
               	RoutineDisplay.statePaused_ = false;
                setResult(RESULT_OK);
            	finish();
            }
        });
  
       
        // Listen for ads.
        AdClickListener adClickListener = new AdClickListener();
 	    findViewById(R.id.trailer).setOnClickListener(adClickListener);
        
    }
    
   /**
    * Displays the list of pictures specified in bundle before the exercise description text.
    * @param bundle contains the picture list and number of pictures.
    */
   private void displayPictureList(Bundle bundle) {
        //
        // Set picture
        //
        int picture = bundle.getInt("picture");
        ImageButton iview = (ImageButton) findViewById(R.id.imageButton0);
        iview.setImageResource(picture);      
      //  Log.e("ExerciseExplanation", "Base Picture = " + picture);
        
        //
        // Display the list of exercises.
        //
        int numPicts = bundle.getInt("numpicts");
        //Log.e("ExerciseExplanation", "num pictures = " + numPicts);
        for (int i = 1; (i < 4); i++ ) { 
        	
        	//
        	//  Get the image button associated with the picture number.
        	//
        	switch (i) {
	    		case 1: 
	    			iview = (ImageButton) findViewById(R.id.imageButton1);
	    			break;
	    		case 2:
	      			iview = (ImageButton) findViewById(R.id.imageButton2);
	      	      break;
	    		case 3:
	      			iview = (ImageButton) findViewById(R.id.imageButton3);
	    			break;
        	}
        	
        	//
        	// hide pictures not in the list.
        	//
        	if (i >= numPicts) {
        		//Log.e("ExerciseExplaination", "i = " + i + " not visible");
        		iview.setVisibility(View.GONE);
        	}
        	// Display pictures.
        	else {
        		String name = "picture"+i;
        		picture = bundle.getInt(name);
        	//	Log.e("ExerciseExplaination", "i = " + i + " visible. Name = " + name + " and picture = " + picture);
        		iview.setImageResource(picture);      
        	}
        }
    	
    }
    
   /**
    * Setup the flurry stuff.
    */
 	@Override protected void onStart() { 	
 		super.onStart();
 		/*FlurryAgent.onStartSession(this, "WC41REE2S5H3HCRR3I4F");
 		HashMap<String, String> eventArgs = new HashMap<String, String>();
 		eventArgs.put("Where Displayed", "Explanation View");	
 		FlurryAgent.onEvent("Aylio ad displayed", eventArgs);*/
 	}
 	

 	@Override
 	protected void onStop() {
 		super.onStop();
 		FlurryAgent.onEndSession(this);
 	}
    
};