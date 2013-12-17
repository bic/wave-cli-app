package com.maivic.android.app.test;

import java.util.concurrent.ExecutionException;

import net.maivic.comm.Transport;
import net.maivic.comm.TransportManager;
import net.maivic.comm.RPC.LocationQuery;
import net.maivic.comm.impl.RPCHandlerManager;
import net.maivic.context.Context;
import net.maivic.protocol.Model.Location;
import junit.framework.TestCase;

public class ServerCommunicationTest extends TestCase {

	public void testTransport() {
		TransportManager m = Context.get().getTransportManager();
		try {
			Transport transport = m.addTransport("nettytcp://81.181.146.250");
			transport.pullUp();

		} catch (Throwable t) {
			t.printStackTrace();
		}
		LocationQuery locationQuery = RPCHandlerManager
				.getHandler(LocationQuery.class);
		Location location;
		try {
			location = locationQuery.getLocation(1.0, 2.0, 150).get();
			System.out.println(location);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
