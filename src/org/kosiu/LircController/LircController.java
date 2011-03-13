package org.kosiu.LircController;

import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;

//Main activity (window) based on tabs, right now three
//tabs are implemented

//TODO: when unknown command app crashes
public class LircController extends TabActivity {

  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //three tabs
        TabHost tabHost = getTabHost();
        
        LayoutInflater.from(this).inflate(R.layout.main, tabHost.getTabContentView(), true);

        tabHost.addTab(tabHost.newTabSpec("audio")
                .setIndicator(getText(R.string.audio), getResources().getDrawable(R.drawable.tab_amarok))
                .setContent(R.id.audio));
        tabHost.addTab(tabHost.newTabSpec("video")
                .setIndicator(getText(R.string.video), getResources().getDrawable(R.drawable.tab_kaffeine))
                .setContent(R.id.video));
        tabHost.addTab(tabHost.newTabSpec("leave")
                .setIndicator(getText(R.string.leave), getResources().getDrawable(R.drawable.tab_shutdown))
                .setContent(R.id.leave));
            
        //creating new connection class witch handle communication with host 
        final Connection connection = new Connection((Activity)this);
        
        //hash maps store button id from xml file and lirc signal name
        //this should be generated automatically but I have to remove XML file
        final HashMap<Integer, String> list = new HashMap<Integer, String>();
        
        list.put(R.id.a_vol_down,		"G_VOL_DOWN");
        list.put(R.id.a_vol_up,			"G_VOL_UP");
        list.put(R.id.a_vol_mute,		"G_MUTE");
        list.put(R.id.a_stop,			"A_STOP");
        list.put(R.id.a_play,			"A_PLAY");
        list.put(R.id.a_pause,			"A_PAUSE");
        list.put(R.id.a_next,			"A_NEXT");
        list.put(R.id.a_prev,			"A_PREV");
        list.put(R.id.p_poweroff,		"POWER_OFF");
        list.put(R.id.k_vol_down,		"G_VOL_DOWN");
        list.put(R.id.k_vol_up,			"G_VOL_UP");
        list.put(R.id.k_vol_mute,		"G_MUTE");
        list.put(R.id.k_revind,			"K_REVIND");
        list.put(R.id.k_forward,		"K_FORWARD");
        list.put(R.id.k_pause,			"K_PAUSE");
        list.put(R.id.k_next,			"K_NEXT");
        list.put(R.id.k_prev,			"K_PREV");
        
        //iterator to list
        Iterator<Integer> listIt = list.keySet().iterator();
        
        //setting up on click listener for each button
        while(listIt.hasNext()){
        	final int key = listIt.next();
        	View button = findViewById(key);
        	if (button != null){
        		button.setOnClickListener(new OnClickListener() {
        			public void onClick(View v) {
        				connection.seandLircCmd(list.get(key));
        			}
        		});
        	}
        }    
    }

    
    //two buttons in menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, 1, 0, R.string.menu_config);
        menu.add(0, 2, 0, "test");
       return true;
    }

    //when those button clicked
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case 1:
                showConfig();
                return true;
            case 2:
            	//Test: read plots
            	showTestWindow();
            	return true;
      }

        return super.onMenuItemSelected(featureId, item);
    }

    //showing configuration activity
    private void showConfig() {
        Intent i = new Intent(this, Preferences.class);
        startActivity(i);
    }

    //showing configuration activity
    private void showTestWindow() {
        Intent i = new Intent(this, TestActivity.class);
        startActivity(i);
    }
    
}
