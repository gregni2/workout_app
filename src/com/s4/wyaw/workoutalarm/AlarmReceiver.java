package com.s4.wyaw.workoutalarm;

import java.util.Vector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.s4.wyaw.DashboardActivity;
import com.s4.wyaw.R;
import com.s4.wyaw.exercise.Routine;
import com.s4.wyaw.exercise.Trainer;
import com.s4.wyaw.routinedisplayer.RoutineDisplay;
import com.s4.wyaw.sound.SoundManager;


public class AlarmReceiver extends BroadcastReceiver {
	
	    @Override
	    public void onReceive(Context context, Intent intent)
	    {
	    	Log.i("OneShortAralm", "Called"); 
	    	
	    	Toast.makeText(context, "ALARM RECEIVED", Toast.LENGTH_LONG).show();
	    	
	        Intent i = new Intent(context, RoutineDisplay.class);
	        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
	        i.putExtra("STARTED_BY_RECEIVER", true);
	  
	        Trainer trainer = Trainer.getTrainer(context);
	        trainer.loadSecondGroupOfRoutines(context);
	        
	        Bundle bundle = intent.getExtras();
	        int pos = bundle.getInt("pos");
	        
	        //
	        // There is an array of strings called "workout_categories"
	        // set the list adapter with these strings.
	        //
	        Vector<Routine> routines = trainer.getAllRoutines();
	        RoutineDisplay.routine_ = routines.get(pos);
	    
	        context.startActivity(i);
	    }   
}
