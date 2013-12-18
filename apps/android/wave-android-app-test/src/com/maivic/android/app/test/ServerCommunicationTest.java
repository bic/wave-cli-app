package com.maivic.android.app.test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import com.maivic.android.app.api.AndroidPlatformSupport;

import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Transport;
import net.maivic.comm.TransportManager;
import net.maivic.comm.RPC.LocationQuery;
import net.maivic.comm.impl.RPCHandlerManager;
import net.maivic.context.Context;
import net.maivic.context.UnsupportedType;
import net.maivic.context.WrongType;
import net.maivic.protocol.Model.Location;
import net.maivic.protocol.Model.Menu;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.protocol.relations.LocationRelations;
import net.maivic.protocol.relations.MenuRelations;
import net.maivic.protocol.relations.OfferRelations;
import junit.framework.TestCase;

public class ServerCommunicationTest extends TestCase {
	
	public void setUp() {
		try {
			Context.get().register("PlatformSupport", new AndroidPlatformSupport());
		} catch (WrongType e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedType e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
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
		LocationRelations locRels = RPCHandlerManager.getHandler(LocationRelations.class);
		OfferRelations offerRels = RPCHandlerManager.getHandler(OfferRelations.class);
		final MenuRelations menuRels=RPCHandlerManager.getHandler(MenuRelations.class);
		try {
			location = locationQuery.getLocation(1.0, 2.0, 150).get();
			System.out.println(location);
			LazyResponse<List<Offer>> offers = locRels.getOffers(location, null);
			List<List<OfferOption>> res= new ArrayList<List<OfferOption>>();
			final List<LazyResponse<Restaurant>> restaurants = new ArrayList<LazyResponse<Restaurant>>();
			List<LazyResponse<List<OfferOption>>> responses = new ArrayList<LazyResponse<List<OfferOption>>>();
			for (Offer o : offers.get()) {
				offerRels.getMenus(o, null).addSuccessCallback(new Callback<LazyResponse<List<Menu>>>() {
					
					@Override
					public void call(LazyResponse<List<Menu>> result) {
						List<Menu> ret= result.get();
						if(ret == null || ret.size() <1){
							//error
						} else {
							restaurants.add(menuRels.getRestaurant(ret.get(0),null));
						}
					}
				});
				responses.add(offerRels.getOfferOptions(o, null));
				System.out.println(o);
			}
			for (LazyResponse<List<OfferOption>> response : responses){
				System.out.println(response.get());
			}
			
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
