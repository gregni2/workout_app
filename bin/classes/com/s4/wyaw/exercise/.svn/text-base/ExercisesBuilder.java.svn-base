package com.s4.wyaw.exercise;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.s4.wyaw.R;

 /**
  * Class: ExercisesBuilder. 
  * Description: builds the parts need for exercises (such as "left ear stretch"). 
  * It is the BuilderClass based on the Builder patter.
  * From http://en.wikipedia.org/wiki/Builder_pattern
  * 
  * This is mainly used by the Trainer class. You should use the Trainer class (a Director class)
  * instead of this class.
  */

public class ExercisesBuilder {
	
	//
	// Private members.
	//
	protected Map<String, Exercise> exercises_;
	
	
	ExercisesBuilder() {
		exercises_ = null;
	}
	
	public void buildExercises(Context context) {
		
		if (exercises_ == null) { 
			exercises_ = new HashMap<String, Exercise>();
			buildRoutineFromXml(context, R.xml.exercises);
		}
	}
	
	Exercise getExercise(String id) { return exercises_.get(id); }
	
	void printExercises(Context context) {
		if (exercises_ != null) {
			Iterator<Exercise> iter = exercises_.values().iterator();
			
			while (iter.hasNext()) {
				Exercise exercise = iter.next();
				Toast.makeText(context, "Exercise = "+ exercise.getId(), Toast.LENGTH_SHORT).show();
				Log.d("Exercise", "Exercise = " + exercise.getId());
				Log.d("Exercise", "\n\t name = " + exercise.getName());
				Log.d("Exercise", "\n\t description = " + exercise.getDescription());
				Log.d("Exercise", "\n\t time active = " + exercise.getTimeActive());
				Log.d("Exercise", "\n\t time rest = " + exercise.getRestTime());
				Log.d("Exercise", "\n\t picture = " + exercise.getPicture());
			}
		}
	}
	
	//
	// Tag names.
	//
	private static String exercisesTagName 				= "exercises";
	private static String exerciseTagName 				= "exercise";
	private static String shortDescriptionTagName 		= "short-description";
	private static String detailedDescriptionTagName 	= "detailed-description";
	private static String timeActiveTagName 			= "time-active";
	private static String timeRestTagName 				= "time-rest";
	private static String pictureTagName 				= "picture";
	private static String soundTagName 					= "sound";
	private static String preparedMaleSoundTagName 		= "preparing-male-sound";
	private static String preparedFemaleSoundTagName 	= "preparing-female-sound";
	private static String pictureListTagName			= "picture-list";
	private static String pictureEntryTagName			= "picture-entry";
	
	//
	// Create a routine from the xml file/resource.
	//
	void buildRoutineFromXml(Context context, int routineRes) {
		XmlPullParser xpp = context.getResources().getXml(routineRes);
		
		Exercise curEx = null; // exercise being built.
		
		try {
			
			//
			// Loop through each event.
			//	
			int eventType = xpp.getEventType();
			while(eventType != XmlPullParser.END_DOCUMENT) {
				
				//
				// Routine start. Set the name.
				//
				if(eventType == XmlPullParser.START_DOCUMENT) {
	                Log.d("buildRoutineFromXML", "In start document");
	            }
				
				//
				// Many tags. Can be exercise start. Or the sound associated with an exercise ... 
				//
				else if(eventType == XmlPullParser.START_TAG) {
	                Log.d("buildRoutineFromXML", "In start tag = "+xpp.getName());
	                String tagName = xpp.getName();
	                
	                //
	                // Possible exercises tags.
	                //
	                if (tagName.equals(exercisesTagName)) {
	                }
	                
	                //
	                // New exercise.
	                //
	                if (tagName.equals(exerciseTagName)) {
	                	curEx = new Exercise();
	                	
	                	// id
	                   	String id = xpp.getAttributeValue(null, "id");
	                	if (id != null) {
	    	        		Log.d("exercise", id);
	                		curEx.setid(id);
	                	}
	                	
	                	// name
	                   	String name = xpp.getAttributeValue(null, "name");
	                	if (name != null) {
	    	        		Log.d("exercise", name);
	                		curEx.setName(name);
	                	}
	                }
	                // active time 
	                else if (curEx != null && tagName.equals(timeActiveTagName)) {
	                	Log.d("Active time", "name");
	                	eventType = xpp.next();
	                	if (eventType != XmlPullParser.TEXT) {
	                		throw new Exception("Failed time-active parsing");
	                	}
	                			
	                	String text = xpp.getText();	
	    	            if (text != null) {
	    	            	Log.d("text = ", text);
	    	            	curEx.setTimeActive(Integer.parseInt(text));
	    	            }
	                }
	                // rest time.
	                else if (curEx != null && tagName.equals(timeRestTagName)) {
	                	Log.d("rest time ", "name");
	                	eventType = xpp.next();
	                	if (eventType != XmlPullParser.TEXT) {
	                		throw new Exception("Failed time-rest parsing");
	                	}
	                			
	                	String text = xpp.getText();	
	    	            if (text != null) {
	    	            	Log.d("text = ", text);
	    	            	curEx.setRestTime(Integer.parseInt(text));
	    	            }
	                } 
	                // short description.
	                else if (curEx != null && tagName.equals(shortDescriptionTagName)) {
	                	Log.d("short description", "name");
	                	eventType = xpp.next();
	                	if (eventType != XmlPullParser.TEXT) {
	                		throw new Exception("Failed short description parsing");
	                	}
	                			
	                	String text = xpp.getText();	
	    	            if (text != null) {
	    	            	Log.d("text = ", text);
	    	            	curEx.setShortDescription_(text);
	    	            }
	                }
	               // detailed description.
	                else if (curEx != null && tagName.equals(detailedDescriptionTagName)) {
	                	Log.d("detailed description", "name");
	                	eventType = xpp.next();
	                	if (eventType != XmlPullParser.TEXT) {
	                		throw new Exception("Failed detailed description parsing");
	                	}
	                			
	                	String text = xpp.getText();	
	    	            if (text != null) {
	    	            	Log.d("text = ", text);
	    	            	curEx.setDescription(text);
	    	            }
	                }
	                // picture. 
	                else if (curEx != null && tagName.equals(pictureTagName)) {
	                	Log.d("Picture Tag work ", "name");
	                		eventType = xpp.next();
	                		if (eventType != XmlPullParser.TEXT) {
	                			throw new Exception("Failed parsing picture");
	                		}
	                			
	                		String text = xpp.getText();
	    	                if (text != null) {
	    	                	Log.d("text = ", text);

	    	    				int resID = context.getResources().getIdentifier(text, "drawable", "com.s4.wyaw");
	    	                	Log.d("id = ", ""+resID);
	    	                	curEx.setPicture(resID);
	    	                }
	                }
	                // picture list
	                else if (curEx != null && tagName.equals(pictureListTagName)) { // TODO: picture name.
	                	Log.d("Picture List Tag work ", "name");
	                	curEx.pictureList_ = new Vector<Integer>();
	                }
	                else if (curEx != null && tagName.equals(pictureEntryTagName) && curEx.pictureList_ != null) {
	                		Log.d("Picture Entry", "Picture entry" + curEx.pictureList_.size());
		                
	                		eventType = xpp.next();
	                		if (eventType != XmlPullParser.TEXT) {
	                			throw new Exception("Failed parsing picture");
	                		}
	                			
	                		String text = xpp.getText();
	    	                if (text != null) {
	    	                	Log.e("text = ", text);

	    	    				int resID = context.getResources().getIdentifier(text, "drawable", "com.s4.wyaw");
	    	                	Log.e("id = ", ""+resID);
	    	                	curEx.pictureList_.add(resID);
	    	                	Log.d("New List now size", "size = " + curEx.pictureList_.size());
	    	                }
	                }
	                else if (curEx != null && tagName.equals(preparedMaleSoundTagName)) {
	                	eventType = xpp.next();
	                	if (eventType != XmlPullParser.TEXT) {
                			Log.d("No male sound for ", tagName);
                		}
	                	else {
                			
	                		String text = xpp.getText();
	                		if (text != null) {
	                			Log.d("sound = ", text);
	                			int resID = context.getResources().getIdentifier(text, "raw", "com.s4.wyaw");
	                			Log.d("id = ", ""+resID);
	                			curEx.setPreparedMaleSound(resID);
	                		}
	                	}
	                }
	                else if (curEx != null && tagName.equals(preparedFemaleSoundTagName)) {
	                	eventType = xpp.next();
                		if (eventType != XmlPullParser.TEXT) {
                			Log.d("No female sound for ", tagName);
                		}
	                	else {
	                			
	                		String text = xpp.getText();
	    	                if (text != null) {
	    	                	Log.d("sound = ", text);
	    	                	int resID = context.getResources().getIdentifier(text, "raw", "com.s4.wyaw");
	    	                	Log.d("id = ", ""+resID);
	    	                	curEx.setPreparedFemaleSound(resID);
	    	                }
	                	}
	                }
	                
	                // Sound. 
	                else if (curEx != null && tagName.equals(soundTagName)) {
	                	Log.d("Sound Tag work ", "name");
	                	String name = xpp.getAttributeValue(null, "name");
	                	if (name != null) {
	                		Log.d("sound", name);
	          	 
	                		eventType = xpp.next();
	                		if (eventType != XmlPullParser.TEXT) {
	                	//		throw new Exception("Failed parsing sound");
	                			Log.d("Xml parsing", "No text associated with sound");
	                		}
	                			
	                		String text = xpp.getText();
	    	                if (text != null) {
	    	                	Log.d("text = ", text);
	    	                	int resID = context.getResources().getIdentifier(text, "raw", "com.s4.wyaw");
	    	                	Log.d("id = ", ""+resID);
	    	                
	    	                	if (name.equals("male")) {
	    	                		Log.d("adding", "male sound");
	    	    	                curEx.addMaleSound(resID);
	    	                	}
	    	                	else if (name.equals("female")) {
	    	                		Log.d("adding", "female sound");
		    	    	            curEx.addFemaleSound(resID);
	    	                	}
	    	                	else if (name.equals("rest")) {
	    	                		Log.d("adding", "rest sound");
		    	    	            curEx.addRestedSound(resID);
	    	                	}
	    	                }
	                	}
	                }
	            }
				
				// Add the exercise if possible.
	            else if(eventType == XmlPullParser.END_TAG) {
	                Log.d("buildRoutineFromXML",  "In end tag = "+xpp.getName());
	                
	                if (curEx != null && xpp.getName().equals(exerciseTagName) ) {
	                	Log.d("Adding to routine exercise", curEx.getName());
	                	exercises_.put(curEx.getId(), curEx);
	                }
	            }
	            else if(eventType == XmlPullParser.TEXT) {
	                Log.d("buildRoutineFromXML",  "Have text = "+xpp.getText());
	            }
	    	  eventType = xpp.next();
	    	 }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
