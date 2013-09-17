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
import android.text.format.Time;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TimePicker;

import com.flurry.android.FlurryAgent;
import com.s4.wyaw.R;
import com.s4.wyaw.exercise.Routine;
import com.s4.wyaw.exercise.Trainer;


// A view that comes up after the routine is completed.  If awards have been
// earned, they are displayed here.  The user has the option to go to a 
// 'trophy room' view to see all awards earned.
public class PlanDayAlarmView extends Activity implements OnClickListener
{
    
	// Items in the preferences database with this prefix of alarms.
    private static final String ALARM_PREFIX = "alarm.";
	
	Button submitButton_;
	Button cancelButton_;
    TimePicker time_;

    Routine [] routineArray_;

    
    //
    // Find the position of the routine by name.
    //
    int findPosition(String name) {
    	int pos = 0;
    	boolean found = false;
    	while ( (pos < routineArray_.length) && !found) {
    		if (routineArray_[pos].getName().equalsIgnoreCase(name)) {
    			found = true;
    		}
    		else {
    			pos++;
    		}
    	}
    	
    	return pos;
    }
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
    		// First one. Morning stretch.
    		//
    		int currentHour = time_.getCurrentHour();
    		int currentMinute = time_.getCurrentMinute();
    	
    		int pos = findPosition("Good Morning Bed Stretch");
    		saveAnAlarm(pos, currentMinute, currentHour);
    		
    		//
    		// Second. Desk Stretch.
    		//
    		pos = findPosition("Desk Stretch");
    		saveAnAlarm(pos, currentMinute+2, currentHour);
    		
    		
    		//
    		// Third. Midday office workout. Beginner Workaholic Workout
    		//
    		pos = findPosition("Beginner Workaholic Workout");
    		saveAnAlarm(pos, currentMinute, currentHour+4);
    		
    		//
    		// Forth. Desk Stretch.
    		//
    		pos = findPosition("Desk Stretch");
        	saveAnAlarm(pos, currentMinute, currentHour+6);
    		
    		//
    		// Workout at home. 15 minute intermediate.
    		//
        	pos = findPosition("15 Minute Intermediate Workout");
    		saveAnAlarm(pos, currentMinute, currentHour+10);
    		
    		//
    		// Stretch before bed.
    		//
    		pos = findPosition("Good Night Bed Stretch");
    		saveAnAlarm(pos, currentMinute, currentHour+13);
    	}
    	else if (v.getId() == cancelButton_.getId()) {
    		// do nothing.
    	}
    	
    	// Go back to alarm view.
		Intent intent = new Intent(this, WorkoutAlarm.class);
	    startActivity(intent);
    }
    
    public void saveAnAlarm(int pos, int currentMinute, int currentHours) {
    	//
		// Get all settings, including accomplishment counts, from the 
		// preferences database
		//
		SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
		SharedPreferences.Editor editor = settings.edit();
        
		
		//
		// Setup the minutes to have 0 in front of the ones less then two digits.
		//
		String timeStringMinutes = ""+currentMinute;
		if (timeStringMinutes.length() < 2) {
			timeStringMinutes = "0" + timeStringMinutes;
		}
		
		String key = ALARM_PREFIX+routineArray_[pos]+ "::" + currentHours + ":" + timeStringMinutes;
		int count = settings.getInt(key, 900); // 0 is default value   
       
		//
		// Update database with accomplishment
		//
		editor.putInt(key, ++count);
		editor.commit();
		
		//
		// Setup the broadcaster to receive an alarm.
		//
		Intent intent = new Intent(PlanDayAlarmView.this, AlarmReceiver.class);
		Bundle bundle = new Bundle();
		bundle.putString("key", key);
		bundle.putInt("pos", pos);
	    intent.putExtras(bundle);
	    intent.addCategory(key);
	    PendingIntent sender = PendingIntent.getBroadcast(PlanDayAlarmView.this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
   		
   		//
   		// We want the alarm to go off 30 seconds from now.
   		//
   		Calendar calendar = Calendar.getInstance();
   		calendar.set(Calendar.HOUR_OF_DAY, currentHours);
   		calendar.set(Calendar.MINUTE, currentMinute);
   		
   		//
   		// Schedule the alarm.
   		//
   		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
   		am.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, sender);
	}
    
   
 	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.planday);
    
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
        
        //
        // Connect the time.
        //
        time_ = (TimePicker)this.findViewById(R.id.alarm_time);     
        
        //
        // Get the list of possible routines.
        //
        Trainer trainer = Trainer.getTrainer(this);
        trainer.loadSecondGroupOfRoutines(this);
        if (trainer != null) {
        	Vector<Routine> routineVector = trainer.getAllRoutines();
        	routineArray_ = new Routine[routineVector.size()];
        	routineVector.toArray(routineArray_);;
        }
       
    }
    
    
    @Override
    protected void onStart() {
    	super.onStart();
    	FlurryAgent.onStartSession(this, "WC41REE2S5H3HCRR3I4F");
		FlurryAgent.onEvent("Planday Scheduled Alarm");
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