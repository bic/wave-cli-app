package net.maivic.comm;
/**
 * A Transport represents a layer through which messages can be
 * sent or received
 * @author paul
 *
 */
public interface Transport {
	/**
	 * Name of the transport
	 * @return
	 */
	String getName();
	/**
	 * Type of the transport
	 * @return
	 */
	String getTransportType();
	/**
	 * Disconnect transport. In case of Tcp/Ip connections
	 * this means disconnecting the open Sockets.
	 * 
	 * This method shall be called
	 * @param millis if >0 then wait this many milliseconds  for stateful operations to finish
	 */
	void tearDown(int millis);
	/**
	 * Connect to the other End
	 * This function does nothing if {@link Transport.isConnected()} is true
	 */
	void pullUp();
	/**
	 * Query if this transport is connected
	 * @return true if connected, false otherwwhise
	 */
	boolean isConnected();
	/**
	 * Callback parameter called before interface is disconnected
	 * @param cb the Call back function
	 */
	void cbBeforeDisconnect(Callback<Transport> cb);
	
	/**
	 * Callback Parameter called after a connect (attempt) has been made.
	 * @param cb
	 */
	void cbAfterConnect(Callback<Transport> cb);
	
	/**
	 * only Message topics passing this filter will be accepted
	 * in this topic and through this {@link Transport}
	 * @return
	 */
	 TopicFilter getAcceptFilter();
}
