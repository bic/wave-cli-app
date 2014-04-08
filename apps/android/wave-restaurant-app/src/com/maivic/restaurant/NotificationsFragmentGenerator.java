package com.maivic.restaurant;

import org.apache.http.RequestLine;

import android.app.ActionBar.LayoutParams;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ListView;

import com.example.restaurantapp.R;

public class NotificationsFragmentGenerator extends ListFragment  {
	private Handler mHandler = new Handler();

	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
	    
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState); 
		
		 View v = inflater.inflate(R.layout.notifications_list_view, container, false);
		 
		 final ListView listView = (ListView) v.findViewById(android.R.id.list);
		 final ArrayAdapterNotifications adapter = new ArrayAdapterNotifications(inflater.getContext(), android.R.id.list);
         
         listView.setAdapter(adapter);
         
         adapter.notifyDataSetChanged();
 		final ImageButton showAvailableNotifs = (ImageButton) v
 				.findViewById(R.id.show_current_notifications_btn);
 		
 		//final LayoutParams params = (LayoutParams) listView.getLayoutParams();
 		
 		
		mHandler.postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				listView.setAlpha(0);
				showAvailableNotifs.setVisibility(View.VISIBLE);
			}
		}, 14000);
		
         showAvailableNotifs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				showAvailableNotifs.setVisibility(View.GONE);
				listView.setAlpha(1);
				android.view.ViewGroup.LayoutParams params = listView.getLayoutParams();
				params.height = 160;
				listView.setLayoutParams(params);
				listView.requestLayout();
				
			}
		});
         
		 return v;
	}
	

}
