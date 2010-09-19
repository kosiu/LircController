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

//TODO: when unknown command app crashes
public class LircController extends TabActivity {

    private static final int INSERT_ID = Menu.FIRST;
  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        

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
    
        
        
        final Connection connection = new Connection((Activity)this);
        
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
        
        
        Iterator<Integer> listIt = list.keySet().iterator();
         
        while(listIt.hasNext()){
        	final int key = listIt.next();
        	View button = findViewById(key);
        	if (button != null){
        		button.setOnClickListener(new OnClickListener() {
        			public void onClick(View v) {
        				connection.seandCommand(list.get(key));
        			}
        		});
        	}
        }    
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        menu.add(0, INSERT_ID, 0, R.string.menu_config);
        return true;
    }
   
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch(item.getItemId()) {
            case INSERT_ID:
                showConfig();
                return true;
        }

        return super.onMenuItemSelected(featureId, item);
    }

    private void showConfig() {
        Intent i = new Intent(this, Preferences.class);
        startActivity(i);
    }

}
