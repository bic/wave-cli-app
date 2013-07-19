package net.maivic.comm;

public class AbstractTransport implements Transport {

	public String getName() {
		throw new UnsupportedOperationException();
	}

	public String getTransportType() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void tearDown(int millis) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void pullUp() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public boolean isConnected() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void cbBeforeDisconnect(Callback<Transport> cb) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public void cbAfterConnect(Callback<Transport> cb) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public TopicFilter getAcceptFilter() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

}
