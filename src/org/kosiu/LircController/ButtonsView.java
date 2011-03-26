package org.kosiu.LircController;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

//Grid Layout to put buttons
//this activity use ButtonAdapter

public class ButtonsView extends Activity {
	
	Conf mConf = null;
	Integer mTabNumber = null;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
	    mTabNumber = Integer.parseInt(getIntent().getAction());
        setContentView(R.layout.buttons_view);
        if(mConf==null) mConf = new Conf(this);

	    GridView gridview = (GridView) findViewById(R.id.buttonsView);
	    gridview.setAdapter(new ButtonAdapter(mTabNumber, this));
	    	    
    }	    
}    
