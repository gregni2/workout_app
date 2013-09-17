package com.s4.wyaw.workoutalarm;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;

import android.app.AlarmManager;
import android.app.ListActivity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.TextView;

import com.s4.wyaw.R;
import com.s4.wyaw.dialogs.DeleteConfirmDialog;



//
// Workout Alarm view. List of all the scheduled alarms.
//
public class WorkoutAlarm extends ListActivity
{
	
    //
    // Items in the preferences database with this prefix are alarms that have
    // already been earned
    //
    public static ArrayList<String> alarms_;

	
	//
	// Plan there day of exercises for them.
	//
	private OnClickListener planDay_ = new OnClickListener() {
	      
	 @Override
	   public void onClick(View v) {
		 Intent intent = new Intent (v.getContext(), PlanDayAlarmView.class); 
		 startActivity(intent);
		}
	};
	
	
	//
	// Schedule an exercise.
	//
	private OnClickListener addRoutine_ = new OnClickListener() {
	      
	 @Override
	   public void onClick(View v) {
		 	Intent intent = new Intent (v.getContext(), ScheduleAlarmView.class); 
		 	startActivity(intent);
		}
	};
	

	
	//
    // Class to provide our fancy list.
    //
	public static class EfficientAdapter extends BaseAdapter {
	        private LayoutInflater mInflater;
	        @SuppressWarnings("unused")
	        
	    	public class TimeComparer implements Comparator {

	    		@Override
	    		public int compare(Object arg1, Object arg2) {
	    		
	    			int timeStart = ((String)arg1).indexOf("::");
	    			String stringTime1 = ((String)arg1).substring(timeStart+2, ((String)arg1).length());
	    			
	    			int timeStart2 = ((String)arg2).indexOf("::");
	    			String stringTime2 = ((String)arg2).substring(timeStart2+2, ((String)arg2).length());
	    			
	    			Log.e("StringTime1 =" +stringTime1, "StringTime2 = " + stringTime2);

	    			DateFormat sdf = new SimpleDateFormat("hh:mm");
	    			Date date1;
	    			Date date2;
					try {
						date1 = sdf.parse(stringTime1);
						date2 = sdf.parse(stringTime2);		
		    			Log.e("Working", date1.toString());

		    			return date1.compareTo(date2);


					} catch (ParseException e) {
						// TODO Auto-generated catch block
						Log.e("Failed", "Faield");
						e.printStackTrace();
					}
	    		
					return 0;
	    		}
	    		
	    	}
		    
	        //
	        // Get all alarms saved.
	        //
	        private static final String ALARM_PREF = "alarm.";
	        
	        //
	        //  
	        //
	        public EfficientAdapter(Context context) { 
		        mInflater = LayoutInflater.from(context);
                
	            //
	            // save all the routines that have been awarded.
	            // Get all settings, including accomplishment counts, from the 
	            // preferences database
	            SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(context);
	            
	            // Go through all items in the "shared preferences" store.  We will
	            // need to figure out which of these are awards and build a list of
	            // them.  This gives us the list of awards previously earned.
	            alarms_ = new ArrayList<String>();
	            for (String k: settings.getAll().keySet())
	            {   
	                if (k.startsWith(ALARM_PREF))
	                {
	                	Log.d("Alarms", "K = " + k);
	                    alarms_.add(k);
	                }
	            }
	            
	            Collections.sort(alarms_, new TimeComparer());
	        }

	        /**
	         * The number of items in the list is determined by the number of alarms.
	         * @see android.widget.ListAdapter#getCount()
	         */
	        public int getCount() {
	        	return alarms_.size();	        
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
	                convertView = mInflater.inflate(R.layout.schedule_list_item, null); /* Here is my special view. We could change this if we wanted to different views */
	            } 
	            
	            // Creates a ViewHolder and store references to the two children views
                // we want to bind data to.
                holder = new ViewHolder();
                holder.text = (TextView) convertView.findViewById(R.id.topLineFirst);
                holder.clockText = (TextView) convertView.findViewById(R.id.timeText);
		        
                //
                // Get the routine name and clock time.
                //
                String routineName = alarms_.get(position);
                int prefex = routineName.indexOf(ALARM_PREF);
                routineName = routineName.substring(prefex+ALARM_PREF.length(), routineName.length());
                
                String clockTime = new String("");
                int clockIndex = routineName.indexOf("::");
            	if ( (routineName.length() >= 0) && (clockIndex >= 0) ) {
                	clockTime = routineName.substring(clockIndex, routineName.length());
                	clockTime = clockTime.substring(2, clockTime.length());
                	routineName = routineName.substring(0, clockIndex);
                }
                	
                //
                // Display the text and clock.
                //
                holder.text.setText(routineName);
                holder.clockText.setText(clockTime);
                
                //
                // set the tag and return the new view.
                //
                convertView.setTag(holder);
                return convertView;
	        }
	        
	        static class ViewHolder {
	            TextView text;
	            TextView bottomText;
	            TextView clockText;
	        }
	    }
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.routinescheduler);
    
        //
        // Setup the plan a healthy day button.
        //
        Button planDayButton = (Button)findViewById(R.id.planDayButton);
        planDayButton.setOnClickListener(planDay_);
        
        //
        // Setup the plan a healthy day button.
        //
        Button addRoutineButton = (Button)findViewById(R.id.scheduleRoutineButton);
        addRoutineButton.setOnClickListener(addRoutine_);
        
        //
        // Set adapter.
        //
        ListView lv = (ListView) getListView();
        final BaseAdapter ba = new EfficientAdapter(this); 
        setListAdapter(ba);
        
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
                if (position < alarms_.size()) {
                	
                	//
        			// Setup the exercises.
        			//
        			DeleteConfirmDialog.launch_dialog(view, position, ba, WorkoutAlarm.this);
        		}
            }
        });
    }
    
    //
    // Remove the alarm from the alarm manager.
    //
    public void deleteAlarm(String key, int pos) {
    	Intent intent = new Intent(WorkoutAlarm.this, AlarmReceiver.class);
		Bundle bundle = new Bundle();
		bundle.putString("key", key);
		bundle.putInt("pos", pos);
	    intent.putExtras(bundle);
	    intent.addCategory(key);
	    PendingIntent sender = PendingIntent.getBroadcast(WorkoutAlarm.this, 0, intent, 0);
		AlarmManager am = (AlarmManager)getSystemService(ALARM_SERVICE);
		am.cancel(sender);
	}
    
    
    @Override protected void onStart() {
    	super.onStart();
  }

	@Override
	protected void onStop() {
		super.onStop();
		
	}
	
	
  }