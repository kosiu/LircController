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
	    
	    final Connection connection = new Connection(this);
	    
        gridview.setOnItemClickListener(new OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
            	Integer buttonNumber = new Integer(position+1);
                String fullButtonName = new String("Button: ");
                fullButtonName = fullButtonName.concat(buttonNumber.toString());
                fullButtonName = fullButtonName.concat(", Tab: ");
                fullButtonName = fullButtonName.concat(mTabNumber.toString());
                fullButtonName = fullButtonName.concat("signalName");
                String signal = mPref.getString(fullButtonName,"DUPA");
                connection.seandLircCmd(signal);

            }
        });
    }	    
}    