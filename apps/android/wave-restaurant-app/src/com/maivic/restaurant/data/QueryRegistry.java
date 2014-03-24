package com.maivic.restaurant.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import net.maivic.protocol.Model.Offer;

import android.app.Activity;
public abstract class QueryRegistry<T > {
	
	public abstract long getValueId( T value);
	public QueryRegistry(Activity a) {
		this.activity=a;
	}
	int id_seq=0;

	Map<Integer,UpdateDataCallback<T>> valueListener = new HashMap<Integer,UpdateDataCallback<T>>();

	public int registerValueListener(UpdateDataCallback<T> cb) {
		valueListener.put(id_seq,cb);
		id_seq++;
		return id_seq-1;
	}

	public void unregisterValueListener(int id) {
		valueListener.remove(id); 
	}

	boolean scheduledDataUpdate=false;
	private synchronized void sendDataUpdatedNotification() {
		if(scheduledDataUpdate) return;
		
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				
				List<Long> newIds = new ArrayList<Long>(newTs);
				newTs.clear();
				Map<Long, T> values = new TreeMap<Long, T>(receivedValues);
				receivedValues.clear();
				scheduledDataUpdate = false;
				for (UpdateDataCallback<T> cb : valueListener.values()) {
					
					for (long id : newIds) {
						if (values.get(id) instanceof Offer){
							int i=0;
							i=1;
							
						}
						cb.updateValues(values.get(id));
						
						
					}
				}	
			}
		});
		scheduledDataUpdate=true;
		
	}
	private Activity activity;


	private Map<Long,T> receivedValues = new  HashMap<Long,T>();
	private List<Long> newTs = new ArrayList<Long>();
	public void setreceivedValues(List<T> newValues) {
		for(T o: newValues){
			T oldval = this.receivedValues.put(getValueId(o), o);
			if(oldval==null || !oldval.equals(o)){
				newTs.add(getValueId(o));
			}
		}
		this.sendDataUpdatedNotification();
	}
	
	
	public void setreceivedValue(T newValue) {
		receivedValues.put(getValueId(newValue), newValue);
		newTs.add(getValueId(newValue));
		this.sendDataUpdatedNotification();
	}

}
