package net.maivic.comm;

import java.util.HashSet;
import java.util.Set;
/**
 * Class managing communication threads.
 * 
 * By convention a connecting Party in a bidirectional communication channel
 * has the id 2. Also if bidirectional communications are etablished
 * the threadCreators argument is 2.
 * 
 * Threads assigned by the accepting party have the numbers 2*x
 * Threads in the connecting party have the numbers 2*x+1 
 * @author paul
 *
 */
public class ThreadManager {
	private Set<Integer> closedThreads = new HashSet<Integer>();
	private Set<Integer> knownThreads = new HashSet<Integer>();
	private int thread_seq=-1;
	private int threadCreators;
	private int myId;
	public ThreadManager( int threadCreators, int myId) {
		this.thread_seq = myId-threadCreators;
		this.myId=myId;
		this.threadCreators=threadCreators;
	}
	/**
	 * Constructor fot a Thread manager client in a 2 party communication 
	 */
	public ThreadManager() {
		this.thread_seq = 0;
		this.myId=1;
		this.threadCreators=2;
	}
	
	public synchronized int newThread() { 
			thread_seq+=this.threadCreators;
			this.knownThreads.add(thread_seq);
			return thread_seq;
	}
	public synchronized void registerKnownThread( int id) {
		if((id%this.threadCreators)==myId){
			throw new IllegalArgumentException("This Thread id must be assigned by myself. Cannot register!");
		}else if(!knownThreads.add(id)){
			throw new IllegalArgumentException("Thread id is already Known");
		}
	}
	public synchronized boolean  closeThread(int id) {
		if(id%this.threadCreators == this.myId){
			if( id > thread_seq || id < 0) {
				throw new IllegalArgumentException("No thread has been created below id 0 or above id " + Integer.toString(thread_seq));
			}
			else if (closedThreads.contains(id)){
				return false;
			} else {
				closedThreads.add(id);
				return true;
			}
		}else {
			if(this.knownThreads.contains(id)){
				return this.closedThreads.add(id); 
			} else {
				throw new IllegalArgumentException("Thread Id" + Integer.toString(id) + "has not been registered");
			}
		}
	}
	public synchronized boolean isActive( int id) {
		if( id > thread_seq || id < 1) {
			throw new IllegalArgumentException("No thread has been created below id 0 or above id " + Integer.toString(thread_seq));
		}else{
			return !closedThreads.contains(id);
		}
	}
}
