package net.maivic.comm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.maivic.protobuf.Message.MaivicMessage;

public class DummyTransportManager implements TransportManager{
	Transport transport=new DummyTransport();
	
	public List<Transport> getTransports() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public boolean removeTransport(Transport t) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public Transport getTransportByUri(String URI) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	public Transport addTransport(String URI) {
		throw new UnsupportedOperationException();
	}

	public void connectTransportsOfType(String type) {
		if(type.equals("dummy")) {
			this.transport.pullUp();
		}
		
	}

	public Map<String, List<Transport>> getTransportsByType() {
		HashMap<String, List<Transport>> ret = new HashMap<String, List<Transport>>();
		ArrayList<Transport> list = new ArrayList<Transport>();
		list.add(this.transport);
		ret.put("dummy", list);
		return ret;
	}

	
	public void disconnectAll(int graceful_wait) {
		this.transport.tearDown(1000);
		
	}

	public LazyResponse<?> RPC(MaivicMessage message) {
		// TODO Auto-generated method stub
		return null;
	}

	public LazyResponse<?> streamMessages(Iterator<MaivicMessage> messageIter) {
		// TODO Auto-generated method stub
		return null;
	}

	public void notify(MaivicMessage mess) {
		// TODO Auto-generated method stub
		
	}

	


}
