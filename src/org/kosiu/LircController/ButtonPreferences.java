package org.kosiu.LircController;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;

//TabPreferences (configuration) activity, handle tabs configuration
public class ButtonPreferences extends PreferenceActivity {

	//Place to store witch button on witch tab is
	Integer mButtonNumber = null;
	Integer mTabNumber = null;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPreferenceScreen(createPreferenceHierarchy());
    }
	
    private PreferenceScreen createPreferenceHierarchy() {

    	String[] splited = getIntent().getAction().split(" ");
    	mTabNumber = Integer.parseInt(splited[0]);
    	mButtonNumber = Integer.parseInt(splited[1]);
    	Connection connection = new Connection(this);
    	
        String fullButtonName = new String("Button: ");
        fullButtonName = fullButtonName.concat(mTabNumber.toString());
        fullButtonName = fullButtonName.concat(", Tab: ");
        fullButtonName = fullButtonName.concat(mButtonNumber.toString());
        
    	// Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        // Server category
        PreferenceCategory tabsConf = new PreferenceCategory(this);
        tabsConf.setTitle(splited[0]);
        root.addPreference(tabsConf);
		              
        final EditTextPreference buttonName = new EditTextPreference(this);
        buttonName.setDialogTitle(fullButtonName);
        buttonName.setKey(fullButtonName);
        buttonName.setTitle(fullButtonName);
        buttonName.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		buttonName.setSummary((String)newValue);
				return true;
        	}        	
        });
        tabsConf.addPreference(buttonName);
        if(buttonName.getText()==null) buttonName.setText(fullButtonName);
        buttonName.setSummary(buttonName.getText());

        List<String> pilots = connection.readPilots();
        HashMap<String, String> commandMap = connection.readKeys(pilots.get(0));
        Collection<String> arrayOfStrings = commandMap.keySet();
        CharSequence[] adopted = arrayOfStrings.toArray(new CharSequence[arrayOfStrings.size()]);

        // List preference
        ListPreference listPref = new ListPreference(this);
        listPref.setEntries(adopted);
        listPref.setEntryValues(adopted);
        listPref.setKey(fullButtonName.concat("signalName"));
        listPref.setDialogTitle(R.string.change_signal);
        listPref.setTitle(R.string.set_signal);
        tabsConf.addPreference(listPref);

        return root;
    }
}

