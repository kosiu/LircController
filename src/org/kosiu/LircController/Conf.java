package org.kosiu.LircController;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.Preference;
import android.preference.PreferenceManager;

public class Conf extends Object {

	//Context is needed for resource strings and shared preferences
	private Context mContext = null;
	//Preferences
	private SharedPreferences mPref = null;
	
	//Constructor
	public Conf(Activity activity){
		mContext = activity.getApplicationContext();
		Preference pref = new Preference(mContext);
		pref.getPreferenceManager();
		mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
	}

	
	//Return Tabs Quantity
	public Integer nrTabs(){
		return new Integer(mPref.getString("Tab Quantity","0"));
	}
	//Return Tabs Quantity Key
	public String nrTabsKey(){
		return new String("Tab Quantity");
	}
	
	
	//Return the name of given tab
	public String tabName(Integer tabNumber){
		String key = tabNameKey(tabNumber);
		if (mPref.contains(key)) {
			return mPref.getString(key, "");
		} else {
			return new String(mContext.getString(R.string.tab))
					.concat(" ")
					.concat(tabNumber.toString());
		}
	}
	//Key value for name of tab
	public String tabNameKey(Integer tabNumber){
		return new String("Tab_")
			.concat(tabNumber.toString())
			.concat(" Name");
	}
	
	//Return number of buttons
	public Integer buttNr(Integer tabNumber){
		return new Integer(mPref.getString(buttNrKey(tabNumber), "0"));
	}
	//Key value for buttons numbers in tab
	public String buttNrKey(Integer tabNumber){
		return new String("Tab_")
			.concat(tabNumber.toString())
			.concat(" Button Quantity");
	}

	//Return name of button
	public String buttName(Integer tab, Integer button){
		String key = buttNameKey(tab, button);
		if (mPref.contains(key)) {
			return mPref.getString(key, "");
		} else {
			return new String(mContext.getString(R.string.tab))
			.concat(" ")
    		.concat(tab.toString())
    		.concat(", ")
    		.concat(mContext.getString(R.string.button))
    		.concat(" ")
    		.concat(button.toString());          				
		}
	}
	//Key value for button name
	public String buttNameKey(Integer tab, Integer button){
		return tabBtn(tab, button).concat(" Name");
	}

	//Key value for button name
	public String buttSigKey(Integer tab, Integer button){
		return tabBtn(tab, button).concat(" Signal");
	}
	public String buttSig(Integer tab, Integer button){
		return mPref.getString(buttSigKey(tab,button), "");
	}
	
	
	//PRIVATE -----------------------------------------------------
	
	//Return front of the key for buttons
	private String tabBtn(Integer tab, Integer button){
		return new String("Tab_")
    		.concat(tab.toString())
    		.concat("_Button_")
    		.concat(button.toString());          	
	}		
}
