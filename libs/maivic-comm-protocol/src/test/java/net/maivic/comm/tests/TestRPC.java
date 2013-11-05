package net.maivic.comm.tests;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.maivic.comm.LazyResponse;
import net.maivic.comm.LocationQuery;
import net.maivic.comm.OfferQuery;
import net.maivic.comm.Transport;
import net.maivic.comm.TransportManager;
import net.maivic.comm.impl.RPCHandlerManager;
import net.maivic.context.Context;
import net.maivic.protocol.Model.Location;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.protocol.relations.LocationRelations;
import net.maivic.protocol.relations.OfferRelations;

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
	public void testOfferGet() {
		TransportManager  m = Context.get().getTransportManager();
		try {
			Transport transport =m.addTransport("nettytcp://localhost");
			transport.pullUp();
		} catch(Throwable t) {
			t.printStackTrace();
		}
		LocationQuery locationQuery = RPCHandlerManager.getHandler(LocationQuery.class);
		
		Location location;
		try {
			location = locationQuery.getLocation(1.0, 2.0, 150).get();
			List<Offer>  offers = locationQuery.getCurrentActiveOffers(location).get();
			OfferQuery offer_q= RPCHandlerManager.getHandler(OfferQuery.class);			
			List<LazyResponse<Restaurant>> restaurants= new ArrayList<LazyResponse<Restaurant>>();
			for (Offer offer : offers) {
				//Launch the Queries
				restaurants.add(offer_q.getRestaurant(offer));
			}
			
			for ( LazyResponse<Restaurant> restaurant : restaurants ){
				// Do something with the results
				// Note that the restaurant.get() call will block until a response arrives from the Network
				System.out.println(restaurant.get());
				// An alternative which does not block inndefinitely would be:
				try {
					System.out.println(restaurant.get(10, TimeUnit.MILLISECONDS));
				} catch (TimeoutException e) {
					// do somethin else on timeout!!
				}
				
				
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
