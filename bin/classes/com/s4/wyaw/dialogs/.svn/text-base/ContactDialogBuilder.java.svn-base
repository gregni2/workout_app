package com.s4.wyaw.dialogs;

import com.s4.wyaw.R;

import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.widget.TextView;

public class ContactDialogBuilder {
	public static AlertDialog create( Context context ) throws NameNotFoundException {
		// Try to load the a package matching the name of our own package
		PackageInfo pInfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_META_DATA);
		String versionInfo = pInfo.versionName ;
		
		String aboutTitle = "S4 - Building Apps 4 life";
	    String versionString = String.format("Version: %s", versionInfo); 
        String aboutText = "http://whereverworkoutapp.com/";
        
        //
        // Set up the TextView
        //
        final TextView message = new TextView(context);
        
        //
        // We'll use a spannable string to be able to make links clickable
        //
        final SpannableString s = new SpannableString(aboutText);
        
        //
        // Set some padding
        //
        message.setPadding(5, 5, 5, 5);
        
        //
        // Set up the final string
        //
        message.setText(versionString + "\n\n" + s);
        
        //
        // Now linkify the text
        //
        Linkify.addLinks(message, Linkify.ALL);
        return new AlertDialog.Builder(context).setTitle(aboutTitle).setCancelable(true).setIcon(R.drawable.icon).setPositiveButton(
        context.getString(android.R.string.ok), null).setView(message).create();		
     } 
}
