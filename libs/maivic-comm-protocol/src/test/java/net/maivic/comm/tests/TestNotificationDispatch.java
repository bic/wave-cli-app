package net.maivic.comm.tests;

import net.maivic.comm.CallBackDispatcherThread;
import net.maivic.comm.TransportManager;
import net.maivic.context.Context;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNotificationDispatch {
	Context factory;
	TransportManager transportManager;
	private CallBackDispatcherThread cb_thread;
	@Before 
	public void setUp(){
		
	}
	
	@After
	public void tearDown() {
		this.cb_thread.quit();
	}
	@Test
	public void test() {

	
	}

}
