package com.maivic.restaurant;

import com.example.restaurantapp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SideCourseFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        
    	View v = inflater.inflate(R.layout.course_layout, container, false);
        //View tv = v.findViewById(R.id.set_delivery_hour);
        View tv = v.findViewById(R.id.course_type);
    	((TextView)tv).setText("Nu avem desert");
    	
    	
    	return v;
        
        
    }
}
