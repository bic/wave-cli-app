package net.maivic.comm;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import net.maivic.comm.Maivic.MessageContainer;



public interface TransportManager {
	
	/**
	 * Returns the currently active Transports
	 * @return
	 */
	List<Transport> getTransports();
	/**
	 * Removes a Transport from the manager
	 * If the Transport is up {@link Transport.tearDown()} will be
	 * called first
	 * @param t Transport to remove from the List of transports
	 * @return true if the Transport has successfully been removed, false otherwhise
	 */
	boolean removeTransport(Transport t);
	/**
	 * Returns the transport for a given URI
	 * @param URI uri String of the Transport
	 * @return The transport Handle
	 */
	Transport[] getTransportByUri(String URI);
	
	
 	/**
	 * Adds a transport to the Manager 
	 * @param URI the connection uri for the new Transport to be created
	 */
	Transport addTransport(String URI);
	/**
	 * connect all transports of a given type
	 * @param type the type of transport
	 * 
	 */
	void connectTransportsOfType(String type);
	
	/**
	 * Returns transports in a map grouped by type of connection
	 * @return map of Transports grouped by type
	 */
	Map<String,List<Transport>>getTransportsByType();
	/**
	 * disconnects all connected transports
	 * @param graceful_wait give this many milliseconds to gracefully shut down the connection. supply 0 for non graceful disconnect
	 */
	void disconnectAll(int graceful_wait); 
	
	public <T> void registerIncomingAllTransports (Callback<T> cb );
	public <T> SettableLazyResponse<T> sendWithRetry( final T message, final SendStrategy<T> strategy, DefaultLazyResponse<T> lazyResponse);
	void schedule(long when, Callable<Void> callable);
	
	
}
