package org.kosiu.LircController;

import java.util.LinkedHashMap;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.Preference;
import android.preference.PreferenceManager;

public class Conf extends Object {

	//Context is needed for resource strings and shared preferences
	private Context mContext = null;
	//Preferences
	SharedPreferences mPref = null;

	private LinkedHashMap<String, Integer> mIconList = null;
	
	//Constructor
	public Conf(Activity activity){
		mContext = activity.getApplicationContext();
		Preference pref = new Preference(mContext);
		pref.getPreferenceManager();
		mPref = PreferenceManager.getDefaultSharedPreferences(mContext);
		buildBaseIconList();
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
	
	//Key value for icon of tab
	public String tabIconKey(Integer tabNumber){
		return new String("Tab_")
			.concat(tabNumber.toString())
			.concat(" Icon");
	}
	//Return the icon of given tab
	public String tabIcon(Integer tab){
		return mPref.getString(tabIconKey(tab),"");
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

	//Button Type
	public String buttTypKey(Integer tab, Integer button){
		return tabBtn(tab, button).concat(" Type");
	}
	public String buttTyp(Integer tab, Integer button){	
		return mPref.getString(buttTypKey(tab,button), "");
	}
	
	//Img value for button name
	public String buttImgKey(Integer tab, Integer button){
		return tabBtn(tab, button).concat(" Img");
	}
	public String buttImg(Integer tab, Integer button){	
		return mPref.getString(buttImgKey(tab,button), "");
	}
	
	
	//PRIVATE -----------------------------------------------------
	
	//Return front of the key for buttons
	private String tabBtn(Integer tab, Integer button){
		return new String("Tab_")
    		.concat(tab.toString())
    		.concat("_Button_")
    		.concat(button.toString());          	
	}		

	//----------------------------------------------------------------
	//ICONS-----------------------------------------------------------
	//----------------------------------------------------------------

	
	Drawable getIconDrawable(String name){
		if(mIconList.containsKey(name)){
			return mContext.getResources().getDrawable(mIconList.get(name));
		}
		return null;
	}

	Drawable getIconDrawable(int position){
		return mContext.getResources().getDrawable(mIconList.get(getIconName(position)));
	}
	
	String getIconName(int position){
		String[] keys = null;
		Set<String> keySet = mIconList.keySet();
		keys = keySet.toArray(new String[0]);
		return keys[position];
	}
	int getIconCount(){
		return mIconList.size();
	}

	// references to our images
	void buildBaseIconList(){
		mIconList = new LinkedHashMap<String, Integer>();
		mIconList.put("Forward",		R.drawable.bt_forward);
		mIconList.put("Mute",			R.drawable.bt_mute);
		mIconList.put("Next text",		R.drawable.bt_next_text);
		mIconList.put("Next",			R.drawable.bt_next);
		mIconList.put("Pause",			R.drawable.bt_pause);
		mIconList.put("Play",			R.drawable.bt_play);
		mIconList.put("Preview text",	R.drawable.bt_prev_text);
		mIconList.put("Preview",		R.drawable.bt_prev);
		mIconList.put("Revind",			R.drawable.bt_revind);
		mIconList.put("Stop",			R.drawable.bt_stop);
		mIconList.put("Volume down",	R.drawable.bt_volume_down);
		mIconList.put("Volume up",		R.drawable.bt_volume_up);
		mIconList.put("Amarok",			R.drawable.tab_amarok);
		mIconList.put("Smplayer",		R.drawable.tab_smplayer);
		mIconList.put("Shutdown",		R.drawable.tab_shutdown);
		
	}
 }

