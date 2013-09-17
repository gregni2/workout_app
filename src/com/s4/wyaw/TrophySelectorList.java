package com.s4.wyaw;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.flurry.android.FlurryAgent;
import com.s4.wyaw.awards.AwardInformation;
import com.s4.wyaw.awards.Awards;
import com.s4.wyaw.dialogs.TextProgressBar;
import com.s4.wyaw.exercise.Routine;
import com.s4.wyaw.exercise.Trainer;
import com.s4.wyaw.routinedisplayer.RoutineDisplay;

//
// List the routines in a category to let the user choose one 
// of the routines to begin.  
//

public class TrophySelectorList extends ListActivity 
{
	//
	// Trainer to provide the list of routines.
	//
    private Trainer trainer_ = null;
    Vector<Routine> routines_ = null;
    static public EfficientAdapter adapter_ = null;
    private TextProgressBar progressBar_;
    
    //
    // Class to provide our fancy list.
    //
	public static class EfficientAdapter extends BaseAdapter {
	        private LayoutInflater mInflater;
	        @SuppressWarnings("unused")
			private Bitmap mIcon1;
	        private Vector<Routine> routines_ = null;	    
	        
	        // Items in the preferences database with this prefix are counts of 
	        // workout accomplishments
	        private static final String ACCOMPLISHMENT_PREFIX = "acm.";
	        
	        // Items in the preferences database with this prefix are awards that have
	        // already been earned
	        
	        private static ArrayList<String> awards_;

	        public EfficientAdapter(Context context, Vector<Routine> routines) { 
		        routines_ = routines;
	            mInflater = LayoutInflater.from(context);
	        
	            // TODO: remove me.
	        	// Icons bound to the rows.
	            mIcon1 = BitmapFactory.decodeResource(context.getResources(), R.drawable.icon);
	       
	            //
	            // save all the routines that have been awarded.
	            // Get all settings, including accomplishment counts, from the 
	            // preferences database
	            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
	            
	            // Get a handle to the preferences editor, for any updates.
	          //  SharedPreferences.Editor editor = settings.edit();
	            
	            // Go through all items in the "shared preferences" store.  We will
	            // need to figure out which of these are awards and build a list of
	            // them.  This gives us the list of awards previously earned.
	            awards_ = new ArrayList<String>();
	            for (String k: settings.getAll().keySet())
	            {   
	                if (k.startsWith(ACCOMPLISHMENT_PREFIX))
	                {
	                    awards_.add(k);
	                }
	            }
	            
	            
	        }

	        /**
	         * The number of items in the list is determined by the number of speeches
	         * in our array.
	         *
	         * @see android.widget.ListAdapter#getCount()
	         */
	        public int getCount() {
	        	return routines_.size();	        
	           }

	        /**
	         * Since the data comes from an array, just returning the index is
	         * sufficent to get at the data. If we were using a more complex data
	         * structure, we would return whatever object represents one row in the
	         * list.
	         *
	         * @see android.widget.ListAdapter#getItem(int)
	         */
	        public Object getItem(int position) {
	            return position;
	        }

	        /**
	         * Use the array index as a unique id.
	         *
	         * @see android.widget.ListAdapter#getItemId(int)
	         */
	        public long getItemId(int position) {
	            return position;
	        }

	        /**
	         * Make a view to hold each row.
	         *
	         * @see android.widget.ListAdapter#getView(int, android.view.View,
	         *      android.view.ViewGroup)
	         */
	        public View getView(int position, View convertView, ViewGroup parent) {
	            // A ViewHolder keeps references to children views to avoid unneccessary calls
	            // to findViewById() on each row.
	            ViewHolder holder;

	            // When convertView is not null, we can reuse it directly, there is no need
	            // to reinflate it. We only inflate a new View when the convertView supplied
	            // by ListView is null.
	            if (convertView == null) {
	                convertView = mInflater.inflate(R.layout.award_list_item_icon_text, null); /* Here is my special view. We could change this if we wanted to different views */
	            } 
	            
                // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.topLineFirst);
                holder.bottomText = (TextView) convertView.findViewById(R.id.bottomLine);
                holder.icon = (ImageView) convertView.findViewById(R.id.icon);
	            
                //
                // Fill out the images and text from the routine in the vector.
                //
                if (position < routines_.size()) {
                	Routine currentRoutine = routines_.get(position);
	                
	                //
	                // Indicate number of points.
	                //
	                String titleText = currentRoutine.getName();
	                if (currentRoutine.getLevel().equalsIgnoreCase("beginner")) {
	                	titleText += " (100 points)";
	                }
	                else if (currentRoutine.getLevel().equalsIgnoreCase("intermediate")) {
	                	titleText += " (200 points)";
			                	
	                }
	                else {
	                	titleText += " (300 points)";
	                }
	                holder.text.setText(titleText);
	               
	                //
	                // Set the description of routine.
	                //
	                holder.bottomText.setText(currentRoutine.getDescription());
	                
	                //
	                // Set the image.
	                //
	                String routineName = currentRoutine.getName();
	                if (hasBeenCompleted(routineName, convertView.getContext())) {
	                   	int resource = getAwardIconResource(currentRoutine, convertView.getContext());
	                   	holder.icon.setImageResource(resource);     
	                }
	                else {
	                   	holder.icon.setImageResource(R.drawable.nomedal);     
		                	
	                }
	                convertView.setTag(holder);
	            } else {
	                // Get the ViewHolder back to get fast access to the TextView
	                // and the ImageView.
	                holder = (ViewHolder) convertView.getTag();
	            }
	            

	            return convertView;
	        }
	        
	        
	        // 
	        // Has the routine already been run? 
	        //
	        boolean hasBeenCompleted(String routineName, Context context) {
	        	boolean found = false;
	            Iterator<String> iter = awards_.iterator();
	            while (iter.hasNext() && !found) {
	            	String name = iter.next();
	                found = name.contains(routineName);
	            }
	            
	            return found;
	        }
	        
	        //
	        // Get a bronze, gold, or Silver medal. Depending on the routine.
	        //
	        int getAwardIconResource(Routine routine, Context context) {
	        	String level = routine.getLevel();
                
	        	int iconResource = R.drawable.nomedal;
	        	if (level.equalsIgnoreCase("Beginner")) {
	        		iconResource = R.drawable.bronzemedal;
	        	}
	        	else if (level.contains("Intermediate")) {
	        		iconResource = R.drawable.silvermedal;
	        	}
	        	else if (level.contains("Advanced")) {
	        		iconResource = R.drawable.goldmedal;
	        	}
	        	
	        	return iconResource;
	        }

	        static class ViewHolder {
	            TextView text;
	            TextView bottomText;
	            ImageView icon;
	        }
	    }	

    
	/*
	 * TODO: Delete or add back.
	private class AdClickListener implements OnClickListener {
	    @Override
	    public void onClick(View v) {
	    	FlurryAgent.onEvent("Aylio Add Click");
	    	startActivity( new Intent(Intent.ACTION_VIEW).setData(Uri.parse("http://www.aylio.com/?utm_source=workoutapp&utm_medium=banner&utm_campaign=workout%2Bapp")) );
        }
	}
	*/

    
    //
	// Called when the activity is first created.
	//
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("ThrophySelectorList", "Enter Constructor");

        setContentView(R.layout.trophytodo);

        
        //
        // The trainer will provide the list of all the routines.
        //
        trainer_ = Trainer.getTrainer(this);
        trainer_.loadSecondGroupOfRoutines(this);
        routines_ = trainer_.getAllRoutines();
        
        //
        // Put the passed-in category into the title
        //
        int points = Awards.getTotalPoints(this);
		AwardInformation info = Awards.getCurrentRangeAndLevelNameName(points);
        this.setTitle("Trophy List: " + points + " points. " + info.levelName);
 
        //
        // Set adapter.
        //
        ListView lv = (ListView) getListView();
        adapter_ = new EfficientAdapter(this, routines_);
        setListAdapter(adapter_);
        
        //
        // Setup the list on click..
        //
        lv.setTextFilterEnabled(true);
        lv.setOnItemClickListener(new OnItemClickListener() 
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) 
            {
                //
                // Fill out the images and text from the routine in the vector.
                //
                if (position < routines_.size()) {
                	
                	//
        			// Setup the exercises.
        			//
        			RoutineDisplay.routine_ = routines_.get(position);
            	
        			//
        			// Display Routine View.
        			//
        	 		Intent intent = new Intent (view.getContext(), RoutineDisplay.class); 
        			startActivity(intent);
                }
            }
        });
       

        // Setup the progress bar.
        setupProgressBar();
        
        // Listen for ads.
     /*   AdClickListener adClickListener = new AdClickListener();
 	    findViewById(R.id.ad_trophy_button).setOnClickListener(adClickListener);
       */
        
        Log.d("ThrophySelectorList", "Enter Constructor"); 
     }
    

    
    //
	// Setup the progress bar. 
	//
	@Override protected void onStart() { 	
		super.onStart();
		int points = Awards.getTotalPoints(this);
    
		
		AwardInformation info = Awards.getCurrentRangeAndLevelNameName(points);
		progressBar_.setMax(info.maxPoints - info.minPoints);
		
		//
		// See if the button has changed.
		//
		Button level = (Button)findViewById(R.id.trophy_levelbutton);
	    level.setText(" Award: " + info.levelName + " ");
		
	    progressBar_.setProgress(points-info.minPoints);
		progressBar_.setText("" + points + " points");
		
		/* FlurryAgent.onStartSession(this, "WC41REE2S5H3HCRR3I4F");
		HashMap<String, String> eventArgs = new HashMap<String, String>();
		eventArgs.put("Where Displayed", "Trophy View");	
		FlurryAgent.onEvent("Aylio ad displayed", eventArgs);*/
	}
	

	@Override
	protected void onStop() {
		super.onStop();
		
		FlurryAgent.onEndSession(this);
	}
	
	
	//
	// Progress bar that setsup the level button and the progress bar.
	//
	private void setupProgressBar() {
		progressBar_ = (TextProgressBar)findViewById(R.id.trophy_progressbar);

		Button level = (Button)findViewById(R.id.trophy_levelbutton);
	    level.setOnClickListener(new OnClickListener() {
	    	
			@Override public void onClick(View v) {
				Intent intent = new Intent();
	            intent.setClass(v.getContext(), TrophySelectorList.class);
				startActivity(intent);
			}
	        });
	}
	
}
