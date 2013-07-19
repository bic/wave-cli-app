package net.maivic.comm;

import net.maivic.comm.exception.HaveCallbackException;
import net.maivic.comm.exception.WaitingForResult;



public class DummyLazyResponse<T> implements LazyResponse<T> {
	private T response;
	private long start = 0;
	Callback<T> cb;
	private boolean waiting;
	private long end;
	
	public DummyLazyResponse(T object, int delay) {
		TestConfig.cb_thread.insertCb(new Callback<T>() {
			DummyLazyResponse<T> l = DummyLazyResponse.this;
			public void call(T result) {
				l.onResponse(result);
			}

			public void onError(Throwable t) {
				// Do nothing
			}
			} 
		, object, System.currentTimeMillis()+delay);
		start=System.currentTimeMillis();
	}
	
	public synchronized T waitForResponse(int timeOutMillis) throws HaveCallbackException {
		synchronized (this) {
			if(cb!=null) {
				throw new HaveCallbackException("You cannot call waitForResponse while cb is set");
			}
			this.waiting = true;
		}
		try {
			this.wait(timeOutMillis);
		} catch (InterruptedException e) {
			// Nothing unusual, just wait
		}
		
		synchronized (this) {
			this.waiting = false;
			return response;
		}
		
	}

	public boolean hasResponse() {
		return this.response != null;
	}

	public long requestCycleNanosElapsed() {
	
		return (end-start) * (long)Math.pow(10,6);
	}

	public void setCallbackOnResponse(Callback<T> cb) throws WaitingForResult {
		synchronized (this) {
			if(this.waiting){
				throw new WaitingForResult("Cannot install callback while waiting for a result!");
			}
			this.cb=cb;
		}
		
	}

	public int progress() {
		if(this.response == null){
			return 0;
		}
		return 10000;
	}

	public void setProgressCallBack(Callback<Integer> cb, int step_size) {
		throw new UnsupportedOperationException("Cannot support operation.");
	}
	void onResponse(T response) {
		synchronized(this){
			if(cb!=null) {
				end=System.currentTimeMillis();
				cb.call(response);
			}else{
				this.response=response;
				this.end=System.currentTimeMillis();
				this.notify();
			}
		}
		
	}

}
