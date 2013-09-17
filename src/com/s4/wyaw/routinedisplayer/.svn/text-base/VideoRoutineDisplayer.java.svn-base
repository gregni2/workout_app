//
// file: RoutineDisplay.java
// owner: gsn
//
// TODO: Use themes for look http://developer.android.com/guide/topics/ui/themes.html,
//       http://developer.android.com/resources/articles/timed-ui-updates.html
//
package com.s4.wyaw.routinedisplayer;

import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.s4.wyaw.CompletedView;
import com.s4.wyaw.ExerciseExplanation;
import com.s4.wyaw.R;
import com.s4.wyaw.exercise.Exercise;
import com.s4.wyaw.exercise.Routine;

// KEY = WC41REE2S5H3HCRR3I4F
// Class: RoutineDisplay. 
// Description: Used to display an entire workout routine. 
// A clock counts down the seconds of a routine while we display a picture and text.
// Also. Sound is displayed for the start of each new exercise.
//

public class VideoRoutineDisplayer extends Activity implements OnClickListener {
	
	//
	// Routine information.
	//
	static public Exercise 	    curExercise_ = null;
	static public Routine 		routine_ = null;
	static public Iterator<Exercise>  exerciseIter_ = null;
	
	//
	// Information about next exercise when we are resting between exercises
	// Needed by ExplanationView
	// Semi-hack
	//
    String nextExerciseName_ = "";
    String nextExerciseDesc_ = "";
    int nextExercisePict_ = 0;

	//
	// Clock information.
	//
	private int 				countDown_ = 0;
  //  private int					numExercises_ = 0;
  //  private int 				exerciseCount_ = 0;
    
	//
	// View info/state.
	//
	private TextView 			clock_ = null;
	private TextView			clockInfo_ = null;
//	private TextView			moveInfo_ = null;
	private Button 				pauseButton_ = null;
	private Button 				skipButton_ = null;
   	static public boolean		statePaused_ = false;
   	
   	//
   	// Sound.
   	//
   	private RoutineSounds routineSounds_ = null;
   	
   	//
   	// We want the phone on during the hold routine.
   	//
   	PowerManager.WakeLock wl_;
   	PowerManager pm_;
   	
	//
	// Threads
	//
	//private VideoSoundLoaderThread soundRunnable_ = null;
   	//private UpdateVideoClockThread timerRunnable_ = null;
   //	private Thread soundThread_ = null;
   	//private Thread timerThreadHolder_ = null;
   	
   	//
   	// Display the routine image and clock. Setup threads.
   	//
   	@Override
    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
    	Log.d("RoutineDisplay::onCreate", "Enter");
  	
    	//
		// Init exercise work.
		//
		curExercise_ = null;		
		if (VideoRoutineDisplayer.routine_ == null) {
			FlurryAgent.onEvent("ERROR: routine_ == null ");
			return;
		}
		
		VideoRoutineDisplayer.exerciseIter_= VideoRoutineDisplayer.routine_.getExerciseIterator();
		//
	    // Routine display layout.
        //
    	setContentView(R.layout.videoroutinedisplay);
    	setVolumeControlStream(AudioManager.STREAM_MUSIC);

    	//
    	// Voices: Setup the spinner with the choices of voice.
    	//
    /* Spinner s = (Spinner) findViewById(R.id.spinner);
    	ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource( this, R.array.voice_choices, 
    																	     // android.R.layout.simple_spinner_item);
    																		  R.layout.my_spinner_text);
    	//adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    	s.setAdapter(adapter);
    	s.setOnItemSelectedListener(new VoiceSelectedListener());
    	 
        //
        // Setup the pause button.
        //
    //    pauseButton_ = (Button) findViewById(R.id.pause);
        pauseButton_.setOnClickListener(this);
        pauseButton_.setVisibility(Button.VISIBLE);
        
        //
        // Setup the skip button.
        //
        skipButton_ = (Button) findViewById(R.id.skip);
        skipButton_.setOnClickListener(this);
       
        //
        // Get voice saved preference.
        //
        final int DEFAULT_VALUE = 0;
		String preferenceName = "voiceType";
	    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
	    int voiceSelected = settings.getInt(preferenceName, DEFAULT_VALUE);
	    s.setSelection(voiceSelected);
    	
	    //
	    // Setup the sounds.
	    //
	    routineSounds_ = new RoutineSounds(routine_, getBaseContext(), voiceSelected);
	    
	    //
        // Setup the display.
        //
        setupStartOfRoutineDisplay();
        
        //
        // Setup the clock view. Give it an embedded font.
        //
        //Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/LCD-BOLD.TTF");
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/DIGITALDREAM.ttf");
        clock_.setTypeface(tf);
        
        //
        // Start Timer threads.
        //
        timerRunnable_  = new UpdateVideoClockThread(this);
        timerThreadHolder_= new Thread(timerRunnable_);
     //   timerThreadHolder_.start();
      
        //
        // Start sound thread.
        //
 	   	soundRunnable_ = new VideoSoundLoaderThread(this);
        soundThread_ = new Thread(soundRunnable_);
       // soundThread_.start();
        
        TextView title = (TextView) getWindow().findViewById(android.R.id.title);  
        title.setText("Title");
    
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
	   	
	@Override
	protected void onPause() {
		super.onPause();
		statePaused_ = true;
	}

	@Override
	protected void onStart() {
		super.onStart();
		FlurryAgent.onStartSession(this, "WC41REE2S5H3HCRR3I4F");
		statePaused_ = false;

		pm_ = (PowerManager) getSystemService(Context.POWER_SERVICE);
		wl_ = pm_.newWakeLock(PowerManager.SCREEN_DIM_WAKE_LOCK, "Routine Displayer");
		wl_.acquire();
		
		
		// Send the start event.
	    HashMap<String, String> parameters = new HashMap<String, String>();
	    parameters.put("Routine", VideoRoutineDisplayer.routine_.getName());
		FlurryAgent.onEvent("Begin Routine ", parameters);
		
		
		VideoView video = (VideoView)getWindow().findViewById(R.id.routinevideo);  
		String urlpath = "android.resource://" + getPackageName() + "/" + R.raw.corkscrew;
		Toast.makeText(this, "Path = " + urlpath, Toast.LENGTH_LONG).show();
		video.setVideoURI(Uri.parse(urlpath));
		video.start();
	  

	} 
	
	@Override
	protected void onStop() {
		super.onStop();

		// Send the stop event.
		if ((VideoRoutineDisplayer.routine_.getNumberExercises() - numExercises_) > 0) {
			HashMap<String, Integer> parameters = new HashMap<String, Integer>();
			parameters.put("exercise not done", (VideoRoutineDisplayer.routine_.getNumberExercises() - numExercises_));
			FlurryAgent.onEvent("Stop Not finished" + VideoRoutineDisplayer.routine_.getName(), parameters);
		}
		else {
			HashMap<String, String> parameters = new HashMap<String, String>();
			parameters.put("Routine Finished", VideoRoutineDisplayer.routine_.getName());
			FlurryAgent.onEvent("Stop finished", parameters);
		}
			
		FlurryAgent.onEndSession(this);
		wl_.release();
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();

	
		//
		// Stop the threads.
		//
		soundThread_.interrupt();
		timerThreadHolder_.interrupt(); 
		
		//
		// Reset the rested state.
		//
		Iterator<Exercise> iter = VideoRoutineDisplayer.routine_.getExerciseIterator();
		while (iter.hasNext()) {
			Exercise exercise = iter.next();
		//	Toast.makeText(this, "Exercise = " + exercise.getName(), Toast.LENGTH_LONG).show();
			exercise.setRested(false);
			exercise.setPerformed(false);
		}
		
		soundThread_ = null;
		timerThreadHolder_ = null;
		soundRunnable_ = null;
		timerRunnable_ = null;
		// routine_ = null;
		curExercise_ = null;
		
		//
		// Save the voice type.
		//
		String preferenceName = "voiceType";
	    SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
	    SharedPreferences.Editor editor = settings.edit();
	    int voiceSelected = routineSounds_.getVoiceSelected();
	    editor.putInt(preferenceName, voiceSelected);
	    editor.commit();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		statePaused_ = false;
	}
	
	//
	// Voice option selector notifications.
	//
	public class VoiceSelectedListener implements OnItemSelectedListener {

	    public void onItemSelected(AdapterView<?> parent,
	        View view, int pos, long id) {
	    	Log.d("VoiceSelectedListener::onItemSelected", "Enter");
			routineSounds_.setVoiceSelected(pos, curExercise_);
	    	Log.d("VoiceSelectedListener::onItemSelected", "Exit");
	    }

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
		}
   }
	
	//
	// Setup the display before we begin the routine.
	//
	void setupStartOfRoutineDisplay() 
	{
		Log.d("RoutineDisplay::setupStartOfRoutine", "Enter");
		
		Iterator<Exercise> iter = routine_.getExerciseIterator();
		curExercise_ = iter.next();
		numExercises_ = routine_.getNumberExercises();
		
	   //Toast.makeText(this, "Current exercise = " + curExercise_.getName() + " rest = " + curExercise_.isRested() + " active time = " + curExercise_.getTimeActive(), Toast.LENGTH_LONG).show();
		
		routineSounds_.loadExerciseSounds(curExercise_);   	// GSN. I think we can remove this and still sound good in the beginning.	
		
		/*
		//
		// Get the name of the routine.
		//
		TextView titleView = (TextView)findViewById(R.id.title);
		titleView.setText("Prepare for " + routine_.getName() + " routine");
		
		//
		// Describe the routines.
		//
		TextView exerciseDescription = (TextView)findViewById(R.id.exerciseDescription);
		exerciseDescription.setText(routine_.getDescription());
		
		//
		// No clock yet.
		//
		clock_ = (TextView)findViewById(R.id.clock);
        clock_.setText("");
        clockInfo_ = (TextView)findViewById(R.id.clockinfo);
        moveInfo_ = (TextView)findViewById(R.id.exerciseinfo);
     	++exerciseCount_;
       	moveInfo_.setText("Exercise " + exerciseCount_ + " of " + numExercises_);
  
		//
		// TODO: GSN. Picture it.
		//
	/*	ImageView imageView = (ImageView)findViewById(R.id.splashpatch9);
		imageView.setImageResource(curExercise_.getPicture());
		exerciseDescription.invalidate();
   */
		Log.d("RoutineDisplay::setupStartOfRoutine", "Exit");	
	}
	
	public void playPreparingSound()
	{
		routineSounds_.playPreparingSound(curExercise_);
	}
	
	//    
    // Implement the OnClickListener callback
    //
    public void onClick(View v) 
    {    
    	Log.d("RoutineDisplay::onClick", "Enter");
    	
    	//
    	// Pause button.
    	//
    	if (v.getId() == pauseButton_.getId() && (curExercise_ != null)) 
    	{ 
    		 //
   		 	 // Pause.
   		 	 //
   		 	 statePaused_ = true;
   	  	
    		 //
    		 // Setup for calling exercise explainer.
    		 //
             Intent intent = new Intent (this, ExerciseExplanation.class); 
             
             if (!curExercise_.isRested()) 
             {		 
                 intent.putExtra("name", curExercise_.getName());
                 intent.putExtra("description", curExercise_.getDescription());
                 intent.putExtra("picture", curExercise_.getPicture());
             }
             else if (nextExerciseName_ != null)
             {                 
                 intent.putExtra("name", nextExerciseName_);
                 intent.putExtra("description", nextExerciseDesc_);
                 intent.putExtra("picture", nextExercisePict_);
             }
    		 
    		 //
    		 // Start new intent.
    		 //
    		 startActivity(intent);
    	}
    	
    	else if (v.getId() == skipButton_.getId()) 
    	{ 
    		if (VideoRoutineDisplayer.routine_ != null) {
    			// Send the stop event.
    			HashMap<String, String> parameters = new HashMap<String, String>();
    			parameters.put("Exercise skipped", nextExerciseName_);	
    			FlurryAgent.onEvent("Skip Routine " + VideoRoutineDisplayer.routine_.getName(), parameters );
    		}
    		countDown_ = 0;
    	}
    	
    	Log.d("RoutineDisplay::onClick", "Exit");
    }
    
    
    //
    // Handle messages coming in. Used to update the display for the clock and picture.
    //
    public static final int MSG_FIRST_UPDATE = 0;
    public static final int MSG_UPDATE = 1;
    public static final int MSG_LOAD_SOUND = 2;
        
    //
    // Handle the messages: UPDATE (DISPLAY) and LOAD SOUND. 
    //
    private Handler handler_ = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	Log.d("handleMessage", "Enter");
            
        	//
        	// Update the views pictures.
        	//
        	if (MSG_UPDATE == msg.arg1) {
        		updateDisplay();
        	}
        	else if (MSG_FIRST_UPDATE == msg.arg1) {
        		playPreparingSound();
        	}
        	
        	//
        	// Load the next sound.
        	//
        	else if (MSG_LOAD_SOUND == msg.arg1) {
        		routineSounds_.addNextRoutineSound();
        	}
        	Log.d("handleMessage", "Exit");
        }
    };
    
    //
    // Get the next handler.
    //
    Handler getHandler() {
    	return handler_;
    }
    
    //
    // We're done. Move to the completed view.
    //
    protected synchronized void routineCompleted()
    {
     	Log.d("RoutineDisplay::routineCompleted", "Enter");		
     	FlurryAgent.onEvent("Completed = " + VideoRoutineDisplayer.routine_.getName());

     	//
		// New intent.
		//
        Intent intent = new Intent(this, CompletedView.class);
        
        // CompletedView needs to know which routine was completed
        Bundle bundle = new Bundle();
        bundle.putString("name", routine_.getName());
        bundle.putString("level", routine_.getLevel());
        intent.putExtras(bundle);
        
        startActivity(intent);
      
        //
        // Done.
        //
        finish();
   
        Log.d("RoutineDisplay::routineCompleted", "Exit");
    }
    
    //
    // Change the images, text, sound, and clock.
    //
    protected synchronized void updateDisplay()
    {
    	Log.d("updateDisplay", "Updating for count = " + countDown_);

    	//
    	// If we are paused. Don't keep counting down.
    	//
    	if (statePaused_) {
    		Log.d("updateDisplay", "Returning. Paused state");
    		return;
    	}
    	countDown_--;
   	 
   	 	//
    	// Sanity check.
    	//
    	if ( (exerciseIter_ == null ) || (curExercise_ == null) || (clock_ ==  null) || (clockInfo_ == null) ) {
    		Log.d("updateDisplay", "Return. Not setup exercise routine in thread." + exerciseIter_ + " " + curExercise_ + " " + clock_);
    		return;
    	}
     	
   	 	//
   	 	// If there are no exercises left and we've counted down and rested. 
   	 	// Then we're done.
   	 	// WFM. Add completed view here.
   	 	//
   		if (!exerciseIter_.hasNext() && countDown_ <= 0 && curExercise_.isRested()) {
   			routineCompleted();
   		}	

   	 	//
   	 	// Updates clock with new look.
   	 	//   		
   		if ( countDown_ > 0 ) {
   	
   			if (!curExercise_.isRested()) {
   				Log.d("updateDisplay", "set is active clock " + curExercise_.getName() + " count down = " + countDown_);
   	   	        clock_.setText(getClockString(countDown_));
   	   	        clockInfo_.setText("Count Down");
   	 		}
   	 		else {
   				Log.d("updateDisplay", "set is rested clock " + curExercise_.getName() + " count down = " + countDown_);
   	 			clock_.setText(getClockString(countDown_));
   	   	        clockInfo_.setText("Rest");
   	 		}
   			clockInfo_.invalidate();
        	clock_.invalidate();
   	 		return;
   	 	}
   		
   	 	//
   	 	// If we've performed a current exercise but not rested.
   	 	//
   	 	if ((curExercise_ != null) && curExercise_.isPerformed() && !curExercise_.isRested()) {
   	 		//
   	 		// Setup the clock.
   	 		//
   	 		countDown_ = curExercise_.getRestTime();
   	 		clock_.setText(getClockString(countDown_));
	   	    clockInfo_.setText("Rest");
	   		clockInfo_.invalidate();
   	   		clock_.invalidate();
   		 	Log.d("updateDisplay", "Getting next exercise for rest time " + curExercise_.getName());
   	       
   	 		//
   	 		// Exercise description is empty. And set that we've rested.
   	 		// 
   		 	/*
   	 		final TextView exerciseDescription = (TextView)findViewById(R.id.exerciseDescription);
   	 		exerciseDescription.setText("");
   	 		curExercise_.setRested(true); */
   	 		
   	 		//
   	 		// Get the next exercise sound.
   			//
   	 		Iterator<Exercise> tmpExerciseIter = VideoRoutineDisplayer.routine_.getExerciseIterator();
   			while (tmpExerciseIter.hasNext() && (tmpExerciseIter.next() != curExercise_ )) {
   			};
   			
   			if (tmpExerciseIter.hasNext()) {
   				Exercise tmpExercise = tmpExerciseIter.next();
   				routineSounds_.playPreparingSound(tmpExercise);
   		
   				// Describe it.
   	    		//
   				/*
   	    		TextView tmpExerciseDescription = (TextView)findViewById(R.id.exerciseDescription);
   	    		tmpExerciseDescription.setText(tmpExercise.getShortDescription_());
   	        	TextView titleView = (TextView)findViewById(R.id.title);
   	        	titleView.setText(tmpExercise.getName());
   	        	++exerciseCount_;
   	        	moveInfo_.setText("Exercise " + exerciseCount_ + " of " + numExercises_);

   	        	//
   	        	// Picture it. TODO: GSN. 
   	        	//
   	        	/*ImageView imageView = (ImageView)findViewById(R.id.splashpatch9);
   	        	imageView.setImageResource(tmpExercise.getPicture());
   	        	*/
   	        	
   	        	nextExerciseName_ = tmpExercise.getName();
   	        	nextExerciseDesc_ = tmpExercise.getShortDescription_();
   	        	nextExercisePict_ = tmpExercise.getPicture();
   				
   	        	//
   	        	// Redraw the description.
   	        	//
   	      //  	exerciseDescription.invalidate();
   	        //	moveInfo_.invalidate();
   			}
   	 	}
   	 	//
   	 	// If we have another exercise. Grab it and setup all the graphics, sound, and text.
   	 	//
   	 	else if ( exerciseIter_.hasNext() ) {
		
   	 		//
    		// Get it.
    		//
    		curExercise_ = exerciseIter_.next();
       	 	Log.d("updateDisplay", "Getting next exercise for active time " + curExercise_.getName());
        	
    		//
    		// Describe it.
    		//
    	/*	TextView exerciseDescription = (TextView)findViewById(R.id.exerciseDescription);
        	exerciseDescription.setText(curExercise_.getShortDescription_());
        	TextView titleView = (TextView)findViewById(R.id.title);
        	titleView.setText(curExercise_.getName());
        	
        	//
        	// Picture it.
        	// TODO: GSN.
        	/*
        	ImageView imageView = (ImageView)findViewById(R.id.splashpatch9);
        	imageView.setImageResource(curExercise_.getPicture());
			*/
        	//
        	// Setup the clock.
        	//
        	countDown_ = curExercise_.getTimeActive();
     		clock_.setText(getClockString(countDown_)); 
     		clockInfo_.setText("Count Down");
  	   		clockInfo_.invalidate();
     	   	clock_.invalidate();
    	
        	//
        	// Redraw the description.
        	//
        //	exerciseDescription.invalidate();
        	
        	//
        	// Play the sound.
        	//
        	routineSounds_.playSound(curExercise_);
            
        	//
           	// Marked as performed.
           	//
           	curExercise_.setPerformed(true);
    	}
    
   		Log.d("updateDisplay", "returning");
    }

   String getClockString(int time) {
		String digialString = new String(""+time);
		if (digialString.length() < 2) {
			digialString = "0" + digialString; 
		}
		digialString = "00:" + digialString;
		
		return digialString;
   }
}