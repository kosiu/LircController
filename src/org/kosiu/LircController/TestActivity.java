package org.kosiu.LircController;

import android.app.TabActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

//Main activity (window) based on tabs, right now three
//tabs are implemented
public class TestActivity extends TabActivity {

	//Place where configuration will be stored
	Conf mConf = null;
	
	//Overriding onCreate function
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);

	    //setting configuration object 
	    if(mConf==null) mConf = new Conf(this);
	    
	    //Setting tabs host
	    TabHost tab_host = getTabHost(); 
	    tab_host.setup(this.getLocalActivityManager());

	    //adding tabs names and quantity is read from configuration object
	    for( Integer i=1; i<=mConf.nrTabs(); i++ ) {

	    	//reading tab name
	    	String tabName = mConf.tabName(i);
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
