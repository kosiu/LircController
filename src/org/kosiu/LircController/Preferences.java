package org.kosiu.LircController;

import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.text.method.DigitsKeyListener;
import android.content.Intent;

//Preferences (configuration) activity, right now store
//server name an port
public class Preferences extends PreferenceActivity {

	//Place to store new number of tabs
	Integer mTabsNumber=null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(createPreferenceHierarchy());
    }
	
    private PreferenceScreen createPreferenceHierarchy() {
        // Root
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
        		//TODO: check if its correct address
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
        		//TODO: check if it's number between 1 and ???
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
        numberOfTabs.setKey("numberOfTabs");
        numberOfTabs.setTitle(R.string.numberOfTabs);
        root.addPreference(numberOfTabs);
        if(numberOfTabs.getText()==null) numberOfTabs.setText("0");
        numberOfTabs.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		//checking if number is correct
        		Integer tmpTabNum = Integer.parseInt(newValue.toString());
        		if (tmpTabNum != null) {
        			if (tmpTabNum >= 0 && tmpTabNum <= 6 ) {
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
        
        makeTabs(mTabsNumber, root);
        
        return root;
    };
   
    void makeTabs(Integer tabsNumber, PreferenceScreen tabCat){
    	for(Integer i=1;i<=tabsNumber;i++){
    		
	        // Intent preference
	        final PreferenceScreen tabsPref = getPreferenceManager().createPreferenceScreen(this);
	        
	        Uri uri = null;
	        tabsPref.setIntent(new Intent(i.toString(), uri, this, TabsPreferences.class));
	       
	        String str = new Integer(i).toString();
	        tabsPref.setTitle(new String("Tab ").concat(str));
	        tabsPref.setSummary(R.string.click_to_configure);
	        tabCat.addPreference(tabsPref);
      }
    }
}

