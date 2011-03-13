package org.kosiu.LircController;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

//Main activity (window) based on tabs, right now three
//tabs are implemented

//TODO: when unknown command app crashes
public class TestActivity extends TabActivity {

  
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //three tabs
        TabHost tabHost = getTabHost();
        tabHost.setup(this.getLocalActivityManager());

        
        tabHost.addTab(tabHost.newTabSpec("audio")
                .setIndicator("dddd", getResources().getDrawable(R.drawable.tab_amarok))
                );
        tabHost.addTab(tabHost.newTabSpec("video")
                .setIndicator("aaaaa", getResources().getDrawable(R.drawable.tab_kaffeine))
                );
        tabHost.addTab(tabHost.newTabSpec("leave")
                .setIndicator("aaaaa", getResources().getDrawable(R.drawable.tab_shutdown))
                );



        
        
    }    
}
