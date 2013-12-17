package net.maivic.comm.impl;
import java.lang.reflect.InvocationTargetException;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.maivic.comm.Callback;
import net.maivic.comm.DefaultLazyResponse;
import net.maivic.comm.Maivic.MessageContainer;
import net.maivic.comm.RetryOnFailure;
import net.maivic.comm.SendStrategy;
import net.maivic.comm.SettableLazyResponse;
import net.maivic.comm.Transport;
import net.maivic.comm.TransportManager;
import net.maivic.context.Context;


public class TransportManagerImpl implements TransportManager {

	private Map<String,Class<Transport>> tranport_types = new HashMap<String,Class<Transport>>();
	private List<Transport> transports = new ArrayList<Transport>();
	private List<Callback<MessageContainer>> installCallbacksUponConstruction=new ArrayList<Callback<MessageContainer>>();

	public List<Transport> getTransports() {
		return Collections.unmodifiableList(this.transports);
	}

	public boolean removeTransport(Transport t) {
		if (! this.transports.contains(t)){
			return false;
		}
		if (t.isConnected()){
			t.tearDown(0);
		}
		this.transports.remove(t);
		return true;
	}

	public Transport addTransport(String URI_str) {
		URI uri = URI.create(URI_str);
		Class<? extends Transport> transportClass = this.tranport_types.get(uri.getScheme());
		if(transportClass == null) {
			transportClass = Context.get().< Class<? extends Transport>>get("Transport("+uri.getScheme()+")");
		}
		Transport ret=null;
		try {
			ret = transportClass.getConstructor(String.class).newInstance(URI_str);
			
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (ret!= null) this.transports.add(ret);
		for(Callback<?> cb : installCallbacksUponConstruction){
			ret.registerCallback(cb);
		}
		return ret;
		
	}

	public void connectTransportsOfType(String type) {
		for (Transport t: this.transports){
			for( String t_type :t.getTransportTypes()){
				if(t_type == type){
					t.pullUp();
				}
			}
		}
	}

	public Map<String, List<Transport>> getTransportsByType() {
		Map<String, List<Transport>> ret = new HashMap<String, List<Transport>>();
		for (Transport t: this.transports){
			for( String t_type :t.getTransportTypes()){
				if(ret.containsKey(t_type)){
					ret.get(t_type).add(t);
				} else {
					List<Transport>arr=new ArrayList<Transport>();
					arr.add(t);
					ret.put(t_type, arr);
				}
			}
		}
		return ret;
	}

	public void disconnectAll(int graceful_wait) {
		for(Transport t : this.transports){
			if(t.isConnected()){
				t.tearDown(graceful_wait);
			}
		}
	}

	public Transport[] getTransportByUri(String URI) {
		// TODO Auto-generated method stub
		return null;
	}

	@SuppressWarnings("unchecked")
	public <T> void registerIncomingAllTransports(Callback<T> cb) {
		for(Transport t : this.transports){
			t.registerCallback(cb);
		}
		Callback<MessageContainer> cast_cb = (Callback<MessageContainer>) cb;
		this.installCallbacksUponConstruction.add(cast_cb);
		
	}
	
	
	public <T> SettableLazyResponse<T> sendWithRetry ( final T message, final SendStrategy<T> strategy, final DefaultLazyResponse<T> lazyResponse){		
		if( lazyResponse == null) {
			throw new IllegalArgumentException("lazyResponse Argument may not be null");
		}
		Callback<SettableLazyResponse<T>> cb = new Callback<SettableLazyResponse<T>> () {
			public void call(final SettableLazyResponse<T> result) {
				while (strategy.hasNext()) {
					long when=strategy.next();

					Transport<T> transport = strategy.getTransport();
					if (transport==null) {
						Callable<Void> callable = new Callable<Void>() {

							private Transport<T> t;

							public Void call() throws Exception {
								System.out.print("Trying to send");
								t= strategy.getTransport();
								t.send(message, result, 0);
								System.out.print("Tried to send");
								return null;
								
							}
						};
						Context.get().getTransportManager().schedule(when,callable);
						return;
					} else {
						transport.send(message,result,when);
						throw new RetryOnFailure();
					}
				}
			
			}
		};
		if(!strategy.hasNext()) {
			lazyResponse.setFailure(new TimeoutException("Operation send timed out!"));
			return lazyResponse;
		}
		Long when = strategy.next();
		Transport<T> transport = strategy.getTransport();
		if(transport != null) {
			transport.send(message,lazyResponse, when);
			lazyResponse.addBeforeFailureCallback(cb);
			return lazyResponse;
		} else  {
			
			this.schedule(when, new Callable<Void>() {
				public Void call() throws Exception {
					TransportManagerImpl.this.sendWithRetry(message, strategy, lazyResponse);
					return null;
				}
			});
			return lazyResponse;
		}
	}

	public void schedule(long when, Callable<Void> callable) {
		ScheduledExecutorService srv =	(ScheduledExecutorService)Context.get().get(ScheduledExecutorService.class.getName());
		srv.schedule(callable, when, TimeUnit.SECONDS);
	}
}
