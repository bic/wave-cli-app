package net.maivic.comm.service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import net.maivic.comm.ExponentialBackoffIterator;
import net.maivic.comm.SendStrategy;
import net.maivic.comm.Transport;
import net.maivic.context.Context;

public class FixedRetry<T> implements SendStrategy<T> {
	private static Map<Integer,long[]>retry_iter_cache = new HashMap<Integer, long[]>();
	public static long [] logRetryDelays(int max) {
		if(retry_iter_cache.containsKey(max)) {
			return retry_iter_cache.get(max);
		} else {
			ExponentialBackoffIterator iter = new ExponentialBackoffIterator(max);
			long[] ret = new long[max];
			int i=0;
			while( iter.hasNext() ){
				ret[i] = iter.next();
				i++;
			}
			synchronized (retry_iter_cache) {
				if(retry_iter_cache.containsKey(max)) {
					return retry_iter_cache.get(max);
				}else {
					retry_iter_cache.put(max, ret);
					return ret;
				}
				
				
			}
		}
	}
	
	private long[] retryDelays;
	int idx =-1;
	private Object transportType;
	public FixedRetry( long [] retryDelays, String transportType) {
		this.retryDelays=retryDelays;
		this.transportType=transportType;
	}
	public FixedRetry( String transportType) {
		this.retryDelays=null;
		this.transportType = transportType;
	}
	public boolean hasNext() {
		if (idx < 0) {
			return true;
		} else if(this.retryDelays == null || this.retryDelays.length == idx) {
			return false;
		} else{
			return true;
		}
	}

	public Long next() {
		idx += 1;
		if (idx == 0) {
			return System.currentTimeMillis();
		} else if(this.retryDelays == null || this.retryDelays.length == idx) {
			throw new IllegalStateException("There is no next retry");
		} else{
			return this.retryDelays[idx];
		}
	}
	public Transport<T> getTransport() {
		for (Transport<T> t: Context.get().getTransportManager().getTransports()){
			for( String typeStr : t.getTransportTypes()) {
				if(typeStr.equals(transportType)){
					return t;
				}
			}
		}
		return null;		
	}
}
