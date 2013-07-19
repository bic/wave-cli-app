package net.maivic.comm;

public class TestFactory implements Factory {

	public TransportManager createTransportManager() {
		return new DummyTransportManager();
	}

}
