package org.kosiu.LircController;

import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.text.method.DigitsKeyListener;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;

//Main Preferences (configuration) activity, right now store
//PC server name an port, tabs number and links to tabs configuration,
//Storing and retrieving configuration to SD card
public class Preferences extends PreferenceActivity {

	//Place to store new number of tabs
	Integer mTabsNumber = null;
	//I like to have pointer to activity (in this case it's pointer
	//to "this" object instance
	Activity mActivity = null;
	//Place where configuration will be stored
	SharedPreferences mPref = null;
	
	//Overriding onCreate function
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //see comment in initialization
        mActivity = this;

	    //setting configuration object 
	    if(mPref==null){
	    	mPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
	    }

        //Setting up PreferenceScreen.
        setPreferenceScreen(createPreferenceHierarchy());

    }

	
	//loooooong linear function
    private PreferenceScreen createPreferenceHierarchy() {

    	// Root of all evil.
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        // Server category
        PreferenceCategory serverCat = new PreferenceCategory(this);
        serverCat.setTitle(R.string.pc_config);
        root.addPreference(serverCat);

        // IP address
        final EditTextPreference lircIp = new EditTextPreference(this);
        lircIp.setDialogTitle(R.string.lirc_ip);
        lircIp.setKey("lirc_ip");
        lircIp.setTitle(R.string.lirc_ip);
        lircIp.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		lircIp.setSummary((String)newValue);
				return true;
        	}        	
        });
        serverCat.addPreference(lircIp);
        if(lircIp.getText()==null) lircIp.setText("0.0.0.0");
        lircIp.setSummary(lircIp.getText());
        
        // IP port
        final EditTextPreference lircPort = new EditTextPreference(this);
        lircPort.getEditText().setKeyListener(new DigitsKeyListener());
        lircPort.setDialogTitle(R.string.lirc_port);
        lircPort.setKey("lirc_port");
        lircPort.setTitle(R.string.lirc_port);
        lircPort.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		lircPort.setSummary((String)newValue);
        		lircIp.setSummary((String)newValue);
        		
				return true;
        	}        	
        });
        serverCat.addPreference(lircPort);
        if(lircPort.getText()==null) lircPort.setText("8765");
        lircPort.setSummary(lircPort.getText());
        
        // Tabs category
        PreferenceCategory tabsCat = new PreferenceCategory(this);
        tabsCat.setTitle(R.string.tabs_config);
        root.addPreference(tabsCat);
        
        // Number of tabs 
        final EditTextPreference numberOfTabs = new EditTextPreference(this);
        numberOfTabs.getEditText().setKeyListener(new DigitsKeyListener());
        numberOfTabs.setDialogTitle(R.string.numberOfTabs);
        numberOfTabs.setKey("Tab Quantity");
        numberOfTabs.setTitle(R.string.numberOfTabs);
        root.addPreference(numberOfTabs);
        if(numberOfTabs.getText()==null) numberOfTabs.setText("0");
        	numberOfTabs.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		//checking if number is correct
        		Integer tmpTabNum = Integer.parseInt(newValue.toString());
        		if (tmpTabNum != null) {
        			if (tmpTabNum >= 0 && tmpTabNum <= 15 ) {
        				numberOfTabs.setSummary((String)newValue);
        				mTabsNumber = tmpTabNum;
        				setPreferenceScreen(createPreferenceHierarchy());
        				return true;
        			}
        		}
        		return false;        		
        	}        	
        });
        if(mTabsNumber == null){
        	mTabsNumber = Integer.parseInt(numberOfTabs.getText().toString());
        }
        numberOfTabs.setSummary(mTabsNumber.toString());

        //make tabs
        makeTabs(mTabsNumber, root);
        
        //Export category
        PreferenceCategory impCat = new PreferenceCategory(this);
        impCat.setTitle(R.string.exp_config);
        root.addPreference(impCat);

        // Export Preferences
        final EditTextPreference expPref = new EditTextPreference(this);
        expPref.setDialogTitle(R.string.filename);
        expPref.setTitle(R.string.expPref);
        expPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		StorePreferences storePreferences = new StorePreferences(mActivity);
        		storePreferences.save(newValue.toString());
        		return true;
        	}        	
        });
        impCat.addPreference(expPref);
       
        
        // Import Preferences
        final EditTextPreference impPref = new EditTextPreference(this);
        impPref.setDialogTitle(R.string.filename);
        impPref.setTitle(R.string.impPref);
        impPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		StorePreferences storePreferences = new StorePreferences(mActivity);
        		storePreferences.load(newValue.toString());
				return true;
        	}        	
        });
        impCat.addPreference(impPref);
        
        return root;
    };

    //Making links to tab configuration
    void makeTabs(Integer tabsNumber, PreferenceScreen tabCat){
    	for( Integer i=1; i<=tabsNumber; i++ ){
    		
	        // Intent preference
	        final PreferenceScreen tabsPref = getPreferenceManager().createPreferenceScreen(this);
	        
	        Uri uri = null;
	        tabsPref.setIntent(new Intent(i.toString(), uri, this, TabsPreferences.class));
	        //TODO:Update title when come back from button preference
	        String tabName = mPref.getString(KeyParas.tabName(i), "");
	        if(tabName.equals("")) tabName = KeyParas.tabNameH(this, i);
	        tabsPref.setTitle(tabName);
	        tabsPref.setSummary(R.string.click_to_configure);
	        tabCat.addPreference(tabsPref);
      }
    }
}
