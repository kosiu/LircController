package org.kosiu.LircController;

//import android.content.Intent;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceCategory;
import android.preference.PreferenceScreen;

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

        EditTextPreference lircIp = new EditTextPreference(this);
        lircIp.setDialogTitle(R.string.lirc_ip);
        lircIp.setKey("lirc_ip");
        lircIp.setTitle(R.string.lirc_ip);
        serverCat.addPreference(lircIp);
        
        EditTextPreference lircPort = new EditTextPreference(this);
        lircPort.setDialogTitle(R.string.lirc_port);
        lircPort.setKey("lirc_port");
        lircPort.setTitle(R.string.lirc_port);
        serverCat.addPreference(lircPort);

        return root;
    }
    
}
