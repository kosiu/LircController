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
    private Context mParent;
    Integer mButtonsNumber=null;
    Integer mTabNumber = null;
    SharedPreferences mPref = null;
    Activity mActivity;
    
    public ButtonAdapter(Context c, Integer tabNumber, Activity activity) {
        mParent = c;
        mActivity = activity;
        mTabNumber = tabNumber;
        mPref = PreferenceManager.getDefaultSharedPreferences(mParent.getApplicationContext());
    }

    public int getCount() {
        String str = mPref.getString(new String("numberOfButtons ").concat(mTabNumber.toString()),"0");
        Integer num = new Integer(str);
        return num;
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
        Button buttonView = null;
        //if (convertView == null) {  // if it's not recycled, initialize some attributes
            buttonView = new Button(mParent);
            buttonView.setLayoutParams(new GridView.LayoutParams(100, 80));
            buttonView.setPadding(4, 4, 4, 4);
        //} else {
        //    buttonView = (Button) convertView;
        //}
        
        String fullButtonName = new String("Button: ");
        fullButtonName = fullButtonName.concat(mButtonNumber.toString());
        fullButtonName = fullButtonName.concat(", Tab: ");
        fullButtonName = fullButtonName.concat(mTabNumber.toString());

        
        CharSequence caption = mPref.getString(fullButtonName,"DUPA");
        buttonView.setText(caption);
        
        final Connection connection = new Connection(mActivity);
        
        buttonView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String fullButtonName = new String("Button: ");
                fullButtonName = fullButtonName.concat(mButtonNumber.toString());
                fullButtonName = fullButtonName.concat(", Tab: ");
                fullButtonName = fullButtonName.concat(mTabNumber.toString());          	
                fullButtonName = fullButtonName.concat("signalName");
                String signal = mPref.getString(fullButtonName,"");
                connection.seandLircCmd(signal);

            }
        });

        return buttonView;
    }

}