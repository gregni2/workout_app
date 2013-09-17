package com.s4.wyaw.workoutalarm;

import java.util.Calendar;
import java.util.Vector;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TimePicker;

import com.flurry.android.FlurryAgent;
import com.s4.wyaw.R;
import com.s4.wyaw.exercise.Routine;
import com.s4.wyaw.exercise.Trainer;


// A view that comes up after the routine is completed.  If awards have been
// earned, they are displayed here.  The user has the option to go to a 
// 'trophy room' view to see all awards earned.
public class ScheduleAlarmView extends Activity implements OnClickListener
{
    
	// Items in the preferences database with this prefix of alarms.
    private static final String ALARM_PREFIX = "alarm.";
	
	Button submitButton_;
	Button cancelButton_;

    Routine [] routineArray_;
    TimePicker time_;
    Spinner spinner_;
	

	//    
    // Implement the OnClickListener callback
    //
    public void onClick(View v) 
    {    
    	//
    	// For submit. Save the alarm time in the preferences.
    	//
    	if (v.getId() == submitButton_.getId()) { 
    		//
    		// Get all settings, including accomplishment counts, from the 
    		// preferences database
    		//
    		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
    		SharedPreferences.Editor editor = settings.edit();
            
    		//
    		// Now, update the count for the routine that was completed.
    		// Preferences with the prefix ALARM_PREFIX are counts of alarms set.
    		//
    		int pos = spinner_.getSelectedItemPosition();
    		
    		//
    		// Setup the minutes to have 0 in front of the ones less then two digits.
    		//
    		String timeStringMinutes = ""+time_.getCurrentMinute();
    		if (timeStringMinutes.length() < 2) {
    			timeStringMinutes = "0" + timeStringMinutes;
    		}
    		
    		String key = ALARM_PREFIX+routineArray_[pos]+ "::" + time_.getCurrentHour() + ":" + timeStringMinutes;
    		int count = settings.getInt(key, 900); // 0 is default value   
	       
    		//
    		// Update database with accomplishment
    		//
    		editor.putInt(key, ++count);
    		editor.commit();
    		
    		//
   		 	// Setup the broadcaster to receive an alarm.
   		 	//
    		Intent intent = new Intent(ScheduleAlarmView.this, AlarmReceiver.class);
    		Bundle bundle = new Bundle();
    		bundle.putString("key", key);
    		bundle.putInt("pos", pos);
    	    intent.putExtras(bundle);
    	    intent.addCategory(key);
    	    PendingIntent sender = PendingIntent.getBroadcast(ScheduleAlarmView.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
	   		
	   		//
	   		// We want the alarm to go off 30 seconds from now.
	   		//
	   		Calendar calendar = Calendar.getInstance();
	   		calendar.set(Calendar.HOUR_OF_DAY, time_.getCurrentHour());
	   		calendar.set(Calendar.MINUTE, time_.getCurrentMinute());
	   		
	   		//
	   		// Schedule the alarm.
	   		//
	   		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
	   	
	   		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), /* AlarmManager.INTERVAL_FIFTEEN_MINUTES */ AlarmManager.INTERVAL_DAY, sender);
    	}
    	else if (v.getId() == cancelButton_.getId()) {
    		// do nothing.
    	}
    	
    	// Go back to alarm view.
		Intent intent = new Intent(this, WorkoutAlarm.class);
	    startActivity(intent);
    }
    
   
 	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_exercise);
    
        //
        // Setup the routine spinner items. These are gotten from the routine names.
        //
        Spinner routinesSpinner = (Spinner) this.findViewById(R.id.routine_list_spinner);
        Trainer trainer = Trainer.getTrainer(this);
        trainer.loadSecondGroupOfRoutines(this);
        if (trainer != null) {
        	Vector<Routine> routineVector = trainer.getAllRoutines();
        	routineArray_ = new Routine[routineVector.size()];
        	routineVector.toArray(routineArray_);
        	routinesSpinner.setAdapter(new ArrayAdapter<Routine>(this.getBaseContext(), R.layout.my_spinner_text, routineArray_));
        }
        
        //
        // Set submit listener.
        //
        submitButton_ = (Button) this.findViewById(R.id.scheduleSubmitButton);
        submitButton_.setOnClickListener(this);
        
        //
        // Set cancel listener.
        //
        cancelButton_ = (Button) this.findViewById(R.id.scheduleCancelButton);
        cancelButton_.setOnClickListener(this);
        
        time_ = (TimePicker)this.findViewById(R.id.alarm_time);
        spinner_ = (Spinner)this.findViewById(R.id.routine_list_spinner);
        
       
    }
    
    
    @Override
    protected void onStart() {
    	super.onStart();
    	FlurryAgent.onStartSession(this, "WC41REE2S5H3HCRR3I4F");
		FlurryAgent.onEvent("Routine Scheduled Alarm");
    }
    

	@Override
	protected void onStop() {
		super.onStop();
		FlurryAgent.onEndSession(this);
	}
    
    public void done(View view) 
    {
        finish();
        Intent intent = new Intent (this, WorkoutAlarm.class); 
        startActivity(intent);
        
    }
}