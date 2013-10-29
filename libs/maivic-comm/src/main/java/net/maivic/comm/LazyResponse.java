package net.maivic.comm;

import java.io.ObjectInputStream.GetField;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import net.maivic.comm.exception.ConcurrecyException;
import net.maivic.comm.exception.HaveCallbackException;
import net.maivic.comm.exception.WaitingForResult;
/**
 * Interface for Lazy response. This implements the {@link Future} interface, but adds callback functionality
 * Callbacks can be added for :
 *  
 *  <li> Result arrival (see {@link #addDoneCallback(Callback)})
 *  <li> Success result arrival (see {@link #addSuccessCallback(Callback)})
 *  <li> Error result arrival (see {@link #addFailureCallback(Callback)})
 *  <li> Progress Notifications (see {@link #addProgressCallBack(Callback)};
 *  
 *  All callbacks receive the {@link LazyResponse} object as an argument. Results can be read as described in {@link Future}
 *  Basically when the result is ready the {@link #get()} function will not block and either throw an {@link ExecutionException}
 *  or return the T value
 *  
 *  Progress Handling follows this protocol:
 *  	<li> Upon construction the maximum value must be either passed to the constructor or be {@link Integer#MAX_VALUE}
 *  	<li> When the value of {@link #progress()} increases all cb links are called in case
 *  		<li> {@link Math#floorDiv(int, int) Math.floorDiv(oldvalue, step) < Math.floorDiv(newvalue, step) }
 *          <li> in case a result has arrived.
 *          
 * Subclasses must set progress to {@link #getMaxProgress()} if a result has arrived
 * All callbacks will be called immediately in case {@link #isDone() == True}
 * if {@link #isDone()} is true all registered callbacks must be removed after eventual calling (the call may depend on success status)
 * @author Paul Balomiri
 *
 * @param <T> The Result type got {@link #get()} and {@link #get(long, java.util.concurrent.TimeUnit)}
 */
public interface LazyResponse<T> extends Future<T>{
	/**
	 * For Rpc-s with progress Implementation
	 * 
	 * Progress (integer between 1-10000) where 10000 is the only value
	 * for which hasResponse() == true;
	 * @return progress
	 */
	int progress();
	
	/**
	 * Set the notification stepping. The progress callbacks  will only be called if
	 * floor(progress) < floor(new progress value)
	 * Np progress callback will be called directly from this method 
	 * @param step
	 */
	void setStep( int step);
	/**
	 * 
	 * @return the current step size
	 */
	int getStep();
	
	/**
	 * Set when progress() has been reached this value the process must be done.
	 * Also the progress is automatically set to this value when an error or a result has been received
	 * @param value new maximum progress value
	 */
	void setMaxProgress(int value);
	/**
	 * 
	 * @return maximum  progress value
	 */
	int getMaxProgress();
	
	
	/**
	 * Schedule a Callback everytime n*step_size has been reached where
	 * n is the current progress
	 * The progress callbacks are called one final time when the progress reaches {@link LazyResponse.getProgressMax()}
	 * Also all callbacks are removed after this final call. 
	 * @param cb callback called with the current progress
	 * @param step_size sets how much progress must at least be made before cb is called
	 */
	void addProgressCallBack (Callback<LazyResponse<T>> cb, int step_size);
	/***
	 * adds a callback which will be called when the result for this response arrives
	 * @param cb
	 */
	void addDoneCallback(Callback<LazyResponse<T>> cb);
	/**
	 * Adds a callback to be called when the response arrives in case
	 * the result is a success
	 * @param cb The callback accepting a success notification
	 */
	void addSuccessCallback(Callback<LazyResponse<T>> cb);
	/**
	 * Adds a callback to be called when the response arrives in case
	 * the result is an exception
	 * @param cb
	 */
	void addFailureCallback(Callback<LazyResponse<T>> cb);
}
