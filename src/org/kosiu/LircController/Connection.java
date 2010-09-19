package org.kosiu.LircController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Connection {
	LircdConfig lircdConfig = null;
	Activity mParent = null;
	
	Connection(Activity parent){
		mParent = parent;
		lircdConfig = new LircdConfig(parent);
	}
	
	void seandCommand(String command){
		try {
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mParent.getApplicationContext());
			Integer port = Integer.parseInt(pref.getString("lirc_port","8765"));
			if (port == null) port = 8765;
			Socket server = new Socket(pref.getString("lirc_ip","0.0.0.0"), port);		
			PrintWriter out = new PrintWriter(server.getOutputStream(), true);
			out.println(lircdConfig.fullCommand(command));

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
