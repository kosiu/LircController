package org.kosiu.LircController;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;
import android.text.method.DigitsKeyListener;

//TabPreferences (configuration) activity, handle tabs configuration
public class TabsPreferences extends PreferenceActivity {

	//Place to store new number of buttons in tab
	Integer mButtonsNumber=null;
	//Actual Tab
	Integer mTabNumber = null;
	//Place where configuration will be stored
	Conf mConf = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    //setting configuration object 
	    if(mConf==null)	mConf = new Conf(this);

    	//number of actual tab
    	mTabNumber = Integer.parseInt(getIntent().getAction());
	    	
	    //building preferences screen	
        setPreferenceScreen(createPreferenceHierarchy());
    }
	
	@Override
    protected void onResume() {
		super.onResume();
		
		//redrawing
		setPreferenceScreen(createPreferenceHierarchy());
    }

    private PreferenceScreen createPreferenceHierarchy() {

    	// Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        // Tabs category
        PreferenceCategory tabsConf = new PreferenceCategory(this);
        tabsConf.setTitle(R.string.tabs_config);
        root.addPreference(tabsConf);

        // Tab name
        final EditTextPreference tabName = new EditTextPreference(this);
        tabName.setDialogTitle(R.string.tab_name);
        tabName.setKey(mConf.tabNameKey(mTabNumber));
        tabName.setTitle(R.string.tab_name);
        tabName.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		tabName.setSummary((String)newValue);
				return true;
        	}        	
        });
        tabsConf.addPreference(tabName);
        if(tabName.getText()==null) tabName.setText(mConf.tabName(mTabNumber));
        tabName.setSummary(tabName.getText());

        // Tab icon
        final PreferenceScreen tabIcon = getPreferenceManager().createPreferenceScreen(this);
        Uri uri = null;
        tabIcon.setIntent(new Intent(mTabNumber.toString(), uri, this, IconsView.class));
        tabIcon.setTitle(R.string.tab_icon);
        tabIcon.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		tabIcon.setSummary((String)newValue);
				return true;
        	}        	
        });
        tabsConf.addPreference(tabIcon);
        tabIcon.setSummary(mConf.tabIcon(mTabNumber));
       
        // Number of buttons 
        final EditTextPreference numberOfButtons = new EditTextPreference(this);
        numberOfButtons.getEditText().setKeyListener(new DigitsKeyListener());
        numberOfButtons.setDialogTitle(R.string.numberOfButtons);
        numberOfButtons.setKey(mConf.buttNrKey(mTabNumber));
        numberOfButtons.setTitle(R.string.numberOfButtons);
        tabsConf.addPreference(numberOfButtons);
        if(numberOfButtons.getText()==null) numberOfButtons.setText("0");
        numberOfButtons.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		//checking if number is correct
        		Integer tmpButNum = Integer.parseInt(newValue.toString());
        		if (tmpButNum != null) {
        			if (tmpButNum >= 0 && tmpButNum <= 50 ) {
        				numberOfButtons.setSummary((String)newValue);
        				mButtonsNumber = tmpButNum;
        				setPreferenceScreen(createPreferenceHierarchy());
        				return true;
        			}
        		}
        		return false;        		
        	}        	
        });
        if(mButtonsNumber == null){
        	mButtonsNumber = Integer.parseInt(numberOfButtons.getText().toString());
        }
        numberOfButtons.setSummary(mButtonsNumber.toString());
        
        makeButtons(mButtonsNumber, tabsConf);
        
        return root;
    }
    
    
    void makeButtons(Integer buttonsNumber, PreferenceCategory tabsConf){
    	for(Integer i=1;i<=buttonsNumber;i++){
    		
	        // Intent preference
	        final PreferenceScreen buttonsPref = getPreferenceManager().createPreferenceScreen(this);
	        String str = new String(mTabNumber.toString()).concat(" ").concat(i.toString());
	        
	        Uri uri = null;
	        buttonsPref.setIntent(new Intent(str, uri, this, ButtonPreferences.class));

	        String buttonName = mConf.buttName(mTabNumber, i);
	        buttonsPref.setTitle(buttonName);
	        
	        buttonsPref.setSummary(R.string.click_to_configure_button);
	        tabsConf.addPreference(buttonsPref);
    	}
    }
    
}

