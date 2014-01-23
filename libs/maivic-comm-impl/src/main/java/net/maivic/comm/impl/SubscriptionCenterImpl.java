package net.maivic.comm.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.maivic.comm.Callback;
import net.maivic.comm.RoutingFilter;
import net.maivic.comm.SubscriptionCenter;
import net.maivic.context.Context;

public class SubscriptionCenterImpl<T> implements SubscriptionCenter<T> {
	List<RoutingFilter<T>> filters = new ArrayList<RoutingFilter<T>>();
	List<Callback<T>> callBacks = new ArrayList<Callback<T>>(); 
	public SubscriptionCenterImpl () {
		Context.get().getTransportManager().registerIncomingAllTransports(
				new Callback<T> (){
					public void call(T result) {
						SubscriptionCenterImpl.this.dispatch(result);
					}
				} 
				);
	}
	public void addSubscription(RoutingFilter<T> filter, Callback<T> cb) {
		synchronized(this){
			filters.add(filter);
			callBacks.add(cb);
		}
	}
	public List<RoutingFilter<T>> getFilters() {
		return Collections.unmodifiableList(filters);
	}
	public List<Callback<T>> getCallback() {
		return Collections.unmodifiableList(callBacks);
	}
	public boolean unsubscribe(RoutingFilter<T> filter) {
		synchronized (this) {
			int i = filters.indexOf(filter);
			if(i<0) {
				return false;
			} else {
				filters.remove(i);
				callBacks.remove(i);
				return true;
			}
		}
	}
	public boolean unsubscribe(Callback<T> callback) {
		synchronized (this) {
			int i = callBacks.indexOf(callback);
			if(i<0) {
				return false;
			} else {
				filters.remove(i);
				callBacks.remove(i);
				return true;
			}
		}
	}
	private boolean dispatch (T message) {
		boolean ret = false;
		synchronized (this) {
			for(int i=0; i < filters.size(); i++ ){
				if (filters.get(i).route(message)){
					callBacks.get(i).call(message);
					ret=true;
				}
			}
		}
		return ret;
	}

	
	
}
