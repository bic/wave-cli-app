package com.maivic.restaurant;

import java.util.List;


import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.restaurantapp.R;


public class CourseConfigurationFragmentList extends ListFragment{
	private ListView listView;
	private CustomArrayAdapter myAdapter;
	private String section_title;
	private String third_nr_picker;
	private String second_nr_picker;
	private String first_nr_picker;
	private int myOptions;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState); 
		 int idx = 0;
		 View v = inflater.inflate(R.layout.course_configuration_fragment_layout, container, false);
		 
		 TextView titleView = (TextView) v.findViewById(R.id.proposal_configuration_title);
		 titleView.setText(this.section_title);
		
		 listView = (ListView) v.findViewById(android.R.id.list);
		 
		 myAdapter = new CustomArrayAdapter(inflater.getContext(), android.R.id.list, myOptions);
         
		 myAdapter.setFirstNumberPickerTitle(this.first_nr_picker);
         myAdapter.setSecondNumberPickerTitle(second_nr_picker);
         myAdapter.setThirdNumberPickerTitle(third_nr_picker);
         myAdapter.setOptionsFragmentId(myOptions);
		 listView.setAdapter(myAdapter);
		 
        // listView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,myA));
         return v;
	}
	
   
	@Override
	public void onInflate(Activity activity, AttributeSet attrs,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onInflate(activity, attrs, savedInstanceState);
		int[] attrsArray = {android.R.attr.id, 
				R.attr.configuration_section_title,
				R.attr.first_number_picker_title,
				R.attr.second_number_picker_title,
				R.attr.third_number_picker_title
				};
		TypedArray ta = activity.obtainStyledAttributes(attrs, attrsArray);
		//int id =  ta.getResourceId(0, View.NO_ID);
		myOptions = ta.getResourceId(0, View.NO_ID);
		section_title = ta.getString(1);
		first_nr_picker = ta.getString(2);
		second_nr_picker = ta.getString(3);
		third_nr_picker = ta.getString(4);
		Resources res = ta.getResources();
		
		
		
	}	
	 
	
}




