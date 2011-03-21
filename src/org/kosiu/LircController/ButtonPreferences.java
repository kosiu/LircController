package org.kosiu.LircController;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceManager;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;

//TabPreferences (configuration) activity, handle tabs configuration
public class ButtonPreferences extends PreferenceActivity {

	//Place to store witch button on witch tab is
	Integer mButtonNumber = null;
	Integer mTabNumber = null;
	//Place where configuration will be stored
	SharedPreferences mPref = null;

	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    	String[] splited = getIntent().getAction().split(" ");
    	mTabNumber = Integer.parseInt(splited[0]);
    	mButtonNumber = Integer.parseInt(splited[1]);
    	Connection connection = new Connection(this);
    	
        String keyBase = KeyParas.tabBtn(mTabNumber, mButtonNumber);
        String keyBaseH = KeyParas.tabBtnH(this, mTabNumber, mButtonNumber);
        
        
    	// Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        // Button Name
        PreferenceCategory tabsConf = new PreferenceCategory(this);
        tabsConf.setTitle(R.string.button);
        root.addPreference(tabsConf);
		              
        final EditTextPreference buttonName = new EditTextPreference(this);
        buttonName.setDialogTitle(R.string.buttonName);
        buttonName.setKey(keyBase.concat(" Name"));
        buttonName.setTitle(R.string.buttonName);
        String appended = keyBase.concat(" Name");
        String summary = mPref.getString(appended, " ");
        buttonName.setSummary(summary);
        buttonName.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		buttonName.setSummary((String)newValue);
				return true;
        	}        	
        });
        tabsConf.addPreference(buttonName);
        if(buttonName.getText()==null) buttonName.setText(keyBaseH);

        List<String> pilots = connection.readPilots();
        Map<String, String> commandMap = connection.readKeys(pilots.get(0));
        Collection<String> arrayOfStrings = commandMap.keySet();
        
        CharSequence[] adopted = arrayOfStrings.toArray(new CharSequence[arrayOfStrings.size()]);

        // Signal Name
        final ListPreference listPref = new ListPreference(this);
        listPref.setEntries(adopted);
        listPref.setEntryValues(adopted);
        listPref.setKey(keyBase.concat(" Signal"));
        listPref.setDialogTitle(R.string.change_signal);
        listPref.setTitle(R.string.set_signal);
        listPref.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		listPref.setSummary((String)newValue);
				return true;
        	}        	
        });
        tabsConf.addPreference(listPref);
        listPref.setSummary(listPref.getEntry());

        return root;
    }
}

