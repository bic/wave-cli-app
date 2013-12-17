package net.maivic.comm;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import net.maivic.comm.utils.Math;
import net.maivic.comm.Maivic.MessageContainer;

public class DefaultLazyResponse<T> implements SettableLazyResponse<T> {
	

	private static final int ON_SUCCESS=1;
	private static final int ON_FAILURE= 1<<1;
	private static final int BEFORE_FAILURE= 1<<2;
	private Callback<Integer> cb;
	private int step_size;
	private int progress;
	private int max_progress;
	private T result;
	private ExecutionException failedThrowable;
	private List<Callback<LazyResponse<T>>> doneCallBacks=new ArrayList<Callback<LazyResponse<T>>>();
	private List<Integer> doneCallBackMasks=new ArrayList<Integer>();
	private List<Callback<LazyResponse<T>>> progressCallBacks=new ArrayList<Callback<LazyResponse<T>>>();
	private Function<MessageContainer,T> resultTransformer=null;
	private List<Callback<SettableLazyResponse<T>>> beforeFailureCallBacks = new ArrayList<Callback<SettableLazyResponse<T>>>();
	private boolean result_set=false;
	public DefaultLazyResponse () {
		this.max_progress = Integer.MAX_VALUE;
		this.step_size = 0;
	}
	
	public void setSuccess(T result) {
		
		synchronized (this) {
	
			this.result= result;
			this.result_set=true;
			this.consumeCallbacks(ON_SUCCESS);
			try{
				this.notify();
			} catch (Error e){
				e.printStackTrace();
				throw e;
			}
		}
	}
	public void setFailure(Throwable t) {
		try {
			synchronized(this.beforeFailureCallBacks) {
				for (Callback<SettableLazyResponse<T>>cb : this.beforeFailureCallBacks) {
					cb.call(this);
				}
			}
		} catch (RetryOnFailure r) {
			return;
		}
		synchronized (this) {
			t.printStackTrace();
			this.failedThrowable=new ExecutionException(t);
			
			this.notify();
		}
		this.consumeCallbacks(ON_FAILURE);
		
	}	
	public void setProgress( int i) {
		if(this.progress > this.max_progress) {
			throw new IllegalArgumentException("progress cannot be set to more than max_progress");
	    }else if (i < this.progress()) {
			throw new IllegalArgumentException("Progress is less than it was before");
		} else if (i==this.progress()) {
			return ;
		} else {
			if(step_size == 0 || Math.floorDiv(progress , step_size) < Math.floorDiv(i , step_size)){
				synchronized (this.progressCallBacks) {
					this.progress = i;
					this.announceProgress();
				}
			}
			this.progress = i;
		}
		
	}
	private int announceProgress(){
		for(Callback<LazyResponse<T>> cb : progressCallBacks){
			cb.call(this);
		}
		return progressCallBacks.size();
	}
	public void addToProgress( int i ) {
		this.setProgress(this.progress() + i);
	}
	public int progress() {
		return this.progress;
	}

	public boolean cancel(boolean mayInterruptIfRunning) {
		throw new UnsupportedOperationException("Service Interruption not implemented");
	}
	public boolean isCancelled() {
		return false;
	}
	public boolean isDone() {
		if (this.result_set) {
			return true;
		} else if (this.failedThrowable != null){
			return true;
		}
		return false;
	}
	public T get() throws InterruptedException, ExecutionException {
		try {
			return this.get(-1,null);
		} catch(TimeoutException e ) {
			throw new IllegalStateException("timeout occured in operation without timeout!",e);
		}
	}
	public T get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {
		if(unit!=null && timeout < 0) {
			timeout=unit.toMillis(timeout);
		}
		long deadline = System.currentTimeMillis() + timeout;
		while ((System.currentTimeMillis() < deadline) || (timeout < 0)){
			try {
				synchronized (this) {
					if (timeout > 0 ) {
						this.wait(deadline = System.currentTimeMillis());
					}else {
						
						this.wait();
					}
				}
			} finally{
				synchronized (this) {
					if (this.isDone()) {
						if(this.failedThrowable!=null) {
							throw this.failedThrowable;
						}
						return this.result;
					}
				}
				try{
					synchronized (this) {
						this.notify();
					}
				} catch(IllegalMonitorStateException e){
					e.printStackTrace();
				}
			}
		}
		throw new TimeoutException();
	}

	public void addProgressCallBack(Callback<LazyResponse<T>> cb, int step_size) {
		synchronized (this) {
			if(!this.callbackImmediately(cb, ON_SUCCESS|ON_FAILURE)){
				synchronized (this.progressCallBacks) {
					this.progressCallBacks.add(cb);
				}
			}
		}
	}

	public void addDoneCallback(Callback<LazyResponse<T>> cb) {
		synchronized (this) {
			if(this.isDone()) {
				cb.call(this);
				return;
			}
			synchronized (this.doneCallBacks) {
				this.doneCallBacks.add(cb);
				this.doneCallBackMasks.add(ON_FAILURE|ON_SUCCESS);
			}
		}
	}

	public void addSuccessCallback(Callback<LazyResponse<T>> cb) {
		synchronized (this) {
			if(!this.callbackImmediately(cb, ON_SUCCESS)){
				synchronized (this.doneCallBacks) {
					this.doneCallBacks.add(cb);
					this.doneCallBackMasks.add(ON_SUCCESS);
				}
			}
		}
	}
	private boolean callbackImmediately( Callback<LazyResponse<T>> cb, int on_event){
		if (this.isDone()) {
			if((on_event & ON_SUCCESS) >0 &&(this.result_set)) {
				cb.call(this);
				return true;
			} else 	if((on_event & ON_FAILURE) >0 &&(this.failedThrowable != null)) {
				cb.call(this);
				return true;
			} else {
				return false;
			}
			
		} else {
			return false;
		}

	}
	private int consumeCallbacks(int in_event) {
		for (Callback<LazyResponse<T>> cb : progressCallBacks){
			cb.call(this);
		}
		int i =0;
		for (Callback<LazyResponse<T>>cb : doneCallBacks) {
			if((in_event & this.doneCallBackMasks.get(i))>0){
				/**
				 * Note that this might throw {@link RetryOnFailure}
				 */
				cb.call(this);
			}
			i++;
		}
		doneCallBackMasks.clear();
		doneCallBacks.clear();
		i+=progressCallBacks.size();
		progressCallBacks.clear();
		return i;
	}
	public void addFailureCallback(Callback<LazyResponse<T>> cb) {
		synchronized (this) {
			if(!this.callbackImmediately(cb, ON_FAILURE)){
				synchronized (this.doneCallBacks) {
					this.doneCallBacks.add(cb);
					this.doneCallBackMasks.add(ON_FAILURE);
				}
			}
		}
	}
	public void addBeforeFailureCallback(Callback<SettableLazyResponse<T>> cb) {
		synchronized (this) {
				synchronized (this.beforeFailureCallBacks) {
					this.beforeFailureCallBacks.add(cb);
				}
		}
	}
	
	public void setStep(int step) {
		synchronized (this) {
			this.step_size=step;	
		}
	}

	public int getStep() {
		return this.step_size;
	}
	public void setMaxProgress(int value) {
		synchronized (this) {
			if (this.progress >= value) {
				throw new IllegalArgumentException("Value of new max progress("+value+") is less than or same as the current progress("+this.progress+")" );
			} 
			this.max_progress = value;
		}
		
	}

	public int getMaxProgress() {
		return this.max_progress;
	}
	
}
