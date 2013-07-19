package net.maivic.comm;


public interface Subscription <T>  {
	Callback<T> getCallback();
	/**
	 * Subscriptions are active only while managed by the
	 * {@link SubscriptionCenter}
	 * @return
	 */
	boolean isActive();
	/**
	 * Position in the Queue. The {@link Callback} Handlers are called
	 * according to the position of the subscription. 
	 * @return the position of the Subscription or null in case the subscription is not active
	 */
	Integer getPosition();
	/**
	 * Milliseconds until the subscription times out if no message has been received;
	 * When a timeout occurs the Subscription is removed from the queue;
	 * @return
	 */
	int getTimeOutmillis();
	
	
}