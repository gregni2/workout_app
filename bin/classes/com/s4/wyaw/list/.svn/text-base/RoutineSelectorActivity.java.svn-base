package com.s4.wyaw.list;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.s4.wyaw.R;
import com.s4.wyaw.exercise.Routine;
import com.s4.wyaw.exercise.Trainer;
import com.s4.wyaw.list.item.EntryAdapter;
import com.s4.wyaw.list.item.EntryItem;
import com.s4.wyaw.list.item.Item;
import com.s4.wyaw.list.item.SectionItem;
import com.s4.wyaw.routinedisplayer.RoutineDisplay;
import com.s4.wyaw.routinedisplayer.VideoRoutineDisplay;
import com.s4.wyaw.workoutalarm.WorkoutAlarm;


public class RoutineSelectorActivity extends ListActivity {
    /** Called when the activity is first created. */
	
	ArrayList<Item> items = new ArrayList<Item>();
	Vector<Routine> routines_ = null;
	public static final int SCHEDULE_WORKOUT_NUMBER = 99;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("RoutineSelectorActivity", "Enter Constructor");
           
        //
        // Pull the category/routine out.
        //
        Bundle extras = getIntent().getExtras();
        String category = (extras != null) ? extras.getString("category") : "ERROR";
        Log.d("CategorySelectorList routine names setup - category = ", category);
      
        //
        // Setup the trainer.
        //
        Trainer trainer = Trainer.getTrainer(this);
        trainer.loadSecondGroupOfRoutines(this);
        
        //
        // Put the passed-in category into the title
        //
        this.setTitle("Category: " + category);
        
        //
        // There is an array of strings called "workout_categories"
        // set the list adapter with these strings.
        //
        routines_ = trainer.getRoutines(category);
        if (routines_ == null) {
        	Log.e("RoutineSelectorActivity::onCreate", "routines NULL");
        	return;
        }
       
        //
        // Get all the beginner routines.
        //
        Iterator<Routine> iter = routines_.iterator();
        LinkedList<Routine> begginerList = new LinkedList<Routine>();        
        while (iter.hasNext()) {
        	Routine curRoutine = iter.next();
        	if ( curRoutine.getLevel().equalsIgnoreCase("Beginner") ) {
        		begginerList.add(curRoutine);
        	}
        }
       
        //
        // Get all the intermediate routines and store in a list.
        //
        iter = routines_.iterator();
        LinkedList<Routine> intermediateList = new LinkedList<Routine>();        
        while (iter.hasNext()) {
        	Routine curRoutine = iter.next();
        	if ( curRoutine.getLevel().equalsIgnoreCase("Intermediate") ) {
        		intermediateList.add(curRoutine); 
        	}
        }
        
        //
        // Get all the advanced routines and store in a list.
        //
        iter = routines_.iterator();
        LinkedList<Routine> advancedList = new LinkedList<Routine>();        
        while (iter.hasNext()) {
        	Routine curRoutine = iter.next();
        	if ( curRoutine.getLevel().equalsIgnoreCase("Advanced") ) {
        		advancedList.add(curRoutine); 
        	}
        }
        
        //
        // Add the begin, intermediate, and advanced added.
        //
        if (begginerList.size() > 0) {
        	items.add(new SectionItem("Beginner"));
        	iter = begginerList.iterator();
        	int routineNum = 0;
        	while (iter.hasNext()) {
        		Routine curRoutine = iter.next();
        		setNewEntry(curRoutine.getName(), curRoutine.getTime(), curRoutine.getRequired(), curRoutine.getImage(), routineNum++, curRoutine);
        	}
        }
        if (intermediateList.size() > 0) {
        	items.add(new SectionItem("Intermediate"));
        	iter = intermediateList.iterator();
        	int routineNum = 0;
        	while (iter.hasNext()) {
        		Routine curRoutine = iter.next();
        		setNewEntry(curRoutine.getName(), curRoutine.getTime(), curRoutine.getRequired(), curRoutine.getImage(), routineNum++, curRoutine);
        	}
        }
        if (advancedList.size() > 0) {
        	items.add(new SectionItem("Advanced"));
        	iter = advancedList.iterator();
        	int routineNum = 0;
        	while (iter.hasNext()) {
        		Routine curRoutine = iter.next();
        		setNewEntry(curRoutine.getName(), curRoutine.getTime(), curRoutine.getRequired(), curRoutine.getImage(), routineNum++, curRoutine);
        	}
        }
        
        //
        // For latest/highlights, display a feature section. 
        //
        if (category.contains("Latest")) {
        	items.add(new SectionItem("Features"));
        	setNewEntry("Schedule Workout", "", "", R.drawable.selectorminutestwo,  SCHEDULE_WORKOUT_NUMBER, null);
        }
        
        //
        // Setup the adapter.
        //
        EntryAdapter adapter = new EntryAdapter(this, items);
        setListAdapter(adapter);
        
        Log.d("RoutineSelectorActivity", "Exit Constructor");
    }
    
    public void setNewEntry(String routineName, String time, String requirments, int image, int routineNum, Routine routine) {
    	 items.add(new EntryItem(routineName, time, requirments, image, routineNum, routine) ); // Add requirements
    }
    
    @Override
    protected void onListItemClick(ListView l, View view, int position, long id) {
    	super.onListItemClick(l, view, position, id);
    	
    	if(!items.get(position).isSection()){
    		
    		EntryItem item = (EntryItem)items.get(position);
    		
    		//
    		// Might be the schedule alarm feature.
    		//
    		if (SCHEDULE_WORKOUT_NUMBER == item.routineNum) {
    			Intent i = new Intent(RoutineSelectorActivity.this, WorkoutAlarm.class);
	            startActivity(i);
	            return;
    		}
    		
    		//
    		// Display the new routine.
    		//
    		if (routines_.get(item.routineNum) != null) {
    			//
    			// Setup the exercises.
    			//
    			Routine routine = item.routine;
    			Intent intent = null;
    			if (routine.isVideo_ == false) {
        			//
        			// Display Routine View.
    				// 
        	 		intent = new Intent (view.getContext(), RoutineDisplay.class); 
        	 		RoutineDisplay.routine_ = routine;
    			}
    			else {
    			  /*	intent = new Intent (view.getContext(), VideoRoutineDisplay.class);
    				VideoRoutineDisplayer.routine_ = routine;
    				*/
    				intent = new Intent (view.getContext(), VideoRoutineDisplay.class);
    			}
    			
    			startActivity(intent);
      	    }
    	}
    	
    }
}