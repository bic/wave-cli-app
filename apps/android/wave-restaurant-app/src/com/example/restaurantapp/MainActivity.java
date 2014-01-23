package com.example.restaurantapp;


import java.util.List;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class MainActivity extends FragmentActivity implements TabContentHolder {
    // Fragment TabHost as mTabHost
    
    
    
private List<TabContent> content;

@Override
protected void onCreate(Bundle savedInstanceState) {
	
super.onCreate(savedInstanceState);

setContentView(R.layout.activity_main);
this.content=TestData.generateTabContentList(null);

   

    
    //mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Tab 121"),
    //        Tab1Fragment.class, null);
    
    
    //mTabHost.addTab(mContent);
    /*mTabHost.addTab(mTabHost.newTabSpec("delete_tab").setIndicator("",
    		getResources().getDrawable(R.drawable.ic_delete)),
            Tab3Fragment.class, null);
 	*/
    
    //ImageButton delete_btn = (ImageButton) findViewById(R.id.delete_btn);
    
  
    
   /* delete_btn.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			//mTabHost.removeViewAt(mTabHost.getCurrentTab());
			//tabHost.removeTab(tabHost.getCurrentTab());
			
			//child.setVisibility(View.GONE);
		
		}
	});
    
    ImageButton copy_btn = (ImageButton) findViewById(R.id.copy_btn);
    copy_btn.setOnClickListener(new OnClickListener() {
    			
		@Override
		public void onClick(View v) {
			//int index = mTabHost.getCurrentTab();
			//tabHost.duplicate();
		}
	});
    
   /** 
   mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
		
		@Override
		public void onTabChanged(String tabId) {
			// TODO Auto-generated method stub
			if (tabId.equals("tab2")) {
				TabSpec new_offer_tab_spec = mTabHost.newTabSpec("Propunerea2").setIndicator("Propunerea2");
				
				mTabHost.addTab(new_offer_tab_spec, Tab1Fragment.class, null);
				
				
			}
			
		}
	});
    **/
    
    
	}

@Override
public List<TabContent> getContent() {
	// TODO Auto-generated method stub
	return this.content;
}
}