package com.s4.wyaw.exercise;

import org.xmlpull.v1.XmlPullParser;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;


 /**
  * Class: RoutineBuilder. 
  * Description: builds the parts need for routines (such as "Desk Stretch"). 
  * It is the BuilderClass based on the Builder patter.
  * From http://en.wikipedia.org/wiki/Builder_pattern
  * 
  * This is mainly used by the Trainer class. You should use the Trainer class (a Director class)
  * instead of this class.
  */

public class RoutineBuilder {
	
	//
	// Private members.
	//
	protected Routine routine_;
	protected ExercisesBuilder exerciseBuilder_;
	
	
	RoutineBuilder(ExercisesBuilder exerciseBuilder) {
		exerciseBuilder_ = exerciseBuilder;
	}
	
	public Routine getRoutine() {
			return routine_;
	}
	
	public void buildBasics(String name, String description) {
		routine_.setName(name);
		routine_.setDescription(description);
	}
	
	//
	// Tag names.
	//
	private static String routineTagName 			= "routine";
	private static String routineLevelTagName 		= "level";
	private static String routineTimeTagName 		= "time";
	private static String routineIntroAudioTagName 	= "intro-audio";
	private static String introDescriptionTagName 	= "intro-description";
	private static String exerciseTagName 			= "exercise";
	private static String timeActiveTagName 		= "time-active";
	private static String timeRestTagName 			= "time-rest";
	private static String routineRequiredTimeTagName = "requirements";
	private static String routineImageTagName        = "image";
	private static String routineVideoTagName        = "isvideo";
	private static String routineHasWarmupTagName	= "has-warmup";
	private static String exerciseGoal				= "goal";
	
	//
	// Create a routine from the xml file/resource.
	//
	Routine buildRoutineFromXml(Context context, int routineRes) {
		routine_ = new Routine();
		Exercise curEx = null;
		XmlPullParser xpp = context.getResources().getXml(routineRes);
		
		
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
	                // Possible routine tags.
	                //
	                if (tagName.equals(routineTagName)) {
	                	String name = xpp.getAttributeValue(null, "name");
	                	if (name != null) {
	    	                Log.d("routine name", name);
	    	                routine_.setName(name);
	                	}
	                	
	                	String cat = xpp.getAttributeValue(null, "category");
	                	if (cat != null) {
	    	                Log.d("routine caegory", cat);
	    	                routine_.setCategory(cat);
	                	}	
	                }
	                else if (tagName.equals(introDescriptionTagName)) {
	      	    	    eventType = xpp.next();
	                	String text = xpp.getText();
	                	if (text != null) {
	                		Log.d(introDescriptionTagName, text);
	                		routine_.setDescription(text);
	                	}
	                }
	                else if (tagName.equals(routineIntroAudioTagName)) {
	                	String text = xpp.getText();
	                	if (text != null) {
	                		Log.d(routineIntroAudioTagName, text);
	                		int resID = context.getResources().getIdentifier(text, "raw", "com.s4.wyaw");
	                		Log.d("id = ", ""+resID);
	                	}
	                }	
	                else if (tagName.equals(routineVideoTagName)) {
	                    eventType = xpp.next();
	                    String text = xpp.getText();
	                	Log.d(routineVideoTagName, "isvideo = " + text);
                	   	if (text != null) {
	                		Log.d(routineLevelTagName, text);
	                		routine_.isVideo_ = text.equalsIgnoreCase("true");
                	   	}
		            }	
	                else if (tagName.equals(routineLevelTagName)) {
	                	eventType = xpp.next();
	                	String text = xpp.getText();
	                	if (text != null) {
	                		Log.d(routineLevelTagName, text);
	                		routine_.setLevel(text);
	                	}
	                }	
	                else if (tagName.equals(routineImageTagName)) {
	                	eventType = xpp.next();
	                	String text = xpp.getText();
	                	if (text != null) {
	                		Log.d(routineImageTagName, text);
	                		int resID = context.getResources().getIdentifier(text, "drawable", "com.s4.wyaw");
    	                	routine_.setImage(resID);
	                	}	                	
	                }
	                else if (tagName.equals(routineHasWarmupTagName)) {
	                	 eventType = xpp.next();
		                 String text = xpp.getText();
		                 Log.d(routineHasWarmupTagName, "has-warmup = " + text);
	                	 if (text != null) {
		                	Log.d(routineHasWarmupTagName, text);
		                	routine_.hasWarmup = text.equalsIgnoreCase("true");
	                	 }	
	                }
	                else if (tagName.equals(routineTimeTagName)) {
	                	eventType = xpp.next();
	                	String text = xpp.getText();
	                	if (text != null) {
	                		Log.d(routineTimeTagName, text);
	                		routine_.setTime(text);
	                	}
	                }	
	                else if (tagName.equals(routineRequiredTimeTagName)) {
	                	eventType = xpp.next();
	                	String text = xpp.getText();
	                	if (text != null) {
	                		Log.d(routineRequiredTimeTagName, text);
	                		routine_.setRequired(text);
	                	}
	                }     
	                //
	                // Possible exercise tags.
	                //
	                
	                // New exercise.
	                if (tagName.equals(exerciseTagName)) {
	                	String id = xpp.getAttributeValue(null, "id");
	                	if (id != null) {
	    	        		Log.d("exercise", id);
	    	        		curEx = new Exercise(exerciseBuilder_.getExercise(id));
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
	                // goal 
	                else if (curEx != null && tagName.equals(exerciseGoal)) {
	                	Log.d("Goal", "name");
	                	eventType = xpp.next();
	                	if (eventType != XmlPullParser.TEXT) {
	                		throw new Exception("Failed time-active parsing");
	                	}
	                			
	                	String text = xpp.getText();	
	                	if (text != null) {
	    	            	Log.d("text = ", text);
	    	            	curEx.goal_ = text;
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
				}				
				// Add the exercise if possible.
	            else if(eventType == XmlPullParser.END_TAG) {
	                Log.d("buildRoutineFromXML",  "In end tag = "+xpp.getName());
	                
	                if (curEx != null && xpp.getName().equals(exerciseTagName) ) {
	                	Log.d("Adding to routine exercise", curEx.getName());
	                	routine_.addExercise(curEx);
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
	
		return routine_;
	}
	
}
