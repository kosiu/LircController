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

	//Place to store new number of buttons
	Integer mButtonsNumber=null;
	Integer mTabNumber = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(createPreferenceHierarchy());
    }
	
    private PreferenceScreen createPreferenceHierarchy() {

    	mTabNumber = Integer.parseInt(getIntent().getAction());

    	// Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        // Server category
        PreferenceCategory tabsConf = new PreferenceCategory(this);
        tabsConf.setTitle(R.string.tabs_config);
        root.addPreference(tabsConf);
		      
        
        final EditTextPreference tabName = new EditTextPreference(this);
        tabName.setDialogTitle(R.string.tab_name);
        tabName.setKey(append("Tab"));
        tabName.setTitle(R.string.tab_name);
        tabName.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		tabName.setSummary((String)newValue);
				return true;
        	}        	
        });
        tabsConf.addPreference(tabName);
        if(tabName.getText()==null) tabName.setText(append("Tab"));
        tabName.setSummary(tabName.getText());

        
        // Number of buttons 
        final EditTextPreference numberOfButtons = new EditTextPreference(this);
        numberOfButtons.getEditText().setKeyListener(new DigitsKeyListener());
        numberOfButtons.setDialogTitle(R.string.numberOfButtons);
        numberOfButtons.setKey(append("numberOfButtons"));
        numberOfButtons.setTitle(R.string.numberOfButtons);
        tabsConf.addPreference(numberOfButtons);
        if(numberOfButtons.getText()==null) numberOfButtons.setText("0");
        numberOfButtons.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		//checking if number is correct
        		Integer tmpButNum = Integer.parseInt(newValue.toString());
        		if (tmpButNum != null) {
        			if (tmpButNum >= 0 && tmpButNum <= 6 ) {
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
	        
	        Uri uri = null;
	        String appended = append(i.toString());
	        buttonsPref.setIntent(new Intent(appended, uri, this, ButtonPreferences.class));
	       
	        String str = new Integer(i).toString();
	        buttonsPref.setTitle(append(new String("Button: ").concat(str).concat(", Tab: ")));
	        buttonsPref.setSummary(R.string.click_to_configure_button);
	        tabsConf.addPreference(buttonsPref);
        }
    }
    
    String append(String in){
    	in = in.concat(" ");
    	return in.concat(mTabNumber.toString());
    }
}

