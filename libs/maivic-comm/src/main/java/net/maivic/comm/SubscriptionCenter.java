package net.maivic.comm;

import java.util.List;


public interface SubscriptionCenter {	
	<T> Subscription<T> addSubscription(TopicFilter filter, Callback<T> cb);
	<T> List<Subscription<T>> getSubscriptions();
	<T> boolean unsubscribe(Subscription<T> subscription);
	<T> void setSubscriptionList( List<Subscription<T>> list);
}
