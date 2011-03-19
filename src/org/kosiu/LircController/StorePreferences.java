package org.kosiu.LircController;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Environment;
import android.preference.PreferenceManager;

public class StorePreferences {

	boolean mExternalStorageAvailable = false;
	boolean mExternalStorageWriteable = false;
	File mRootFolder;
	SharedPreferences mPref = null;
	Activity mParent;

	
	
	StorePreferences(Activity parent){
		String state = Environment.getExternalStorageState();
		mParent = parent;
		if (Environment.MEDIA_MOUNTED.equals(state)) {
		    // We can read and write the media
		    mExternalStorageAvailable = mExternalStorageWriteable = true;
		} else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
		    // We can only read the media
		    mExternalStorageAvailable = true;
		    mExternalStorageWriteable = false;
		} else {
		    // Something else is wrong. It may be one of many other states, but all we need
		    //  to know is we can neither read nor write
		    mExternalStorageAvailable = mExternalStorageWriteable = false;
		}
		mRootFolder = Environment.getExternalStorageDirectory();
		
		mPref = PreferenceManager.getDefaultSharedPreferences(mParent.getApplicationContext());
	}
	
	public void save(String string) {
		if(mExternalStorageWriteable){
			Map<String, ?> prefMap = mPref.getAll();
			SortedSet<String> set = new TreeSet<String>(prefMap.keySet());
			Iterator<String> it = set.iterator();
			File file = new File(mRootFolder, string);

			try {
			    BufferedWriter out = new BufferedWriter(new FileWriter(file));
			    while(it.hasNext()){
			    	String key = it.next();
			    	out.write(key);
			    	out.write("=");
			    	out.write(prefMap.get(key).toString());
			    	out.write("\n");
			    }
			    out.close();
			} catch (IOException e) {
			}

		}
		
	}

	public void load(String string) {
		if(mExternalStorageAvailable){
			Editor edit = mPref.edit();
			File file = new File(mRootFolder, string);
			try {
			    BufferedReader in = new BufferedReader(new FileReader(file));
			    String str;
			    String[] splitted;
			    while ((str = in.readLine()) != null) {
			    	splitted = str.split("=");
			    	edit.putString(splitted[0], splitted[1]);
			    }
			    in.close();
			} catch (IOException e) {
			}
			edit.commit();
		}
		
	}

}
