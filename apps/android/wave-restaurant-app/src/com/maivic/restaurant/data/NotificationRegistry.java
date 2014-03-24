package com.maivic.restaurant.data;

import java.util.List;

import net.maivic.protocol.Model.Notification;
import net.maivic.protocol.Model.NotificationOrBuilder;
import android.app.Activity;

public class NotificationRegistry extends QueryRegistry<NotificationOrBuilder> {
	//private NotificationQuery?! 
	//private Notification notify;
	
	public NotificationRegistry(Activity a){
		super(a);
	}
	
	/*public void getMessagesAsync(){
		//query?
		//notify
		
		
		
	}*/
	
	

	@Override
	public long getValueId(NotificationOrBuilder value) {
		// TODO Auto-generated method stub
		return value.getId();
	}

	@Override
	public void setreceivedValues(List<NotificationOrBuilder> newValues){
		super.setreceivedValues(newValues);
	}
	
}
