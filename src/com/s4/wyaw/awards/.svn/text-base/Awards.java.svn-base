package com.s4.wyaw.awards;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;


public class Awards {


	private static final String PREFERENCES_AWARD_POINTS = "wewawardpoints";
	
	public static int currentPoints_ = 100;
	
    public static int getTotalPoints(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
    	currentPoints_ = preferences.getInt(PREFERENCES_AWARD_POINTS, 100);
    	return currentPoints_;
    }
    
    public static void setTotalPoints(Activity activity, int points) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        currentPoints_ = points;
    	preferences.edit().putInt(PREFERENCES_AWARD_POINTS, currentPoints_).commit();
    }
    
    public static String getLevelName(int points) {
    	String retString = new String("Couch Potato");
    	if (points > 300 && points < 600) {
    		retString = new String("Homebody");
    	}
    	else if (points > 600 && points < 1000){
    		retString = new String("World traveler");
    	}
    	
    	return retString;
    }
    
    public static AwardInformation getCurrentRangeAndLevelNameName(int points) {
    	AwardInformation awardInfo = new AwardInformation();
    	if (points > 0 && points < 300) {
    		awardInfo.levelName = "Shut-in";
    		awardInfo.minPoints = 0;
    		awardInfo.maxPoints = 300;	
       }
    	else if (points >= 300 && points < 600) {
    		awardInfo.levelName = "Homebody";
    		awardInfo.minPoints = 300;
    		awardInfo.maxPoints = 600;
    	}
    	
    	else if (points >= 600 && points < 1000){
    		awardInfo.levelName = "Toned Tourist";
    		awardInfo.minPoints = 600;
    		awardInfo.maxPoints = 1000;
    	}
    	else if (points >= 1000 && points < 1400){
    		awardInfo.levelName = "Tough Traveler";
    		awardInfo.minPoints = 1000;
    		awardInfo.maxPoints = 1400;
    	}
    	else if (points >= 1400 && points < 1800){
    		awardInfo.levelName = "Exploding Explorer";
    		awardInfo.minPoints = 1400;
    		awardInfo.maxPoints = 1800;
    	}
    	else if (points >=  1800 && points < 2200){
    		awardInfo.levelName = "Sculpted Scout";
    		awardInfo.minPoints = 1800;
    		awardInfo.maxPoints = 2200;
    	}
    	else if (points >=  2200){
    		awardInfo.minPoints = 2200;
    		awardInfo.maxPoints = 2600;
    		awardInfo.levelName = "World Famous Workouter";
    	}	
    	
    	return awardInfo;
    }
    
    public static int getPointsFromLevel(String routineLevel) {
    	int points = 0;
    	
    	 if (routineLevel.equalsIgnoreCase("beginner"))
         {
    		 points = 100;
         }
         else if (routineLevel.equalsIgnoreCase("intermediate"))
         {
        	 points = 200;
         }
         else if (routineLevel.equalsIgnoreCase("advanced"))
         {
        	 points = 300;
         }
        
    	 return points;
    }
    
    
}
