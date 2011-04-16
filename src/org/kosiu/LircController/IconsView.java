package org.kosiu.LircController;

import android.app.Activity;
import android.os.Bundle;
import android.widget.GridView;

//Grid Layout to put Icons
//this activity use IconAdapter

public class IconsView extends Activity {
		
	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    
        setContentView(R.layout.icons_view);

	    GridView gridview = (GridView) findViewById(R.id.iconsView);
	    gridview.setAdapter(new IconAdapter(this));
	    	    
    }	    
}    
