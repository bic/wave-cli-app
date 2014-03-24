package com.maivic.restaurant;

import android.os.Bundle;
import android.support.v4.app.Fragment;

public class ContentFragment extends Fragment{
	   @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);

	        Bundle extras = getArguments();
	        if (extras != null) {
	            if (extras.containsKey("TAB_ITEM_NAME")) {
	                String subItem = extras.getString("TAB_ITEM_NAME");
	                // Do something with that string
	            }
	        }
	    }

	
	}
	
