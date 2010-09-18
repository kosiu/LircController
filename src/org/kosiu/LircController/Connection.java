package org.kosiu.LircController;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import android.app.Activity;

public class Connection {
	LircdConfig lircdConfig = null;
	
	Connection(Activity parent){
		lircdConfig = new LircdConfig(parent);
	}
	
	void seandCommand(String command){
		try {
			Socket server = new Socket("192.168.1.10", 8765);		
			PrintWriter out = new PrintWriter(server.getOutputStream(), true);
			out.println(lircdConfig.fullCommand(command));

		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODZOC Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
