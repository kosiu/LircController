package org.kosiu.LircController;

import java.util.HashMap;
import java.util.Scanner;

import android.app.Activity;

public class LircdConfig {
	public String remoteName = null;
	public HashMap<String, String> codes = null;
	
	LircdConfig(Activity parent) {
        Scanner scanner = null; 
        scanner = new Scanner(parent.getResources().openRawResource(R.raw.lircd));
		String token = null;
		codes = new HashMap<String, String>();
		
		// TODO: enum 1 - begin remote 2 - name 3 - begin codes 4 - codes 5 - end
		int find = 1;
		while (scanner.hasNext()) {
			//reading first word in the line
			token = scanner.next();
			//if not comment proceed
			if (!token.startsWith("#")){
				if(!token.equals("end")){
					switch(find){
					case 1:
						if(token.equals("begin") && scanner.next().equals("remote")){
							find = 2; 
						}
						break;
					case 2:
						if(token.equals("name")) {
							remoteName = scanner.next(); find = 3;
						}
						break;
					case 3:
						if(token.equals("begin") && scanner.next().equals("codes")) {
							find = 4;
						}
						break;
					case 4:
						String value = scanner.next().substring(2);
						codes.put(token, value);
					break;
					case 5: break;
					}
				} else {
					find = 5;
				}
			}
			scanner.nextLine();
		} 
		
	}

	String keyCode(String key){
		return codes.get(key);
	}
	
	String fullCommand(String key){
		String command = "SIMULATE ";
		command = command.concat(keyCode(key));
		command = command.concat(" 0 ");
		command = command.concat(key);
		command = command.concat(" ");
		command = command.concat(remoteName);
		return command;
	}
}
