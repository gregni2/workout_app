package com.s4.wyaw.exercise;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

 /**
  * 
  * Class: Routine 
  * Description: A routine has a name like 'Desk Stretches' or '10 minutes anywhere'. It is mostly made up of 
  * a series of Exercises.
  */
public class Routine {

	/**
	 * Private members. TODO: GSN. Android recommends not using getters and setters to save space. 
	 */
	private String name_;
	private String description_;
	private String category_;
	private String level_;
	private String time_;
	private String required_;
	private List<Exercise> exercises_;
	private int mSoundDescription_;
	private int wSoundDescription_;
	private int image_;

	public boolean isVideo_ = false;
	public boolean hasWarmup = true;
	
	public int getImage() {
		return image_;
	}

	public void setImage(int image_) {
		this.image_ = image_;
	}

	public String getRequired() {
		return required_;
	}

	public void setRequired(String required) {
		required_ = required;
	}
	public String getTime() {
		return time_;
	}

	public void setTime(String time) {
		time_ = time;
	}

	
	public String getLevel() {
		return level_;
	}

	public void setLevel(String level) {
		level_ = level;
	}
	
	public String getCategory() {
		return category_;
	}

	public void setCategory(String category) {
		category_ = category;
	}

	
	public Routine() { 
		exercises_ = new Vector<Exercise>();
	}
	
	public String getName() { return name_; }
	
	public void setName(String name) { name_ = name; }
	
	public String getDescription() {return description_; }
	
	public int getMaleSoundDescription() { return mSoundDescription_; }
	public void setMaleSoundDescription(int mSoundDescription) { mSoundDescription_ = mSoundDescription;}
	
	public int getFemaleSoundDescription() { return wSoundDescription_; }
	public void setFemaleSoundDescription(int wSoundDescription) { wSoundDescription_ = wSoundDescription;}
	
	public void setDescription(String description) { description_ = description; }
	
	public void addExercise(Exercise exercise) { exercises_.add(exercise); }
	
	public int getNumberExercises() { return exercises_.size(); }
	
	public Iterator<Exercise> getExerciseIterator() {return exercises_.iterator(); }
	
	public String toString() {
		return name_;
		
		/*
		String retString = "Name = " + name_ + "\nDescription" + description_ + "\n";
		
		Iterator<Exercise> iter = getExerciseIterator();
		while (iter.hasNext()) {
			retString += iter.next().toString();
			retString +="\n";
		}
		
		return retString; */
	}
	
	/**
	 * 
	 * @return return all the Exercises rest and active time added together.
	 */
	public int getTotalTime() {
		int totalTime = 0;
		
		Iterator<Exercise> iter = getExerciseIterator();
		while (iter.hasNext()) {
			Exercise exercise = iter.next();
			totalTime += exercise.getTimeActive();
			totalTime += exercise.getRestTime();
		}
	
		return totalTime;
	}
	
}
