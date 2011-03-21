package org.kosiu.LircController;

import android.content.Context;

public class KeyParas extends Object {

	//Return front of the key for buttons
	public static String tabBtn(Integer tab, Integer button){
		return new String("Tab_")
    		.concat(tab.toString())
    		.concat("_Button_")
    		.concat(button.toString());          	
	}

	//Return front of the human readable button name
	public static String tabBtnH(Context cntx, Integer tab, Integer button){
		return new String(cntx.getString(R.string.tab))
			.concat(" ")
    		.concat(tab.toString())
    		.concat(", ")
    		.concat(cntx.getString(R.string.button))
    		.concat(" ")
    		.concat(button.toString());          	
	}
	
	//Key value for name of tab
	public static String tabName(Integer tabNumber){
		return new String("Tab_")
			.concat(tabNumber.toString())
			.concat(" Name");
	}

	//Human readable name of tab
	public static String tabNameH(Context cntx, Integer tabNumber){
		return new String(cntx.getString(R.string.tab))
					.concat(" ")
					.concat(tabNumber.toString());
	}

	//Key value for buttons numbers in tab
	public static String buttNum(Integer tabNumber){
		return new String("Tab_")
			.concat(tabNumber.toString())
			.concat(" Button Quantity");
	}
	
}
