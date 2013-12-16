package net.maivic.comm.tests;

import net.maivic.comm.NameService.NameServiceImpl;
import net.maivic.comm.service.NameService;
import net.maivic.context.Context;

import org.junit.Test;

public class TestNameService {
	NameService ns = null;
	public void  pullUp(){
		ns=Context.get().getNameService();
	}
	
	

	@Test
	public void testReadAll(){
		ns.getValue("RPC.java.names.net.maivic.comm.RPC.LocationQuery");
	}
	

}
