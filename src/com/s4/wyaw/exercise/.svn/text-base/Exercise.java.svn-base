package com.s4.wyaw.exercise;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Vector;

/**
 * Class: Exercise
 * Description: describes completely the Exercise to be performed. It includes the name, times, and
 * even the resource id's need for the exercise's sound and picture.
 */
public class Exercise {
	/**
	 * Private members.
	 */
	private String 		id_;
	private String 		name_;
	private String 		description_;
	private boolean 	performed_;
	private int 		timeActive_;
	private int 		restTime_;
	private String 		shortDescription_;
	private int 		picture_;
	private boolean 	rested_;
	private String 		videoUrl_;
	private Queue<Integer> soundsFemale_;
	private Queue<Integer> soundsMale_;
	private Queue<Integer> soundsRest_;	
	private Integer preparedMaleSound_;
	private Integer preparedFemaleSound_;
	public  Vector<Integer>  pictureList_; // Google actually recommends getting rid of setters and getters. So I'm starting here.
	public String		goal_;
	

	Exercise(Exercise orig) {
		id_ = orig.id_;
		name_ = orig.name_;
		description_ = orig.description_;
		performed_ = orig.performed_;
		timeActive_ = orig.timeActive_;
		restTime_ = orig.restTime_;
		shortDescription_ = orig.shortDescription_;
		picture_ = orig.picture_;
		rested_ = orig.rested_;
		videoUrl_ = orig.videoUrl_;
		preparedMaleSound_ = orig.preparedMaleSound_;
		preparedFemaleSound_ = orig.preparedFemaleSound_;
		
		soundsFemale_ = new PriorityQueue<Integer>();
		soundsFemale_.addAll(orig.soundsFemale_);
		soundsMale_  = new PriorityQueue<Integer>();
		soundsMale_.addAll(orig.soundsMale_);
		soundsRest_  = new PriorityQueue<Integer>();
		soundsRest_.addAll(orig.soundsRest_);
		goal_ = orig.goal_;
		pictureList_ = null;
		if (orig.pictureList_ != null) {
			pictureList_ = new Vector<Integer>();
			pictureList_.addAll(orig.pictureList_);
		}
	}

	
	
	public Integer getPreparedMaleSound() {
		return preparedMaleSound_;
	}


	public void setPreparedMaleSound(Integer preparedMaleSound) {
		preparedMaleSound_ = preparedMaleSound;
	}


	public Integer getPreparedFemaleSound() {
		return preparedFemaleSound_;
	}


	public void setPreparedFemaleSound(Integer preparedFemaleSound) {
		preparedFemaleSound_ = preparedFemaleSound;
	}
	
	public boolean isRested() {
		return rested_;
	}

	public void addFemaleSound(int sound)
	{
		soundsFemale_.add(new Integer(sound));
	}
	public void addMaleSound(int sound)
	{
		soundsMale_.add(new Integer(sound));
	}
	public void addRestedSound(int sound)
	{
		soundsRest_.add(new Integer(sound));
	}
	
	public boolean hasNextFemaleSound() {
		return soundsFemale_.peek() != null;
	}
	
	public int getNextFemaleSound() {
		return soundsFemale_.peek().intValue();
	}
	
	public boolean hasNextMaleSound() {
		return soundsMale_.peek() != null;
	}
	public int getNextMaleSound() {
		return soundsMale_.peek().intValue();
	}

	public int getNextRestSound() {
		return soundsRest_.peek().intValue();
	}
	
	public void setRested(boolean rested) {
		rested_ = rested;
	}

	public Exercise() {
		name_ = "";
		description_ = "";
		performed_ = false;
		rested_ = false;
		timeActive_ = 0;
		restTime_ = 0;
		shortDescription_ = "";
		picture_ = 0;
		soundsFemale_ = new PriorityQueue<Integer>();
		soundsMale_  = new PriorityQueue<Integer>();
		soundsRest_  = new PriorityQueue<Integer>();
		goal_="";
	}
	
	   public Exercise(String id,
			   		   String name,
	                   String description,
	                   int pictureResourceId,
	                   String videoUrl) 
	   {
		    id_ = id;
	        name_ = name;
	        description_ = description;
	        videoUrl_ = videoUrl;
	        performed_ = false;
	        rested_ = false;
	        timeActive_ = 0;
	        restTime_ = 0;
	        shortDescription_ = "";
	        picture_ = pictureResourceId;
			soundsFemale_ = new PriorityQueue<Integer>();
			soundsMale_  = new PriorityQueue<Integer>();
			soundsRest_  = new PriorityQueue<Integer>();
			goal_ = "";
	    }

	public String getName() {
		return name_;
	}
	
	public Queue<Integer> getMaleSounds() { return soundsMale_; }
	public Queue<Integer> getFemaleSounds() { return soundsMale_; }
	public Queue<Integer> getRestSounds() { return soundsMale_; }
	

	public void setid(String id) {
		id_ = id;
	}
	public String getId() {
		return id_;
	}
	public void setName(String name) {
		name_ = name;
	}
	public String getDescription() {
		return description_;
	}

	public void setDescription(String description) {
		description_ = description;
	}

	public boolean isPerformed() {
		return performed_;
	}

	public void setPerformed(boolean performed) {
		performed_ = performed;
	}
	
	public int getTimeActive() {
		return timeActive_;
	}

	public void setTimeActive(int timeActive) {
		timeActive_ = timeActive;
	}

	public int getRestTime() {
		return restTime_;
	}

	public void setRestTime(int restTime) {
		restTime_ = restTime;
	}
	public String getShortDescription_() {
		return shortDescription_;
	}

	public void setShortDescription_(String shortDescription) {
		shortDescription_ = shortDescription;
	}

	public int getPicture() {
		return picture_;
	}

	public void setPicture(int picture) {
		picture_ = picture;
	}
	
	public String getVideoUrl()
	{
	    return videoUrl_;
	}
	
	public void setVideoUrl(String url)
	{
	    videoUrl_ = url;
	}
	
	@Override
	public String toString() {
		return "Exercise Name = " + name_ + " description = " + description_ +
			   "performed = " + performed_ + "timeActive = " + timeActive_ +
			   "resetActive = " + restTime_ + "shortDescription" + videoUrl_ +  " goal = " + goal_ +
			   "\n";
	}

};
