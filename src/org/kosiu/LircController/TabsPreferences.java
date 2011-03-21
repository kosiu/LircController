package org.kosiu.LircController;

import android.content.Intent;
import android.content.SharedPreferences;
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

//TabPreferences (configuration) activity, handle tabs configuration
public class TabsPreferences extends PreferenceActivity {

	//Place to store new number of buttons in tab
	Integer mButtonsNumber=null;
	//Actual Tab
	Integer mTabNumber = null;
	//Place where configuration will be stored
	SharedPreferences mPref = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
	    //setting configuration object 
	    if(mPref==null){
	    	mPref = PreferenceManager.getDefaultSharedPreferences(this.getApplicationContext());
	    }
        setPreferenceScreen(createPreferenceHierarchy());
    }
	
	@Override
    protected void onResume() {
		super.onResume();
		setPreferenceScreen(createPreferenceHierarchy());

    }

    private PreferenceScreen createPreferenceHierarchy() {

    	mTabNumber = Integer.parseInt(getIntent().getAction());

    	// Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        // Tabs category
        PreferenceCategory tabsConf = new PreferenceCategory(this);
        tabsConf.setTitle(R.string.tabs_config);
        root.addPreference(tabsConf);
		      

        // Tab name
        final EditTextPreference tabName = new EditTextPreference(this);
        tabName.setDialogTitle(R.string.tab_name);
        tabName.setKey(KeyParas.tabName(mTabNumber));
        tabName.setTitle(R.string.tab_name);
        tabName.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		tabName.setSummary((String)newValue);
				return true;
        	}        	
        });
        tabsConf.addPreference(tabName);
        if(tabName.getText()==null) tabName.setText(KeyParas.tabNameH(this, mTabNumber));
        tabName.setSummary(tabName.getText());

        
        // Number of buttons 
        final EditTextPreference numberOfButtons = new EditTextPreference(this);
        numberOfButtons.getEditText().setKeyListener(new DigitsKeyListener());
        numberOfButtons.setDialogTitle(R.string.numberOfButtons);
        numberOfButtons.setKey(KeyParas.buttNum(mTabNumber));
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
	       	//TODO:Uptade button name when come back from button configuration        
	        String buttonName = mPref.getString(KeyParas.tabBtn(mTabNumber, i).concat(" Name"), "");
	        if(buttonName.equals("")) buttonName = KeyParas.tabBtnH(this, mTabNumber, i);
	        buttonsPref.setTitle(buttonName);
	        
	        buttonsPref.setSummary(R.string.click_to_configure_button);
	        tabsConf.addPreference(buttonsPref);
    	}
    }
    
}

