package com.example.restaurantapp;

import android.app.Activity;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.KeyListener;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.restaurantapp.TabContent.Course;

public class CourseFragment extends Fragment {
    private Course course;
	private String title;
	
	private boolean enabled;
   
    
    /*@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        course = (Course) getArguments().getSerializable("content") ;
        //myFragmentManager = this.getFragmentManager();
        
    }*/
    
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Bundle args = getArguments();
        
        
        this.course=(Course)args.getSerializable("content");
    	//TextView e  = (TextView)this.getActivity().findViewById(R.id.course_name_edit);
    	
    	View v = inflater.inflate(R.layout.course_layout, container);
        //View tv = v.findViewById(R.id.set_delivery_hour);
        //View course_name = v.findViewById(R.id.course_name_edit);
    	final TextView course_name = (TextView)v.findViewById(R.id.course_name_edit);
    	final EditText course_name_editor = (EditText) v.findViewById(R.id.course_name_edit2);
    	final KeyListener listener = course_name.getKeyListener();
    	//course_name.setFocusable(false);
    	//course_name.setKeyListener(null);
    	
        TextView course_type = (TextView) v.findViewById(R.id.course_type);
    	course_type.setText(this.title);
        ((TextView) course_name).setText(this.course.getTitle());
        ((TextView) course_name_editor).setText(this.course.getTitle());
        
         
         final CheckBox cb = (CheckBox)v.findViewById(R.id.configure);
         cb.setChecked(this.course.isConfigurable());
         cb.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				CourseFragment.this.course.setConfigurable(isChecked);
			}
		});
      
         cb.setEnabled(this.enabled);
         
         ImageButton delete_course = (ImageButton)v.findViewById(R.id.delete_course);
         delete_course.setOnClickListener(new OnClickListener() {
		

			@Override
			public void onClick(View v) {
				((TextView) course_name).setText("Neconfigurat");
				course_name_editor.setText("Neconfigurat");
				cb.setChecked(false);
				cb.setEnabled(false);
				
			}
		});
         
         final ImageButton edit_course_name_btn = (ImageButton)v.findViewById(R.id.edit_course_name);
         final ImageButton save_course_name_btn = (ImageButton)v.findViewById(R.id.save_course_name);
         
         edit_course_name_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//EditText course_name = (EditText)v.findViewById(R.id.course_name_edit);
				course_name.setVisibility(View.GONE);
				course_name_editor.setVisibility(View.VISIBLE);
				edit_course_name_btn.setVisibility(View.GONE);
				save_course_name_btn.setVisibility(View.VISIBLE);
				int textLength = course_name_editor.getText().length();
				course_name_editor.setSelection(textLength, textLength);
				
				
			}
		});
         
         
         save_course_name_btn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				course_name.setVisibility(View.VISIBLE);
				course_name_editor.setVisibility(View.GONE);
				edit_course_name_btn.setVisibility(View.VISIBLE);
				save_course_name_btn.setVisibility(View.GONE);
				
			}
		});
         
         
         
        
         course_name_editor.addTextChangedListener(new TextWatcher() {
			
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
				course_name.setText(s);
				
				
			}
		});
         
         course_name_editor.setOnFocusChangeListener(new View.OnFocusChangeListener() {
        	    @Override
        	    public void onFocusChange(View v, boolean hasFocus) {
        	        if (!hasFocus) {
        	        	course_name.setKeyListener(null);
        	        	
        	        }
        	    }
        	});
         
    	return v;
        
        
    }
	
	@Override
	public void onInflate(Activity activity, AttributeSet attrs,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onInflate(activity, attrs, savedInstanceState);
		int[] attrsArray = {android.R.attr.id, 
				R.attr.title,
				R.attr.configurable};
		TypedArray ta = activity.obtainStyledAttributes(attrs, attrsArray);
		int id =  ta.getResourceId(0, View.NO_ID);
		title = ta.getString(1);
		enabled=ta.getBoolean(2,true);
		Resources res = ta.getResources();
		
		Activity a = activity;
		this.setCourseArg(id, (TabContentHolder) activity);
		
	}	
	 private void setCourseArg(int id, TabContentHolder holder ) {
		    
		
	    	Fragment f = this;
	    	int var = f.getId();
	    	
	    	//FragmentManager fm = getFragmentManager();
	    	//Fragment fragment_byID = fm.findFragmentById(id);
	    	//f.setArguments(null);
	    	Bundle arg = new Bundle();
	    	
	        Course c=null;
	        switch(id){
	        case R.id.first_course_fragment:
	        	c = holder.getContent().get(0).courses.get(0);
	        	break;
	        case R.id.main_course_fragment:
	        	c = holder.getContent().get(1).courses.get(1);
	        	break;
	        case R.id.third_course_fragment:
	        	c = holder.getContent().get(2).courses.get(2);
	        	break;	
	        }
	        arg.putSerializable("content", c);
	        
	        f.setArguments(arg);
	        
	    }
   
}
