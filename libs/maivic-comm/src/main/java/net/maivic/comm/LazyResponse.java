package net.maivic.comm;

import net.maivic.comm.exception.ConcurrecyException;
import net.maivic.comm.exception.HaveCallbackException;
import net.maivic.comm.exception.WaitingForResult;

public interface LazyResponse<T> {
	/**
	 * Waits (=blocks Thread) until a response is returned from the server
	 * for the RPC Call which created this Lazy Response;
	 * @return
	 * @throws HaveCallbackException if a callback is installed when calling this method
	 */
	T waitForResponse(int timeOutMillis) throws ConcurrecyException ;
	/**
	 * Has response 
	 * @return
	 */
	boolean hasResponse();
	long requestCycleNanosElapsed();
	/**
	 * Installs a callback which is called when the RPC Call returns.
	 * @param cb
	 * @throws WaitingForResult Thrown when the setCallbackOnResponse is attempted on
	 * a {@link LazyResponse} which is waiting for a result;
	 */
	void setCallbackOnResponse(Callback<T>cb) throws WaitingForResult;
	
	/**
	 * For Rpc-s with progress Implementation
	 * 
	 * Progress (integer between 1-10000) where 10000 is the only value
	 * for which hasResponse() == true;
	 * @return progress
	 */
	int progress();
	/**
	 * Schedule a Callback everytime n*step_size < 10000 has been reached
	 * 
	 * cb is s not called if progress has reached 10000. In this case
	 * setCallbackOnResponse is called if set.
	 * @param cb callback called with the current progress
	 * @param step_size sets how much progress must at least be made before cb is called
	 */
	void setProgressCallBack (Callback<Integer> cb, int step_size);
}
