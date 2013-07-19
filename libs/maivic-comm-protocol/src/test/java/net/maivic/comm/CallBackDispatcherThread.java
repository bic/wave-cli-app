package net.maivic.comm;


import java.util.concurrent.ConcurrentSkipListMap;

public class CallBackDispatcherThread extends Thread {
	ConcurrentSkipListMap<Long, Object[]> callbackSchedule = new ConcurrentSkipListMap<Long, Object[]>(); 
	private boolean quit=false;
	@SuppressWarnings("unchecked")
	@Override
	public void run() {
		while(!quit) {
			long waitTime = 1000;
			if(!this.callbackSchedule.isEmpty()){
				for(ConcurrentSkipListMap.Entry<Long, Object[]>e: callbackSchedule.entrySet()) {
					long deadline = System.currentTimeMillis();
					if( deadline >= e.getKey()) {
						Callback.class.cast((e.getValue()[0]))
						.call(e.getValue()[1]);
					} else {
						waitTime=e.getKey()-deadline;
						break;
					}
				}
				
			}
			try {
				if(!quit)
					synchronized(this){
						this.wait(waitTime);
					}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	public  void quit() {
		this.quit=true;
		this.notify();
	}
	public void insertCb( Callback<?> cb,Object argument, long time){
		this.callbackSchedule.put(time, new Object[]{cb,argument});
		if (callbackSchedule.firstKey() == time){
			synchronized (this) {
				this.notify();
			}
			
		}
	}
	
}
