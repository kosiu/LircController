package org.kosiu.LircController;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.AdapterView.OnItemClickListener;

//Main activity (window) based on tabs, right now three
//tabs are implemented

//TODO: when unknown command app crashes
public class ButtonsView extends Activity {
	
	SharedPreferences mPref = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

        setContentView(R.layout.buttons_view);

	    if(mPref==null){
	    	mPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
	    }

	        GridView gridview = (GridView) findViewById(R.id.buttonsView);
	        gridview.setAdapter(new ImageAdapter(this));

	        gridview.setOnItemClickListener(new OnItemClickListener() {
	            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
	                
	            }
	        });
    }	    
}    
