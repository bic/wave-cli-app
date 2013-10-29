package net.maivic.comm;

import java.util.List;


public interface SubscriptionCenter <T> {
	void addSubscription(RoutingFilter<T> filter, Callback<T> cb);
	List<RoutingFilter<T>> getFilters();
	List<Callback<T>> getCallback();
	
	boolean unsubscribe(RoutingFilter<T> filter);
	boolean unsubscribe(Callback<T> filter);
}
