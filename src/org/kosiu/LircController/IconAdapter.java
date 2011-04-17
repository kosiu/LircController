package org.kosiu.LircController;

import android.app.Activity;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class IconAdapter extends BaseAdapter {

    Conf mConf = null;
    Activity mActivity;
    Integer mTabNumber = null;
    Integer mButtonNumber = null;
    
    
    public IconAdapter(Activity activity) {
        mActivity = activity;
        mConf = new Conf(activity);
        String[] tbl = activity.getIntent().getAction().split(" ");
        mTabNumber = Integer.parseInt(tbl[0]);
        mButtonNumber = 0;
        if(tbl.length==2){
        	mButtonNumber = Integer.parseInt(tbl[1]);
        }

    }

    public int getCount() {
    	return mConf.getIconCount();
    }

    public Object getItem(int position) {
        return null;
    }

    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
    		final int pos = position;
            ImageView imageView;
            if (convertView == null) {  // if it's not recycled, initialize some attributes
                imageView = new ImageView(mActivity);
                imageView.setLayoutParams(new GridView.LayoutParams(45, 45));
            } else {
                imageView = (ImageView) convertView;
            }

            imageView.setImageDrawable(mConf.getIconDrawable(position));
            imageView.setOnClickListener(new OnClickListener() {
        			public void onClick(View v) {
        				String iconName = mConf.getIconName(pos);
        				setPreferences(iconName);
        				//TODO: Setting preferences
        			}
        		});
            return imageView;
    }
    
    private void setPreferences(String iconName){
    	Editor edit = mConf.mPref.edit();
    	if(mButtonNumber==0){
    		edit.putString(mConf.tabIconKey(mTabNumber), iconName);
    	} else {
    		edit.putString(mConf.buttImgKey(mTabNumber, mButtonNumber), iconName);
    	}
    	edit.commit();
    	mActivity.finish();
    }
    
}
