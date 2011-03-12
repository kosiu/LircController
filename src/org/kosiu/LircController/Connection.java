package org.kosiu.LircController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
//import android.widget.Toast;

public class Connection {
//	LircdConfig lircdConfig = null;
	Activity mParent = null;

	//in and out
	PrintWriter out = null;
	BufferedReader in = null;
	Socket server = null;
	
	Connection(Activity parent){
		mParent = parent;
//		lircdConfig = new LircdConfig(parent);
	}
	
	List<String> readPilots(){
		List<String> list = readList("LIST");
		return list;
	}
	
	HashMap<String, String> readKeys(String pilot){
		String listStr = new String("LIST ");
		List<String> list = readList(listStr.concat(pilot));
        Iterator<String> it=list.iterator();
        HashMap<String, String> codes = new HashMap<String, String>();
        
        while(it.hasNext())
        {
          String s=(String)it.next();
          String[] fields = s.split(" ");
          codes.put(fields[1], fields[0]);
        }

		return codes;
	}
	

	List<String> readList(String command) {
		List<String> list=new ArrayList<String>();
		if(openSocket()==0){
			out.println(command);
			String linia;

			//TODO: Enum: 1 - DATA,
			int find = 1;
			try {
				do {
					linia = in.readLine();
					switch(find){
					case 1:
						if(linia.equals("DATA")){
							find = 2; 
						}
						break;
					case 2:
						//TODO: check number of lines
						find = 3;
						break;
					case 3:
						if(!linia.equals("END")){
							list.add(linia);
							//Toast.makeText(mParent.getApplicationContext(),
							//		linia, Toast.LENGTH_SHORT).show();
						}
						break;
					}
				} while(!linia.contentEquals("END"));

			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
		return list;
	}
	
	void seandCommand(String command){
		if(openSocket()==0){
			String fullCommand = "SIMULATE ";
			HashMap<String, String> keyList = readKeys("Android");
			String keyString = keyList.get(command);
			fullCommand = fullCommand.concat(keyString);
			fullCommand = fullCommand.concat(" 0 ");
			fullCommand = fullCommand.concat(command);
			fullCommand = fullCommand.concat(" ");
			fullCommand = fullCommand.concat("Android");
			out.println(fullCommand);
		}
		closeSocket();
	}

	int openSocket(){
		try {
			SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(mParent.getApplicationContext());
			Integer port = Integer.parseInt(pref.getString("lirc_port","8765"));
			if (port == null) port = 8765;
			server = new Socket(pref.getString("lirc_ip","0.0.0.0"), port);		
			out = new PrintWriter(server.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(server.getInputStream()));
			return(0);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			out = null;
			in = null;
			server = null;
			return(-1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			out = null;
			in = null;
			server = null;
			return(-1);
		}

	}

	void closeSocket() {
		try {
			if(out!=null) out.close();
			if(in!=null) in.close();
			if(server!=null) server.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		out = null;
		in = null;
		server = null;
	}
}
