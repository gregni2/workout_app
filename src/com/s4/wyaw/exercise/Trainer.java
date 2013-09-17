package com.s4.wyaw.exercise;

import java.util.Iterator;
import java.util.Vector;

import com.s4.wyaw.R;

import android.content.Context;

/**
 * 
 * Class: Trainer.
 * Description: This class builds a Routine given a particular RoutineType type. This class (and method constructRoutine) shoud be used 
 * to build your routines with all it's exercises. 
 * 
 * Example: Trainer trainer = new Trainer();
 *          Routine routine = trainer.constructRoutine(RoutineType.DeskStrech);
 *          displayRoutine(routine);
 * Based on the The Director class.
 * http://en.wikipedia.org/wiki/Builder_pattern
 */

public class Trainer {
	private RoutineBuilder routineBuilder_;
	private ExercisesBuilder exercisesBuilder_;
	private Vector<Routine> routines_;

	public static Trainer trainer_;
	
	public static Trainer getTrainer(Context context) { 
		if (trainer_ == null) {
			trainer_ = new Trainer(context); 
		}
		
		return trainer_;
	} 
	
	public static int sGroupLoaded = 0;

	public void loadFirstGroupOfRoutines(Context context) {
		Routine routine = null;

	//	routine = routineBuilder_.buildRoutineFromXml(context, R.xml.bandshoulderchest);
		//routines_.add(routine);
		
		
		
		routine = routineBuilder_.buildRoutineFromXml(context, R.xml.officedeskstretchroutine);
		routines_.add(routine);
		routine = routineBuilder_.buildRoutineFromXml(context, R.xml.officebeginnerworkaholic);
		routines_.add( routine); 
		routine = routineBuilder_.buildRoutineFromXml(context, R.xml.officeletsmoveroutine);
		routines_.add(routine);
		routine = routineBuilder_.buildRoutineFromXml(context, R.xml.officeintermediateworkaholic);
		routines_.add(routine);
		
		routine = routineBuilder_.buildRoutineFromXml(context, R.xml.minutescooldown);
		routines_.add(routine);
		routine = routineBuilder_.buildRoutineFromXml(context, R.xml.minutesfivewarmup);
		routines_.add(routine);
		routine = routineBuilder_.buildRoutineFromXml(context, R.xml.minutestenminutebeginner);
		routines_.add(routine);

		routine = routineBuilder_.buildRoutineFromXml(context, R.xml.minutesfifteenminuteintermediate);
		routines_.add(routine);
		routine = routineBuilder_.buildRoutineFromXml(context, R.xml.minutestwentyminuteadvanced);
		routines_.add(routine);
		
		routine = routineBuilder_.buildRoutineFromXml(context, R.xml.chestandarms);
		routines_.add(routine);
		
		routine = routineBuilder_.buildRoutineFromXml(context, R.xml.abblaster);
		routines_.add(routine);
	
		sGroupLoaded = 1;
	}
	public void loadSecondGroupOfRoutines(Context context) {

		if (sGroupLoaded == 1) {
			Routine routine = null;
	
			//
			// Hotel.
			//
			routine = routineBuilder_.buildRoutineFromXml(context, R.xml.hotelbedstretch);
			routines_.add(routine);
			routine = routineBuilder_.buildRoutineFromXml(context, R.xml.bedstretch);
			routines_.add(routine);
			routine = routineBuilder_.buildRoutineFromXml(context, R.xml.hotelquietwarmup);
			routines_.add(routine);
			routine = routineBuilder_.buildRoutineFromXml(context, R.xml.hotelbeginnerworkout);
			routines_.add(routine);
			routine = routineBuilder_.buildRoutineFromXml(context, R.xml.hotelintermediateworkout);
			routines_.add(routine);
			
			//
			// Latest.
			//
		//	routine = routineBuilder_.buildRoutineFromXml(context, R.xml.superhardtshirt);
			//routines_.add(routine);

			//
			// Plane.
			//
			routine = routineBuilder_.buildRoutineFromXml(context, R.xml.planebeginner);
			routines_.add(routine);
			routine = routineBuilder_.buildRoutineFromXml(context, R.xml.planeletsmove);
			routines_.add(routine);
		
			//
			// Just a bit of equipment.
			//
			routine = routineBuilder_.buildRoutineFromXml(context, R.xml.equipintermediate);
			routines_.add(routine);
			routine = routineBuilder_.buildRoutineFromXml(context, R.xml.equipadvanced);
			routines_.add(routine);
			
			routine = routineBuilder_.buildRoutineFromXml(context, R.xml.buttblaster);
			routines_.add(routine);
		
			sGroupLoaded++;
		}
	}
	
	public Trainer( Context context) {
		
		//
		// Build the exercises.
		//
		exercisesBuilder_ = new ExercisesBuilder();
		exercisesBuilder_.buildExercises(context);
	
		//
		// Build the routines.
		//
		routines_ = new Vector<Routine>(); // new HashMap<String, Routine>();
		
		//
		// Office workouts.
		//
		routineBuilder_ = new RoutineBuilder(exercisesBuilder_);
		loadFirstGroupOfRoutines(context);
		
	}
	
	/*
	public Routine getRoutine(String name) {
		return routines_.get(name);
	} */
	
	public Vector<Routine> getRoutines(String category)
	{
		Iterator<Routine> iter = routines_.iterator(); 
		Vector<Routine> routines = new Vector<Routine>(); 
		category = category.toUpperCase();
		while (iter.hasNext()) {
			Routine routine = iter.next();
			String routineCategory = routine.getCategory().toUpperCase();
			
			if (routine.getCategory() != null && routineCategory.contains(category)) {
				routines.add(routine);
			}
		}
		
		return routines;
	}
	
	public Routine getRoutineFromName(String name) {
		Routine retRoutine = null;
		
		Iterator<Routine> iter = routines_.iterator(); // values().iterator();
		while (iter.hasNext() && retRoutine == null) {
			Routine routine = iter.next();
			if (routine.getCategory() != null && routine.getName().equalsIgnoreCase(name)) {
				retRoutine = routine;
			}
		}
		
		return retRoutine;
	}
	
	public Vector<Routine> getAllRoutines() {
		return routines_;
	}
}
