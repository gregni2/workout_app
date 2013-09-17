package com.s4.wyaw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.s4.wyaw.awards.Awards;
import com.s4.wyaw.exercise.Routine;
import com.s4.wyaw.exercise.Trainer;
import com.s4.wyaw.routinedisplayer.RoutineDisplay;


// A view that comes up after the routine is completed.  If awards have been
// earned, they are displayed here.  The user has the option to go to a 
// 'trophy room' view to see all awards earned.
public class CompletedView extends Activity
{
	// store for routine we have just finished.
	String routineName_;
	String routineLevel_;
	
	private class AdClickListener implements OnClickListener {
	    @Override
	    public void onClick(View v) {
	    	FlurryAgent.onEvent("Aylio Add Click");
	    	startActivity( new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.aylio.com/?utm_source=workoutapp&utm_medium=banner&utm_campaign=workout%2Bapp")) );
        }
	}
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        
       
        // Bundle passes in the name of the completed routine and its level of
        // difficulty.
        Bundle bundle = getIntent().getExtras();
        routineName_ =  bundle.getString("name");
        routineLevel_ = bundle.getString("level");
                     
        // Get all settings, including accomplishment counts, from the 
        // preferences database
        SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(this);
        
        // Get a handle to the preferences editor, for any updates.
        SharedPreferences.Editor editor = settings.edit();
        
        // Go through all items in the "shared preferences" store.  We will
        // need to figure out which of these are awards and build a list of
        // them.  This gives us the list of awards previously earned.
        awards = new ArrayList<String>();
        for (String k: settings.getAll().keySet())
        {   
            if (k.startsWith(AWARD_PREFIX))
            {
                awards.add(k);
            }
        }
        
        // We will fill this container with any new awards earned
        newAwards = new ArrayList<String>();
        
        // Now, update the count for the routine that was completed.
        // Preferences with the prefix ACCOMPLISHMENT_PREFIX are counts 
        // of completed routines.
        String key = ACCOMPLISHMENT_PREFIX+routineName_;
        int count = settings.getInt(key, 0); // 0 is default value   
        
        // Update database with accomplishment
        editor.putInt(key, ++count);
        editor.commit(); 
        
        // We earn an award for completing a routine for the first time (i.e.,
        // the count for this routine was incremented to 1).  If this happens,
        // also add the award to the database.
        if (count == 1)
        {
            String award = "";  // Default to empty
            
            if (routineLevel_.equalsIgnoreCase("beginner"))
            {
                newAwards.add("Bronze Medal");
                award = "awd.Bronze";
                Log.d("CompletedView", "Earned bronze");
            }
            else if (routineLevel_.equalsIgnoreCase("intermediate"))
            {
                newAwards.add("Silver Medal");
                award = "awd.Silver";
                Log.d("CompletedView", "Earned silver");
            }
            else if (routineLevel_.equalsIgnoreCase("advanced"))
            {
                newAwards.add("Gold Medal");
                award = "awd.Gold";
                Log.d("CompletedView", "Earned gold");
            }
            
            // Add award to database
            if (award.length() != 0)
            {
                int n = settings.getInt(award, 0);
                editor.putInt(award, ++n);
                editor.commit();
                
                //
                // Set the new points for this new routine. 
                //
                int points = Awards.getTotalPoints(this);
                points += Awards.getPointsFromLevel(routineLevel_);
           	    Awards.setTotalPoints(this, points);
           	
            }
        }
        
        // Get the view
        setContentView(R.layout.completed);

        // Add the routine name to the success line
        TextView view = (TextView) findViewById(R.id.completed_body_name);
        String newText = view.getText() + "\"" + routineName_ + "\"" + 
                         "\n\nfor the " + convertIntToOrdinal(count) + " time!";
        view.setText(newText);
        view.setKeepScreenOn(true);

        // This starts the dynamic part of the view.  I'm leaving open the 
        // possibility that we might be showing more than one award/trophy/
        // badge.  Since we have an unknown number of views coming up, we'll
        // start numbering each subsequent view with an ID starting at 10000.
        int id = 10000;

        // This layout is where we put the dynamic views.
        ViewGroup layout = (ViewGroup) findViewById(R.id.completed_innerrelativelayout);
        if (layout == null)
        {
            Log.e("CompletedView", "Null layout pointer");
            return;   
        }
        
        
        // This is the id of the last view, so that the next view can be placed
        // relative to it.
        int lastId = R.id.completed_body_name;
        
        // Post if one or more new awards were earned.
        if (!newAwards.isEmpty())
        {
            Iterator<String> it = newAwards.iterator();
            while(it.hasNext()) 
            {
                String award = it.next(); 
                
                // Text view if they have earned a new award
                TextView tv = new TextView(this);
                tv.setTextSize(20);
                tv.setText("You have earned a " + award);
                tv.setTextColor(ColorStateList.valueOf(0xFF000000));  // black font
                tv.setGravity(Gravity.CENTER_HORIZONTAL);             // centered
                RelativeLayout.LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, lastId);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.setMargins(0, 10, 0, 0); // left, top, right, bottom
                tv.setLayoutParams(params);

                // Give a unique id and add to parent layout
                lastId = id;
                tv.setId(id);
                ++id;
                layout.addView(tv);  
                
                // Add image view for this award
                ImageView iv = new ImageView(this);
                iv.setAdjustViewBounds(true);
                params = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
                params.addRule(RelativeLayout.BELOW, lastId);
                params.addRule(RelativeLayout.CENTER_HORIZONTAL);
                params.setMargins(0, 40, 0, 0); // left, top, right, bottom
                iv.setLayoutParams(params);
                
                if (award.contains("Bronze"))
                {
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.bronzemedal);
                    iv.setImageBitmap(b);
                }
                else if (award.contains("Silver"))
                {
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.silvermedal);
                    iv.setImageBitmap(b);  
                }
                else if (award.contains("Gold"))
                {
                    Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.goldmedal);
                    iv.setImageBitmap(b);
                }
                
                // Give a unique id and add to parent layout
                lastId = id;
                iv.setId(id);
                ++id;
                layout.addView(iv);  
            }  // }
        }
        
        // Empty new awards array, since after this it's not new any more.
        newAwards.clear();
        
        if (TrophySelectorList.adapter_!= null) {
        	TrophySelectorList.adapter_.notifyDataSetChanged();
        }
        
        // Listen for ads.
     //   AdClickListener adClickListener = new AdClickListener();
 	  //  findViewById(R.id.completed_bandad).setOnClickListener(adClickListener);
    
    }
    
    String convertIntToOrdinal (int i)
    {
        switch (i)
        {
            case 1:
                return "1st";
            case 2:
                return "2nd";
            case 3:
                return "3rd";
            default:
                return i + "th";
        }
    }
    
    @Override
    protected void onStart() {
    	super.onStart();
    	if (routineName_.contains("Stretch") || routineName_.contains("Warm Up")  || routineName_.contains("Desk") || routineName_.contains("Planes") ) {
    		this.findViewById(R.id.cooldown).setVisibility(View.GONE);
    	}
    	
   /* 	FlurryAgent.onStartSession(this, "WC41REE2S5H3HCRR3I4F");
		HashMap<String, String> eventArgs = new HashMap<String, String>();
		eventArgs.put("Where Displayed", "Completed View");	
		FlurryAgent.onEvent("Aylio ad displayed", eventArgs);*/
    }
    

	@Override
	protected void onStop() {
		super.onStop();
		
		FlurryAgent.onEndSession(this);
	}
	
	
    
    public void done(View view) 
    {
        finish();
        Intent intent = new Intent (this, DashboardActivity.class); 
        startActivity(intent);
        
    }
    
    public void seeTrophies(View view) 
    {
       // Intent intent = new Intent (this, TrophyView.class); 
        //startActivity(intent);
    }
    
    
    public void cooldownRoutine(View view) 
    {
    	Log.d("CompletedView", "cooldownRoutine called");
        Routine routine = Trainer.getTrainer(this).getRoutineFromName("5 Minute Cooldown Stretch");
    	if (routine == null) {
    		Log.e("CompletedView", "routine == null");
    		return;
    	}
    
    	//
		// Setup the exercises.
		//
		RoutineDisplay.routine_ = routine;
	
		//
		// Display Routine View.
		//
 		Intent intent = new Intent (view.getContext(), RoutineDisplay.class); 
		startActivity(intent);
    }
    
    private static ArrayList<String> awards;
    
    private static ArrayList<String> newAwards;
    
    // Items in the preferences database with this prefix are counts of 
    // workout accomplishments
    private static final String ACCOMPLISHMENT_PREFIX = "acm.";
    
    // Items in the preferences database with this prefix are awards that have
    // already been earned
    private static final String AWARD_PREFIX = "awd.";
}