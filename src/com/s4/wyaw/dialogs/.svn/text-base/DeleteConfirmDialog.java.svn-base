package com.s4.wyaw.dialogs;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.s4.wyaw.workoutalarm.WorkoutAlarm;

public class DeleteConfirmDialog {

	public static boolean mComfirm = false;
	public static View mView;
	public static Context mContext;
	public static int mPos;
	public static BaseAdapter mBa;
	public static WorkoutAlarm mWorkoutAlarm;
	
	public static void launch_dialog(View view, int pos, BaseAdapter ba, WorkoutAlarm workoutAlarm) {
		mContext = view.getContext();
		mView = view;
		mPos = pos;
		mBa = ba;
		mWorkoutAlarm = workoutAlarm;
		
		
		//
		// Set the title.
		//
		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("Delete Confirmation");

		//
		// Setting Dialog Message
		//
		builder.setMessage("Would you like to delete this event?");
	
		//
		// Setting Positive "Yes" Button
		//
		builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
            	SharedPreferences settings = PreferenceManager.getDefaultSharedPreferences(mContext);
    			SharedPreferences.Editor editor = settings.edit();
    			String key = WorkoutAlarm.alarms_.get(mPos);
        		editor.remove(WorkoutAlarm.alarms_.get(mPos));
    			editor.commit();
    			mWorkoutAlarm.alarms_.remove(mPos);
    		    mBa.notifyDataSetChanged();
            	mComfirm = true;
            	
            	mWorkoutAlarm.deleteAlarm(key, mPos);
        		
            	Toast.makeText(mContext, "Alarm Deleted", Toast.LENGTH_SHORT).show();
            }
        });
		
		
		
		//
        // Setting Negative "NO" Button
		//
		builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
            	dialog.cancel();
            }
        });
 		
		builder.create().show();
	}
	
	
}
