package net.maivic.comm.tests;

import net.maivic.comm.CallBackDispatcherThread;
import net.maivic.comm.Callback;
import net.maivic.comm.Factory;
import net.maivic.comm.TestConfig;
import net.maivic.comm.TestFactory;
import net.maivic.comm.TransportManager;
import net.maivic.protobuf.Maivic.UserNotification;
import net.maivic.protobuf.Maivic.UserNotification.NotificationType;
import net.maivic.protobuf.Maivic.UserNotification.Priority;
import net.maivic.protobuf.Message.MaivicMessage;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNotificationDispatch {
	Factory factory;
	TransportManager transportManager;
	private CallBackDispatcherThread cb_thread;
	@Before 
	public void setUp(){
		factory=new TestFactory();
		this.transportManager=factory.createTransportManager();
		this.cb_thread= new CallBackDispatcherThread();
		for(int x = 0; x<10; ++x) {
			cb_thread.insertCb(new Callback<UserNotification>(){
	
				public void call(UserNotification result) {
				 //TODO: Implement!!!
					
				}
	
				public void onError(Throwable t) {
					
					
				}} , 
			
				UserNotification.newBuilder()
					.setType(NotificationType.valueOf(x%NotificationType.values().length))
					.setMessage("This is info # "+ x +" for the User").build()
				, x*1000);
		}
	}
	
	@After
	public void tearDown() {
		this.cb_thread.quit();
	}
	@Test
	public void test() {

	
	}

}
