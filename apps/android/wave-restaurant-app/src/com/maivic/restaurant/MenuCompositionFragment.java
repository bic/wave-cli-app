package com.maivic.restaurant;

import java.util.ArrayList;
import java.util.List;

import net.maivic.context.Log;

import com.example.restaurantapp.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabWidget;
import android.widget.TextView;


public class MenuCompositionFragment extends Fragment {
	private static final String TAG = MenuCompositionFragment.class.getName();
	private static Log log = net.maivic.context.Context.get().log();
	
	
	public static class ImageFragment extends Fragment{
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			// TODO Auto-generated method stub
			return null;
			//View v = inflater.inflate(R.layout.add_button, container, false);
	     //return v;
		
	    
		}	
	
	}

  
	

	
	/*public MenuCompositionFragment(){
		
	}*/
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}

	/*
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		//this.getActivity().setContentView(R.layout.menu_compositilayout);  
		
		
		super.onCreate(savedInstanceState);
		mContent = new ArrayList<TabContent>();
	    
		getContent().add(new TabContent("propunerea1"));
	    getContent().add(new TabContent("propunerea2"));
	    getContent().add(new TabContent("propunerea3"));
	    
	    
		//tabHost.setup(this.getActivity(), getActivity().getSupportFragmentManager());
		
		//tabHost.setCurrentTab(0);
	    
		
	}*/
	FragmentTabHost tabHost;
	private View right_side_views;
	@Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
			//super.onCreate(savedInstanceState);
			//View ret = inflater.inflate(R.layout.menu_composition_layout, container);
			//View rootView = inflater.inflate(R.layout.menu_composition_layout, container, false);
			View v = inflater.inflate(R.layout.menu_composition_layout, container);
		    
			ViewGroup tabs =  (ViewGroup)v.findViewById(android.R.id.tabs);
			ViewGroup parent = (ViewGroup) tabs.getParent();
			
			
			//View v;
			
			//log.d(TAG, "Creating TabHost"+tabHost.toString());
			
			tabHost = new FragmentTabHost(getActivity());
		    
			//tabHost=(FragmentTabHost)v.findViewById(android.R.id.tab);
			 this.tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
			    //v=tabHost;
		    tabs.addView(tabHost,parent.indexOfChild(tabs));
		    
		    ImageButton b_delete = (ImageButton) v.findViewById(R.id.button2);
		    
		    right_side_views = v.findViewById(R.id.right_of_tabs);
		    
		    
		    //b_delete.setLayout
		    
			//((ViewGroup)b_edit.getParent()).removeView(b_edit);
			//((ViewGroup)b_delete.getParent()).removeView(b_delete);
		   //parent.removeView(tabs);
		   ((ViewGroup) right_side_views.getParent()).removeView(right_side_views);
		   
		   
		   	
		   
			
			//LayoutInflater.from(this.getActivity()). 	 	
			//tabHost=(FragmentTabHost)FragmentTabHost.inflate(this.getActivity(), R.layout.menu_composition_layout,container);
			//this.tabHost.setup(getActivity(), getChildFragmentManager(), R.id.menu_composition_fragment);
	        
			//this.tabHost.setup(getActivity(), getChildFragmentManager(), R.id.realtabcontent);
	        //tabHost.newTabSpec("Tab1").setIndicator("propunerea1");
			/*
			 List<String> itemsToBeDisplayed = Arrays.asList("A");
			 for (String subItem : itemsToBeDisplayed) {
		            // Give along the name - you can use this to hand over an ID for example
		            Bundle b = new Bundle();
		            b.putString("TAB_ITEM_NAME", subItem); 	

		            // Add a tab to the tabHost
		            tabHost.addTab(tabHost.newTabSpec(subItem).setIndicator(subItem), ContentFragment.class, b);
		        }
			*/
	        //tabHost.addTab(newTabSpec("Tab1").setIndicator(("propunerea1"),getResources().getDrawable(android.R.id.tabs)));
	   
			Bundle params = new Bundle();
	        params.putInt("id", 1);
	       
	        tabHost.addTab(tabHost.newTabSpec("TAB1").setIndicator("Propunerea1"),
	                ProposalFragment.class, params);
	        params = new Bundle();
	        params.putInt("id", 2);
	       tabHost.addTab(tabHost.newTabSpec("TAB2").setIndicator("Propunerea2"),
	               ProposalFragment.class, params);
	       params = new Bundle();
	       tabHost.addTab(tabHost.newTabSpec("TAB3").setIndicator("Propunerea3"),
	                ImageFragment.class, params);
	       
	       //tabHost.setOnTabChangedListener((OnTabChangeListener) this);
	   
	   		TabWidget tw = tabHost.getTabWidget();
	   		//tw.addView(b_edit);
	   		//tw.addView(b_delete);
	       tw.addView(right_side_views);
	       
	       ImageButton b_edit = (ImageButton) right_side_views.findViewById(R.id.button1);
		   b_edit.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//View rv = v.findViewById(R.id.right_of_tabs);
				//TabWidget tw = tabHost.getTabWidget();
				//((ViewGroup) rv.getParent()).removeView(rv);
				
				Bundle params = new Bundle();
				int i=tabHost.getTabWidget().getChildCount();
		        if (i<6){
		           params.putInt("id", i+1);
		           ViewGroup tabsParent = (ViewGroup) right_side_views.getParent();
			       tabsParent.removeView(right_side_views);
			       String TAB_NAME = "Propunerea" + i; 
			       String TAB_SPEC = "TAB" + i;
		           tabHost.addTab(tabHost.newTabSpec(TAB_SPEC).setIndicator(TAB_NAME),
			               ProposalFragment.class, params);
			       tabsParent.addView(right_side_views);
		           //tabHost.setCurrentTab(params.getInt("id"));
			       tabHost.findViewById(params.getInt("id"));
			       
		        }
		        
				
		       //tw.addView(rv);
				
			}
		   });
		   b_delete.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					//View rv = v.findViewById(R.id.right_of_tabs);
					//TabWidget tw = tabHost.getTabWidget();
					//((ViewGroup) rv.getParent()).removeView(rv);
					
					Bundle params = new Bundle();
					int i=tabHost.getTabWidget().getChildCount();
			        if (i<5){
			           params.putInt("id", i+1);
			           ViewGroup tabsParent = (ViewGroup) right_side_views.getParent();
				       tabsParent.removeView(right_side_views);    
			           int currTab = tabHost.getCurrentTab();
				       
			           View t = tabHost.getTabWidget().getChildTabViewAt(currTab);
			           tabsParent.removeView(t);
			           
			           tabHost.setCurrentTab(Math.max(Math.min(tabHost.getChildCount(), currTab), 0));
			           tabsParent.addView(right_side_views);
			           tabsParent.refreshDrawableState();
			        }
			        
					
			       //tw.addView(rv);
					
				}
			   });
	       
	       
	       //tabHost.
			/*this.tabHost=  new FragmentTabHost(getActivity());
			this.tabHost.setup(this.getActivity(), this.getChildFragmentManager(), R.id.realtabcontent);
			
			//tabHost.newTabSpec("Tab1").setIndicator("propunerea1"),
		    		//getResources().getDrawable(android.R.id.tabs));
	        
	        //updateView();
	        return this.tabHost;*/
			return v;
	 }
		
	/*	
	
	 @Override
	public void onStart() {
		updateView();
		super.onStart();
	}
	public void updateView(){
		//this.updateView(this.tabHost);
		
	}
	
	public void updateView(TabHost tabHost){
		int i=0;
		
		int childcount = tabHost.getChildCount()-2;
		for (TabContent content : getContent()){
			Bundle params = new Bundle();
			params.putInt("id", i);
			View visibleView=null;
			if (i<childcount){
				visibleView = tabHost.findViewById(android.R.id.tabs).findViewWithTag(i);
				TextView t = (TextView)  visibleView.findViewById(android.R.id.title);
				t.setText(content.getTitle());
			}
			else {
				
			    this.tabHost.addTab(this.tabHost.newTabSpec("tab1").setIndicator("Tab 121"),
			    		Fragment.class, params);
			   
			    //tabHost.newTabSpec("Tab1").setIndicator("propunerea1",
			    //		getResources().getDrawable(android.R.id.tabs));
			    
			    visibleView = tabHost.findViewWithTag(i);
			    TextView t = (TextView) visibleView.findViewById(android.R.id.title);
			    t.setText(content.getTitle());
			    childcount = childcount+1;
			    
			}
			visibleView.setVisibility(View.VISIBLE);
			i = i+1;
			
		}
		if (childcount > this.getContent().size()){
			for (i=this.getContent().size(); i< childcount; i++){
				 tabHost.findViewWithTag(i).setVisibility(View.GONE);
			}
		}
	
		
	}
	*/
	
/*	
	void removeTab(Integer index){
		getContent().remove(index);
		this.updateView();
	}
	
	
	public void duplicate(int index) {
			
		getContent().add(new TabContent(getContent().get(index)));
		
		this.updateView();
		String viewtag = Integer.toString(getContent().size()-1);
		tabHost.setCurrentTabByTag(viewtag);
		
	
		
		//this.getContent().add(index+1, this.getContent().get(index));
		//this.getCurrentTabView().findViewWithTag(tag))
	}
	*/	
	
	
	
}

