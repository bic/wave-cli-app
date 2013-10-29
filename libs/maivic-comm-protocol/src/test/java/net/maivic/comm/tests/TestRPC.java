package net.maivic.comm.tests;

import static org.junit.Assert.*;

import net.maivic.comm.LocationQuery;
import net.maivic.comm.Transport;
import net.maivic.comm.TransportManager;
import net.maivic.comm.impl.RPCHandlerManager;
import net.maivic.context.Context;
import net.maivic.protocol.Model.Location;

import org.junit.Test;

public class TestRPC {

	@Test
	public void test() {
		TransportManager  m = Context.get().getTransportManager();
		try {
			Transport transport =m.addTransport("nettytcp://localhost");
			transport.pullUp();
		} catch(Throwable t) {
			t.printStackTrace();
		}
		LocationQuery locationQuery = RPCHandlerManager.getHandler(LocationQuery.class);
		Location location = locationQuery.getLocation(1.0, 2.0, 150);
		System.out.println(location);
	}

}
