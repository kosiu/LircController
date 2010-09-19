package org.kosiu.LircController;

import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.app.TabActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TabHost;

//TODO: when unknown command app crashes
public class LircController extends TabActivity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.main);
        

        TabHost tabHost = getTabHost();
        
        LayoutInflater.from(this).inflate(R.layout.main, tabHost.getTabContentView(), true);

        tabHost.addTab(tabHost.newTabSpec("amarok")
                .setIndicator("Amarok", getResources().getDrawable(R.drawable.tab_amarok))
                .setContent(R.id.amarok));
        tabHost.addTab(tabHost.newTabSpec("caffeine")
                .setIndicator("SMPlayer", getResources().getDrawable(R.drawable.tab_kaffeine))
                .setContent(R.id.caffeine));
        tabHost.addTab(tabHost.newTabSpec("leave")
                .setIndicator("Leave", getResources().getDrawable(R.drawable.tab_shutdown))
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

}