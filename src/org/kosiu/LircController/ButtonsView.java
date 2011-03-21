package org.kosiu.LircController;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.GridView;

//Grid Layout to put buttons
//this activity use ButtonAdapter

//TODO: when unknown command app crashes
public class ButtonsView extends Activity {
	
	SharedPreferences mPref = null;
	Integer mTabNumber = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    mTabNumber = Integer.parseInt(getIntent().getAction());
        setContentView(R.layout.buttons_view);
        mPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());

	    if(mPref==null){
	    	mPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
	    }

	    GridView gridview = (GridView) findViewById(R.id.buttonsView);
	    gridview.setAdapter(new ButtonAdapter(this, mTabNumber, this));
	    	    
    }	    
}    
