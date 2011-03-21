package org.kosiu.LircController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Connection {
	private Activity mParent = null;
	SharedPreferences mPref = null;

	//in and out
	private PrintWriter mHostWriter = null;
	private BufferedReader mHostReader = null;
	private Socket mHostSocket = null;

	//Constructor
	Connection(Activity parent){
		mParent = parent;
		mPref = PreferenceManager.getDefaultSharedPreferences(mParent.getApplicationContext());
	}

	//opening socket
	private int openSocket(){
		try {
			Integer port = Integer.parseInt(mPref.getString("lirc_port",""));
			mHostSocket = new Socket(mPref.getString("lirc_ip",""), port);		
			mHostWriter = new PrintWriter(mHostSocket.getOutputStream(), true);
			mHostReader = new BufferedReader(new InputStreamReader(mHostSocket.getInputStream()));
			return(0);
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mHostWriter = null;
			mHostReader = null;
			mHostSocket = null;
			return(-1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			mHostWriter = null;
			mHostReader = null;
			mHostSocket = null;
			return(-1);
		}

	}

	//closing socket
	private void closeSocket() {
		try {
			if(mHostWriter!=null) mHostWriter.close();
			if(mHostReader!=null) mHostReader.close();
			if(mHostSocket!=null) mHostSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
		mHostWriter = null;
		mHostReader = null;
		mHostSocket = null;
	}
	
	//reading list of Pilots
	public List<String> readPilots(){
		List<String> list = readList("LIST");
		return list;
	}
	
	//reading HashMap of buttons names and their codes
	public Map<String, String> readKeys(String pilot){
		String listStr = new String("LIST ");
		List<String> list = readList(listStr.concat(pilot));
        Iterator<String> it=list.iterator();
        Map<String, String> codes = new HashMap<String, String>();
        
        while(it.hasNext())
        {
          String s=(String)it.next();
          String[] fields = s.split(" ");
          codes.put(fields[1], fields[0]);
        }
        
		return sortByValue(codes);
	}
	
	@SuppressWarnings("unchecked")
	static Map<String, String> sortByValue(Map map) {
	     List list = new LinkedList(map.entrySet());
	     Collections.sort(list, new Comparator() {
	          public int compare(Object o1, Object o2) {
	               return ((Comparable) ((Map.Entry) (o1)).getValue())
	              .compareTo(((Map.Entry) (o2)).getValue());
	          }
	     });

	    Map result = new LinkedHashMap();
	    for (Iterator it = list.iterator(); it.hasNext();) {
	        Map.Entry entry = (Map.Entry)it.next();
	        result.put(entry.getKey(), entry.getValue());
	    }
	    return result;
	}
	
	
	//general function used for readKeys and readPilots
	private List<String> readList(String command) {
		List<String> list=new ArrayList<String>();
		if(openSocket()==0){
			mHostWriter.println(command);
			String linia;

			//TODO: Enum: 1 - DATA,
			int find = 1;
			try {
				do {
					linia = mHostReader.readLine();
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
	
	//send complete command to host with given button name 
	public void seandLircCmd(String command){
		if(openSocket()==0){
			String fullCommand = "SIMULATE ";
			Map<String, String> keyList = readKeys("Android");
			String keyString = keyList.get(command);
			fullCommand = fullCommand.concat(keyString);
			fullCommand = fullCommand.concat(" 0 ");
			fullCommand = fullCommand.concat(command);
			fullCommand = fullCommand.concat(" ");
			fullCommand = fullCommand.concat("Android");
			mHostWriter.println(fullCommand);
		}
		closeSocket();
	}

}