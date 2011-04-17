package org.kosiu.LircController;


import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;

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

    	if(type.equals("Text button")||type.equals("Image Button")){
    		//Create Button
    		View view = null;

    		if(type.equals("Text button")){
    			Button buttonView = null;
    			buttonView = new Button(mActivity);
    			CharSequence caption = mConf.buttName(mTabNumber, actButton);
    			buttonView.setText(caption);
    			view = buttonView;
    		} else {
    			ImageButton buttonView = null;
    			buttonView = new ImageButton(mActivity);
    			buttonView.setImageDrawable(mConf.getIconDrawable(mConf.buttImg(mTabNumber, actButton)));
    			view = buttonView; 
    		}
   			view.setLayoutParams(new GridView.LayoutParams(100, 80));
   			view.setPadding(4, 4, 4, 4);

	        //On Click
	        final Connection connection = new Connection(mActivity);
	        
	        view.setOnClickListener(new View.OnClickListener() {
	            public void onClick(View v) {
	                String signal = mConf.buttSig(mTabNumber, actButton);
	                if(!(signal.equals("")))
	                	connection.seandLircCmd(signal);
	            }
	        });
	        return view;

    	} 
    	
    	View buttonView = null;
	    buttonView = new View(mActivity);
	    return buttonView;
    }
    


}