package com.s4.wyaw.routinedisplayer;

import java.util.Iterator;

import android.content.Context;
import android.util.Log;

import com.s4.wyaw.R;
import com.s4.wyaw.DashboardActivity;
import com.s4.wyaw.exercise.Exercise;
import com.s4.wyaw.exercise.Routine;

//
// Handles all the sounds for a routine. Specified the routine in the constructor.
//
public class RoutineSounds  {	
	
	//
	// Everything you need to play the routine sounds.
	//
	private int voiceSelected_ = 0;
	private Iterator<Exercise> exercisePos_ = null; 
	private Routine curRoutine_ = null;

    // 
	// Constructor that holds the sound manager, the routine, and the context we will be 
	// using for sound.
	//
	public RoutineSounds(Routine curRoutine, Context context, int voiceSelected) {
		curRoutine_ = curRoutine;
		voiceSelected_ = voiceSelected;
	}
	
	//
	// Set the voice we are using. Male, female, beep. 
	//
	public void setVoiceSelected(int voiceSelected, Exercise curExercise) {
		voiceSelected_ = voiceSelected;
		exercisePos_= curRoutine_.getExerciseIterator();
		Exercise ex = null;
		while (exercisePos_.hasNext() && ((ex = exercisePos_.next()) != curExercise))
		{}
		
		
		if (exercisePos_ != null) {
			loadExerciseSounds(ex);
		}
	}
	
	public int getVoiceSelected() {
		return voiceSelected_;
	}

	void loadFinishSound() {
		DashboardActivity.soundManager_.addSound(R.raw.finished, R.raw.finished);
	}
	
	//
	// Get all the sounds from this exercise and load it in to 
	// the sound manager. 
	//
	void loadExerciseSounds(Exercise exercise)
	{	
		//
		// Load the male sounds.
		//
		Iterator<Integer> iter = exercise.getMaleSounds().iterator(); 
		while (iter.hasNext()) {
			int maleSound = iter.next().intValue();
			Log.d("Load male sound value = ", ""+maleSound);
			
			if (DashboardActivity.soundManager_ != null){
				DashboardActivity.soundManager_.addSound(maleSound, maleSound);
			}
		}
		
		// 
		// load the female sounds.
		//
		iter = exercise.getFemaleSounds().iterator(); 
		while (iter.hasNext()) {
			int femaleSound = iter.next().intValue();
			Log.d("Load female sound value = ", ""+femaleSound );
		//	DashboardActivity.soundManager_.addSound(femaleSound, femaleSound);
			DashboardActivity.soundManager_.addSound(R.raw.startbeep, R.raw.startbeep);
		}
		
		//
		// load the rest sounds.
		//
		/*
		iter = exercise.getRestSounds().iterator(); 
		while (iter.hasNext()) {
			int restSound = iter.next().intValue();
			Log.d("Rest sound = ", ""+restSound );
			DashboardActivity.soundManager_.addSound(restSound, restSound);
		}
		*/
		
		//
		// Load the "preparing" male sounds.
		//
		if (exercise.getPreparedMaleSound() != null ) {
			DashboardActivity.soundManager_.addSound(exercise.getPreparedMaleSound(), exercise.getPreparedMaleSound());
		}
		//
		// Load the "preparing" female sounds.
		//
		if (exercise.getPreparedFemaleSound() != null) {
			DashboardActivity.soundManager_.addSound(R.raw.preparebeep, R.raw.preparebeep);
		}
	}
	
	//
	// Add a sound each time this is called. Until they have all been added for the current routine.
	//
	public boolean addNextRoutineSound()
	{	 
		Log.d("RoutineSounds::addNextRoutineSound", "Enter");
		
		if (exercisePos_ == null) {
			exercisePos_ = curRoutine_.getExerciseIterator();
		}
		
		if (!exercisePos_.hasNext()) {
			return false;
		}
		else {
			loadExerciseSounds(exercisePos_.next());
		}		
	
	   	Log.d("RoutineSounds::addNextRoutineSound", "Exit");
		return true;
	}
	
	//
	// Play the sound specified by the exercise.
	//
	public void playSound(Exercise curExercise) {
		Log.d("RoutineSounds::playSound", "Enter");
		
		//
    	// Play the sound.
    	//
		if (voiceSelected_ == 0 /* male */ && curExercise.hasNextMaleSound()) {
			DashboardActivity.soundManager_.playSound(curExercise.getNextMaleSound());
		}
		else if (voiceSelected_ == 1 /* beep */) {
			DashboardActivity.soundManager_.playSound(R.raw.startbeep); //curExercise.getNextFemaleSound());
		}
		/*
		else if (voiceSelected_ == 2 /* spanish ) {
			DashboardActivity.soundManager_.playSound(R.raw.spanish);
		}
		else { // beep.
			//DashboardActivity.soundManager_.playSound(R.raw.ok);
		}
		*/
		Log.d("RoutineSounds::playSound", "Exit");      	
	}
	
	public void playFinished() {
		
		if ( (voiceSelected_ == 0 /* male */  ) || (voiceSelected_ == 1 /* beep */)  ) {
			DashboardActivity.soundManager_.playSound(R.raw.finished);
		}
		
	}
	
	public void playPreparingSound(Exercise curExercise) {
		Log.d("RoutineSounds::playPreparingSound", "Enter");

		//
    	// Play the sound.
    	//
		if (voiceSelected_ == 0 /* male */) {

			if (curExercise.getPreparedMaleSound() != null) {
				DashboardActivity.soundManager_.playSound(curExercise.getPreparedMaleSound());
			}
		}
		else if (voiceSelected_ == 1 /* female */) {
		//	Toast.makeText(context_,"INSIDE FEMALE PLAYPREPARING", Toast.LENGTH_LONG).show();
			DashboardActivity.soundManager_.playSound(R.raw.preparebeep); //curExercise.getPreparedFemaleSound());
			/*	
			if (curExercise.getPreparedFemaleSound() != null) {
				DashboardActivity.soundManager_.playSound(R.raw.preparebeep); //curExercise.getPreparedFemaleSound());
			}*/
		}
		Log.d("RoutineSounds::playSound", "Exit");      	
		
	}
};
