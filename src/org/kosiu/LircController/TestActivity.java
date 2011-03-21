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
public class TestActivity extends TabActivity {

	//Place where configuration will be stored
	SharedPreferences mPref = null;
	
	//Overriding onCreate function
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    //setting configuration object 
	    if(mPref==null){
	    	mPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
	    }
	    
	    //Setting tabs host
	    TabHost tab_host = getTabHost(); 
	    tab_host.setup(this.getLocalActivityManager());

	    //adding tabs names and quantity is read from configuration object
	    for( Integer i=1; i<=Integer.parseInt(mPref.getString("Tab Quantity","0")); i++ ) {

	    	//reading tab name
	    	String tabName = mPref.getString( KeyParas.tabName(i), "");
	    	TabSpec ts = tab_host.newTabSpec(tabName); 
	    	ts.setIndicator(tabName);

	    	//setting tab content
	    	Uri uri = null;
	    	//pass tab number as a first argument of new Intednt
	    	ts.setContent(new Intent(i.toString(), uri, this, ButtonsView.class)); 
	    	tab_host.addTab(ts); 
	    }

	    tab_host.setCurrentTab(0);      

    }    
}
