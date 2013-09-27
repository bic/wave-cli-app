package net.maivic.comm.tests;

import net.maivic.comm.CallBackDispatcherThread;
import net.maivic.comm.Factory;
import net.maivic.comm.TransportManager;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestNotificationDispatch {
	Factory factory;
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
