package org.kosiu.LircController;

import android.app.TabActivity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

//Main activity (window) based on tabs, right now three
//tabs are implemented
public class LircController extends TabActivity {

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
	    	//check for icon
	    	Drawable icon = mConf.getIconDrawable(mConf.tabIcon(i));
	    	
	    	//reading tab name
	    	String tabName = mConf.tabName(i);
	    	TabSpec ts = tab_host.newTabSpec(tabName);
	    	if(icon==null){
	    		ts.setIndicator(tabName);
	    	} else {
	    		ts.setIndicator(tabName, icon);
	    	}
	    	

	    	//setting tab content
	    	Uri uri = null;
	    	//pass tab number as a first argument of new Intednt
	    	ts.setContent(new Intent(i.toString(), uri, this, ButtonsView.class)); 
	    	tab_host.addTab(ts); 
	    }

	    tab_host.setCurrentTab(0);      

    }    

    //two buttons in menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        MenuItem menuConf = menu.add(R.string.menu_config);
        menuConf.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
			public boolean onMenuItemClick(MenuItem item) {
				showConfig();
				return false;
			}
        });
        
        MenuItem menuTest = menu.add("test");
        menuTest.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
			public boolean onMenuItemClick(MenuItem item) {
				showTestWindow();
				return false;
			}
        });
        
        return true;
    }


    //showing configuration activity
    private void showConfig() {
        Intent i = new Intent(this, Preferences.class);
        startActivity(i);
    }

    //showing configuration activity
    private void showTestWindow() {
    }

}
