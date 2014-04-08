package com.maivic.restaurant;

import java.util.ArrayList;
import java.util.List;

import net.maivic.protocol.Model;
import net.maivic.protocol.Model.Client;
import net.maivic.protocol.Model.ClientOrBuilder;
import net.maivic.protocol.Model.NotificationOrBuilder;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.restaurantapp.R;
import com.maivic.restaurant.data.NotificationGenerator;
import com.maivic.restaurant.data.UpdateDataCallback;


public class ArrayAdapterNotifications  extends ArrayAdapter<NotificationOrBuilder> {
	
	int layoutResourceId;
	Context mContext;
	private Handler mHandler = new Handler();
	private NotificationGenerator notificationGenerator = new NotificationGenerator();
	private static List<ClientOrBuilder> clientList = new ArrayList<ClientOrBuilder>();
	private static List<NotificationOrBuilder> notificationList = new ArrayList<NotificationOrBuilder>();
	
	public static void initialiseData() {

		Client.Builder client = Client.newBuilder();
		client.setUser("John");
		
		client.hasPassmd5();
		clientList.add(client);

		Client.Builder client2 = Client.newBuilder();
		client2.setUser("George");
	
		client2.hasPassmd5();
		clientList.add(client2);
	}
	
	public ArrayAdapterNotifications(Context mContext, int layoutResourceId) {

		super(mContext, layoutResourceId);
		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		/*initialiseData();
		for (ClientOrBuilder client : clientList) {
			add(client);
		}*/
		Runnable r;
		OfferActivity activity = (OfferActivity) mContext;
		
		
        activity.messageContent.registerValueListener(new UpdateDataCallback<Model.NotificationOrBuilder>(){

			@Override
			public void updateValues(NotificationOrBuilder value) {
				// TODO Auto-generated method stub
				add(value);
				//notificationList.add(value);
				
				//handlerFunction(notificationList);
				//notificationGenerator.DisplayNotification((int) notificationSeverity, notificationMessage, activity);
				
			}

			private void handlerFunction(final List<NotificationOrBuilder> notificationList) {
				// TODO Auto-generated method stub
				 final Handler mHandler = new Handler();
			        
			       Runnable r = new Runnable() {
						
						@Override
						public void run() {
							// TODO Auto-generated method stub
						
								for (NotificationOrBuilder not:notificationList){
									add(not);
									//mHandler.postDelayed(this, 200);
									notifyDataSetChanged();
								}
								
							
							}
					};
			        mHandler.postDelayed(r, 2000);
			        
			}
        	
        });
       
        
       /* Handler mHandler = new Handler();
        
        r = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				for (NotificationOrBuilder not:notificationList){
					add(not);
					notifyDataSetChanged();
				}
			}
		};*/
        //mHandler.postDelayed(r, 2000);
        
		
		// add(notification)
		

	}
	
	@Override
	public View getView(int position, View convertViewParam, ViewGroup parent) {

		LayoutInflater mInflater = (LayoutInflater) mContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		final View convertView = convertViewParam != null ? convertViewParam
				: mInflater.inflate(R.layout.notifications_fragment_list_row, null);
		if (this.getCount() == 0)
			return convertView;

		NotificationOrBuilder notification = getItem(position);
		//ClientOrBuilder client = getItem(position);

		TextView textViewItem = (TextView) convertView
				.findViewById(R.id.notification_list_row_tV);
		textViewItem.setText(notification.getName());
		int notificationType = (int) notification.getId() % 3;
		switch (notificationType) {
		case 0:
			convertView.setBackgroundColor(-16776961);
			break;
		case 1:
			convertView.setBackgroundColor(-16711936);
			break;
		case 2:
			convertView.setBackgroundColor(-65536);
			break;

		default:
			break;
		}
		
		
		//textViewItem.setText(client.getUser());

		/*TextView textViewItem = (TextView) convertView
				.findViewById(R.id.notification_list_row_tV);
		//textViewItem.setText(notification.getName());
		textViewItem.setText("i am here");
		*/
		
		
	/*	mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				convertView.setVisibility(View.GONE);
				//showAvailableNotifs.setVisibility(View.VISIBLE);
			}
		}, 6000);*/
		
	/*	showAvailableNotifs.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				convertView.setVisibility(View.VISIBLE);
				showAvailableNotifs.setVisibility(View.GONE);
				
			}
		});*/

		return convertView;

	}
	
	

	
}
