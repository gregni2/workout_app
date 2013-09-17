package com.s4.wyaw;

import java.util.HashMap;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.flurry.android.FlurryAgent;
import com.s4.wyaw.R.raw;
import com.s4.wyaw.awards.AwardInformation;
import com.s4.wyaw.awards.Awards;
import com.s4.wyaw.dialogs.AppRater;
import com.s4.wyaw.list.RoutineSelectorActivity;
import com.s4.wyaw.sound.SoundManager;
import com.s4.wyaw.workoutalarm.WorkoutAlarm;

public class DashboardActivity extends Activity {

	public static SoundManager soundManager_ = null;
	
	public static String lastAwardString_ = null;
	
	private class DashboardClickListener implements OnClickListener {
	    @Override
	    public void onClick(View v) {
	        Intent i = null;
	        String[] workoutCategories = getResources().getStringArray(R.array.workout_categories);
	        switch (v.getId()) {
	            case R.id.trophies_button:
	            	i = new Intent(DashboardActivity.this, TrophySelectorList.class);
	            	FlurryAgent.onEvent("Category Awards");
	            	break;
	            case R.id.office_button:
	                i = new Intent(DashboardActivity.this, RoutineSelectorActivity.class);
	            	FlurryAgent.onEvent("Category Office");
		            i.putExtra("category",  workoutCategories[0]);
	        	    break;
	            case R.id.travel_workout_button:
	            	FlurryAgent.onEvent("Category Travel");
			        i = new Intent(DashboardActivity.this, RoutineSelectorActivity.class);
	                i.putExtra("category",  workoutCategories[1]);
	        	    break;
	            case R.id.minutes_workout_button:
	            	FlurryAgent.onEvent("Category Minute");
			        i = new Intent(DashboardActivity.this, RoutineSelectorActivity.class);
	             	i.putExtra("category",  workoutCategories[2]);
	        	    break;
	            case R.id.someequipement_workout_button:
	            	FlurryAgent.onEvent("Category Some Equipment");
			        i = new Intent(DashboardActivity.this, RoutineSelectorActivity.class);
	                i.putExtra("category",  workoutCategories[3]);
	        	    break;
	            case R.id.latest_workout:
	            	FlurryAgent.onEvent("Category Latest");
	            	
	            	i = new Intent(DashboardActivity.this, RoutineSelectorActivity.class); 
	            	i.putExtra("category",  workoutCategories[4]); 
	            	break;
//	            case R.id.ad_button:
//	             	FlurryAgent.onEvent("Aylio Add Click");
//	 	            i = new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.aylio.com/?utm_source=workoutapp&utm_medium=banner&utm_campaign=workout%2Bapp"));
//	            	break;
//	            	
	            default:
	                break;
	        }
	        if(i != null) {
	        	
	            startActivity(i);
	        }
	    }
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    Eula.show(this);

	    setContentView(R.layout.dashboard);
       
        if (soundManager_ == null) {
        	soundManager_ = new SoundManager();
        	soundManager_.initSounds(/*getBaseContext()*/ this);
    	    soundManager_.addSound(raw.begin, raw.begin);
    	    soundManager_.addSound(raw.mmarchinplace, raw.mmarchinplace);
    	}

	 
	    //attach event handler to dash buttons
	    DashboardClickListener dBClickListener = new DashboardClickListener();
	    findViewById(R.id.trophies_button).setOnClickListener(dBClickListener);
	    findViewById(R.id.office_button).setOnClickListener(dBClickListener);
	    findViewById(R.id.travel_workout_button).setOnClickListener(dBClickListener);
	    findViewById(R.id.minutes_workout_button).setOnClickListener(dBClickListener);
	    findViewById(R.id.latest_workout).setOnClickListener(dBClickListener);
	    findViewById(R.id.someequipement_workout_button).setOnClickListener(dBClickListener);
	  //   findViewById(R.id.ad_button).setOnClickListener(dBClickListener);
	    

        AppRater.app_launched(this);
		}
	
	
	//
	// Setup awards header.
	//
	@Override protected void onStart() { 	
		super.onStart();
		
		/*FlurryAgent.onStartSession(this, "WC41REE2S5H3HCRR3I4F");
		
		HashMap<String, String> parameters = new HashMap<String, String>();
		parameters.put("Where Displayed", "Dashboard");	
		FlurryAgent.onEvent("Aylio ad displayed", parameters); */
    	
		// Get the points and level name.
		int points = Awards.getTotalPoints(this);
    	AwardInformation info = Awards.getCurrentRangeAndLevelNameName(points);
		
    	// See if the button has changed.
		if ( (lastAwardString_ != null) && !lastAwardString_.contains(info.levelName) ){
			Toast.makeText(getBaseContext(), "Congrats! You're now " + info.levelName + ". Click 'Awards' to see your awards!", Toast.LENGTH_LONG).show();
		}
		
		// Set the button.
		lastAwardString_ = new String(info.levelName);
		setTitle(" Awarded: " + info.levelName + " ");
	}

	@Override
	protected void onStop() {
		super.onStop();
		
		FlurryAgent.onEndSession(this);
	}
	
}
