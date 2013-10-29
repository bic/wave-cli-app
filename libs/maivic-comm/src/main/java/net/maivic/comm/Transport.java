package net.maivic.comm;



import net.maivic.comm.Maivic.MessageContainer;

/**
 * A Transport represents a layer through which messages can be
 * sent or received
 * @author paul
 *
 */
public  interface Transport<T> {
	/**
	 * Scheme of the transport
	 * @return
	 */
	public String getScheme();
	/**
	 * Type of the transport
	 * @return
	 */
	public String [] getTransportTypes();
	/**
	 * Disconnect transport. In case of Tcp/Ip connections
	 * this means disconnecting the open Sockets.
	 * 
	 * This method shall be called
	 * @param millis if >0 then wait this many milliseconds  for stateful operations to finish
	 */
	public void tearDown(int millis);
	/**
	 * Connect to the other End
	 * This function does nothing if {@link Transport.isConnected()} is true
	 */
	public void pullUp();
	/**
	 * Query if this transport is connected
	 * @return true if connected, false otherwwhise
	 */
	public boolean isConnected();
	/**
	 * Callback parameter called before interface is disconnected
	 * @param cb the Call back function
	 */
	public void cbBeforeDisconnect(Callback<Transport> cb);
	
	/**
	 * Callback Parameter called after a connect (attempt) has been made.
	 * @param cb
	 */
	public void cbAfterConnect(Callback<Transport> cb);
	/**
	 * gets the uri of the transport 
	 * @return Transport uri
	 */
	public String getUri();
	
	SettableLazyResponse<T> send(T container);
	/**
	 * 
	 * @param container The message to send
	 * @param lazyResponse The settable lazyrepronse on which to set the result
	 * @param sendAfter send the message after the given time;
	 * @return
	 */
	 void send(T container, SettableLazyResponse<T> lazyResponse,long sendAfter);
	
	void registerCallback(Callback<T> cb);
}
