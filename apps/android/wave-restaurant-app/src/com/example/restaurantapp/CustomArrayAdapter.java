package com.example.restaurantapp;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.restaurantapp.TabContent.Course;
import com.example.restaurantapp.TabContent.DataCallback;

public class CustomArrayAdapter extends ArrayAdapter<TabContent.Course> {
    Context context;
	
 
    public CustomArrayAdapter(Context context, int textViewResourceId, List<Course> courses)  {
        //super(context, android.R.layout.simple_list_item_1);
    	super(context, textViewResourceId, courses);
    	this.context = context;
    
    }
 
    
   
    private TextView courseName;
    	
  
 
 
    /**
     * Populate new items in the list.
     */
    @Override
    public View getView(int position, View convertViewParam, ViewGroup parent) {
        
    	
        Course rowItem = getItem(position);
        
        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        final View convertView = convertViewParam!=null ? convertViewParam:mInflater.inflate(R.layout.list_row, null);
        
        //holder.courseCount = (TextView) convertView.findViewById(R.id.courseCount);
        this.setTitle(R.id.courseName, rowItem.getTitle(), convertView);
        this.setTitle(R.id.edit_courseName, rowItem.getTitle(), convertView);
        if(position == 0) {
	        this.setTitle(R.id.first_number_picker_title, this.firstNumberPickerTitle, convertView);
	        
	        this.setTitle(R.id.second_number_picker_title, this.secondNumberPickerTitle, convertView);
	        this.setTitle(R.id.third_number_picker_title, this.thirdNumberPickerTitle, convertView);
	        
	        
	        this.showChild(R.id.first_number_picker_title, convertView);
        	this.showChild(R.id.second_number_picker_title,convertView);
        	this.showChild(R.id.third_number_picker_title,convertView);
        } else {
        	this.hideChild(R.id.first_number_picker_title, convertView);
        	this.hideChild(R.id.second_number_picker_title,convertView);
        	this.hideChild(R.id.third_number_picker_title,convertView);
        }
        //holder.courseCount.setText(rowItem.getCourse_type());
        //notifyDataSetChanged();
        rowItem.addConfigurableListener(new DataCallback<Boolean>() {
			@Override
			public void updated(Boolean value) {
				if(value)
				convertView.setVisibility(View.VISIBLE);
				else
				convertView.setVisibility(View.GONE);
				
			}
		});
        
        
        
        final ImageButton edit_course_name_btn = (ImageButton) convertView.findViewById(R.id.edit_config_options);
        final ImageButton save_course_name_btn = (ImageButton) convertView.findViewById(R.id.save_config_options);
        final EditText edit_courseName = (EditText) convertView.findViewById(R.id.edit_courseName);
        final TextView courseName1 = (TextView) convertView.findViewById(R.id.courseName);
        edit_course_name_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				edit_courseName.setVisibility(View.VISIBLE);
				courseName1.setVisibility(View.GONE);
				edit_course_name_btn.setVisibility(View.GONE);
				
				save_course_name_btn.setVisibility(View.VISIBLE);
			}
		});
        
        
        save_course_name_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				edit_courseName.setVisibility(View.GONE);
				courseName1.setVisibility(View.VISIBLE);
				edit_course_name_btn.setVisibility(View.VISIBLE);
				
				save_course_name_btn.setVisibility(View.GONE);
				
			}
		});
        
        
        edit_courseName.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
				
				
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				
				//cb.setEnabled(true);
				//cb.setChecked(this.course.isConfigurable());
				//course_name.setText(s);
				courseName1.setText(s);
				
			}
		});
         
         edit_courseName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        	    @Override
        	    public void onFocusChange(View v, boolean hasFocus) {
        	        if (!hasFocus) {
        	        	//course_name.setKeyListener(null);
        	        	edit_courseName.setFocusable(true);
        	        	edit_courseName.setFocusableInTouchMode(true);
        	        }
        	    }
        	});
        
        
        
        notifyDataSetChanged();
        return convertView;
    }
    private void hideChild(int resId, View v) {
		v.findViewById(resId).setVisibility(View.GONE);
	}
    private void showChild(int resId, View v) {
		v.findViewById(resId).setVisibility(View.VISIBLE);
	}
    
	public void setTitle(int textViewId, String title , View parent){
    	TextView t = (TextView) parent.findViewById(textViewId);
    	t.setText(title);
    }


    private String firstNumberPickerTitle;
	public void setFirstNumberPickerTitle(String first_nr_picker) {
		// TODO Auto-generated method stub
		this.firstNumberPickerTitle = first_nr_picker;
		
	}

	public String getFirstNumberPickerTitle() {
		return firstNumberPickerTitle;
	}
	
	private String secondNumberPickerTitle;
	public void setSecondNumberPickerTitle(String second_nr_picker) {
		// TODO Auto-generated method stub
		this.secondNumberPickerTitle = second_nr_picker;
		
	}

	public String getSecondNumberPickerTitle() {
		return secondNumberPickerTitle;
	}
		

    private String thirdNumberPickerTitle;
	public void setThirdNumberPickerTitle(String third_nr_picker) {
		// TODO Auto-generated method stub
		this.thirdNumberPickerTitle = third_nr_picker;
		
	}

	public String getThirdNumberPickerTitle() {
		return thirdNumberPickerTitle;
	}
} 