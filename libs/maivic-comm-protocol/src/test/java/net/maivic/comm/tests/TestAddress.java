package net.maivic.comm.tests;

import static org.junit.Assert.*;
import net.maivic.comm.Comm;
import net.maivic.comm.DummyCommImpl;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.TestConfig;
import net.maivic.comm.exception.ConcurrecyException;
import net.maivic.protobuf.Maivic.Address;
import net.maivic.protobuf.Maivic.VersionedID;

import org.junit.Test;

public class TestAddress {
	
	@Test
	public void test() {
		Comm c = new DummyCommImpl(1000);
		TestConfig.cb_thread.start();
		LazyResponse<Address> a = c.getAddress(VersionedID.newBuilder().setId(0).setVersion(0).build());
		try {
			fail(a.waitForResponse(1000).getStreet() + " time(ms): " + a.requestCycleNanosElapsed()/1000000);
		} catch (ConcurrecyException e) {
			e.printStackTrace();
			fail("ConcurrencyException: " + e.getMessage());
		}
	}

}
