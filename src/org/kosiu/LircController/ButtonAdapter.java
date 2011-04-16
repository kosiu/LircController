package org.kosiu.LircController;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;

public class ButtonAdapter extends BaseAdapter {

    Integer mButtonsNumber=null;
    Integer mTabNumber = null;
    Conf mConf = null;
    Activity mActivity;
    
    public ButtonAdapter(Integer tabNumber, Activity activity) {
        mActivity = activity;
        mTabNumber = tabNumber;
        mConf = new Conf(activity);
        
    }

    public int getCount() {
        return new Integer(mConf.buttNr(mTabNumber));
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int button, View convertView, ViewGroup parent) {
    	final Integer actButton = new Integer(button+1);

    	String type = mConf.buttTyp(mTabNumber, actButton);

    	if(type.equals("Text button")){
	    	Button buttonView = null;
	        buttonView = new Button(mActivity);
	        buttonView.setLayoutParams(new GridView.LayoutParams(100, 80));
	        buttonView.setPadding(4, 4, 4, 4);
	              
	        CharSequence caption = mConf.buttName(mTabNumber, actButton);
	        buttonView.setText(caption);
	        
	        final Connection connection = new Connection(mActivity);
	        
	        buttonView.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                String signal = mConf.buttSig(mTabNumber, actButton);
	                if(!(signal.equals("")))
	                	connection.seandLircCmd(signal);
	            }
	        });
	        return buttonView;

    	} 
    	
    	View buttonView = null;
	    buttonView = new View(mActivity);
	    return buttonView;
    }

}