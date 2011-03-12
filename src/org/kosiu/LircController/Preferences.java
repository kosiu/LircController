package org.kosiu.LircController;

//import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;
import android.preference.Preference.OnPreferenceChangeListener;

//Preferences (configuration) activity, right now store
//server name an port
public class Preferences extends PreferenceActivity {

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        //final Intent intent = getIntent();
        
        setPreferenceScreen(createPreferenceHierarchy());
    }
	
    private PreferenceScreen createPreferenceHierarchy() {
        // Root
        PreferenceScreen root = getPreferenceManager().createPreferenceScreen(this);

        // Server category
        PreferenceCategory serverCat = new PreferenceCategory(this);
        serverCat.setTitle(R.string.PC_config);
        root.addPreference(serverCat);

        final EditTextPreference lircIp = new EditTextPreference(this);
        lircIp.setDialogTitle(R.string.lirc_ip);
        lircIp.setKey("lirc_ip");
        lircIp.setTitle(R.string.lirc_ip);
        serverCat.addPreference(lircIp);
        lircIp.setSummary(lircIp.getText());
        lircIp.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		lircIp.setSummary((String)newValue);
        		getRemoteControllers();
        		//TODO: check if its correct address
				return true;
        	}        	
        });
        
        final EditTextPreference lircPort = new EditTextPreference(this);
        lircPort.setDialogTitle(R.string.lirc_port);
        lircPort.setKey("lirc_port");
        lircPort.setTitle(R.string.lirc_port);
        serverCat.addPreference(lircPort);
        lircPort.setSummary(lircPort.getText());
        lircPort.setOnPreferenceChangeListener(new OnPreferenceChangeListener(){
        	public boolean onPreferenceChange(Preference preference, Object newValue){
        		lircPort.setSummary((String)newValue);
        		//TODO: check if it's number between 1 and ???
				return true;
        	}        	
        });

        return root;
    }

    void getRemoteControllers(){
    	
    }
   
}
