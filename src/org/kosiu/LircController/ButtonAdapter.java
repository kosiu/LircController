package org.kosiu.LircController;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class ButtonAdapter extends BaseAdapter {

	private Context mContext;
    Integer mButtonsNumber=null;
    Integer mTabNumber = null;
    SharedPreferences mPref = null;
    Activity mActivity;
    
    public ButtonAdapter(Context c, Integer tabNumber, Activity activity) {
        mContext = c;
        mActivity = activity;
        mTabNumber = tabNumber;
        mPref = PreferenceManager.getDefaultSharedPreferences(mContext.getApplicationContext());
    }

    public int getCount() {
        return new Integer(mPref.getString(KeyParas.buttNum(mTabNumber),"0"));
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int button, View convertView, ViewGroup parent) {
    	final Integer mButtonNumber = new Integer(button+1);

    	//TODO: Add button type switch
    	Button buttonView = null;
        buttonView = new Button(mContext);
        buttonView.setLayoutParams(new GridView.LayoutParams(100, 80));
        buttonView.setPadding(4, 4, 4, 4);
              
        CharSequence caption = mPref.getString(KeyParas.tabBtn(mTabNumber, mButtonNumber).concat(" Name"),"");
        if (caption.equals(""))
        	caption = mPref.getString(KeyParas.tabBtnH(mContext, mTabNumber, mButtonNumber).concat(" Name"),"");
        buttonView.setText(caption);
        
        final Connection connection = new Connection(mActivity);
        
        buttonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String signal = mPref.getString(KeyParas.tabBtn(mTabNumber, mButtonNumber).concat(" Signal"),"");
                if(!(signal.equals("")))
                	connection.seandLircCmd(signal);
            }
        });

        return buttonView;
    }

}