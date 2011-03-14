package org.kosiu.LircController;

import android.app.TabActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

//Main activity (window) based on tabs, right now three
//tabs are implemented

//TODO: when unknown command app crashes
public class TestActivity extends TabActivity {
	
	SharedPreferences mPref = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    if(mPref==null){
	    	mPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
	    }
	    TabHost tab_host = getTabHost(); 

	    tab_host.setup(this.getLocalActivityManager());

	    
	    for(Integer i=1; i<=Integer.parseInt(mPref.getString("numberOfTabs","0"));i++){
	    	String name = mPref.getString(new String("Tab ").concat(i.toString()),"");
	    	TabSpec ts = tab_host.newTabSpec(name); 
	    	ts.setIndicator(name);
	    	
	    	Uri uri = null;
	    	ts.setContent(new Intent(i.toString(), uri, this, ButtonsView.class)); 
	    	tab_host.addTab(ts); 
	    }

	    tab_host.setCurrentTab(0);      

    }    
}
