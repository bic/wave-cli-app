package net.maivic.comm;

import java.util.List;
import java.util.Random;

import net.maivic.comm.testdata.LocalTestDataGenerator;
import net.maivic.protobuf.Maivic.Address;
import net.maivic.protobuf.Maivic.Interval;
import net.maivic.protobuf.Maivic.Offer;
import net.maivic.protobuf.Maivic.OpenViewRequest;
import net.maivic.protobuf.Maivic.OpenViewResponse;
import net.maivic.protobuf.Maivic.UserNotification;
import net.maivic.protobuf.Maivic.UserNotification.NotificationType;
import net.maivic.protobuf.Maivic.VersionedID;

import com.google.protobuf.GeneratedMessage;

public class DummyCommImpl implements Comm {
	LocalTestDataGenerator gen= new LocalTestDataGenerator();
	Random rnd=null;
	private int maxDelay;
	public DummyCommImpl( int maxDelay) {
		this.maxDelay=maxDelay;
		if (maxDelay > 0) {
			rnd=new Random(TestConfig.randomSeed);
		}
	}
	private int nextRnd() {
		if (rnd != null) {
			return rnd.nextInt(maxDelay);
		} 
		return 0;
	}
	public void subscribeAll(Callback<GeneratedMessage> cb) {
		// TODO Auto-generated method stub

	}

	public LazyResponse<Address> getAddress(VersionedID ver) {
		return new DummyLazyResponse<Address>(gen.getAddress(ver), nextRnd());
	}

	public LazyResponse<List<Offer>> getOffers(List<VersionedID> ids) {
		// TODO Auto-generated method stub
		return null;
	}

	public LazyResponse<List<Offer>> getOffers(Interval interval) {
		// TODO Auto-generated method stub
		return null;
	}

	public LazyResponse<List<Interval>> getDeliveryIntervals(Interval interval) {
		// TODO Auto-generated method stub
		return null;
	}

	public LazyResponse<Subscription<UserNotification>> subscribeUserNotifications(
			Callback<UserNotification> cb, Enum<NotificationType> type) {
		// TODO Auto-generated method stub
		return null;
	}

	public LazyResponse<OpenViewResponse> openView(
			OpenViewRequest openViewRequest) {
		// TODO Auto-generated method stub
		return null;
	}

	public LazyResponse<Integer> sendResource(String URL) {
		// TODO Auto-generated method stub
		return null;
	}

}
