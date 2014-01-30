package net.maivic.comm.tests;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import junit.framework.TestCase;

import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.PlatformSupport;
import net.maivic.comm.TestConfig;
import net.maivic.comm.Transport;
import net.maivic.comm.TransportManager;
import net.maivic.comm.RPC.LocationQuery;
import net.maivic.comm.RPC.OfferQuery;
import net.maivic.comm.impl.RPCHandlerManager;
import net.maivic.context.Context;
import net.maivic.context.UnsupportedType;
import net.maivic.context.WrongType;
import net.maivic.protocol.Model.Decimal;
import net.maivic.protocol.Model.LocationBit;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.protocol.relations.LocationRelations;
import net.maivic.protocol.relations.MenuRelations;
import net.maivic.protocol.relations.OfferRelations;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestRPC extends TestCase {
	public static class TestPlatformSupport implements PlatformSupport {

		private static byte[] uuid = new byte[] { 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,
				10, 11, 12 };

		public byte[] getUUID() {
			return uuid;
		}
		

	}

	private String url = 
			//"nettytcp://api.wavetheapp.com";
			"nettytcp://api.wavetheapp.com";
	private Transport transport;
	@Before
	public void setUp() {
		try {
			Context c = Context.get();
			if (c.get("PlatformSupport") == null) {
				
					c.register("PlatformSupport", new TestPlatformSupport());
			}
			} catch (WrongType e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedType e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		TransportManager m = Context.get().getTransportManager();
		try {
			this.transport = m.addTransport(this.url);
			transport.pullUp();

		} catch (Throwable t) {
			t.printStackTrace();
		}

	}
	@After 
	public void tearDown() {
		this.transport.tearDown(500);
	}
	@Test
	public void testGetRestaurants() {
		LocationQuery locationQuery = RPCHandlerManager
				.getHandler(LocationQuery.class);
		LocationBit location;
		LocationRelations locRels = RPCHandlerManager
				.getHandler(LocationRelations.class);
		OfferRelations offerRels = RPCHandlerManager
				.getHandler(OfferRelations.class);
		final MenuRelations menuRels = RPCHandlerManager
				.getHandler(MenuRelations.class);
		try {
			location = locationQuery.getLocation(1.0, 2.0, 150).get();
			System.out.println(location);
			LazyResponse<List<Offer>> offers = locationQuery.getCurrentActiveOffers(location);
					//.getOffers(location, null);
			List<List<OfferOption>> res = new ArrayList<List<OfferOption>>();
			final List<LazyResponse<Restaurant>> restaurants = new ArrayList<LazyResponse<Restaurant>>();
			List<LazyResponse<List<OfferOption>>> responses = new ArrayList<LazyResponse<List<OfferOption>>>();
			
			for (Offer o : offers.get()) {
				System.out.println(offerRels.getOfferCatalogs(o, null).get());
							
				responses.add(offerRels.getOfferOptions(o, null));
				System.out.println(o);
			}

			for (LazyResponse<List<OfferOption>> response : responses) {
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

	@Test
	public void test() {
		
		TransportManager m = Context.get().getTransportManager();
		try {
			Transport transport = m.addTransport(this.url );
			transport.pullUp();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		LocationQuery locationQuery = RPCHandlerManager
				.getHandler(LocationQuery.class);

		LocationBit location;
		OfferRelations offerRelations = RPCHandlerManager
				.getHandler(OfferRelations.class);
		try {
			location = locationQuery.getLocation(1.0, 2.0, 150).get();
			List<Offer> offers = locationQuery.getCurrentActiveOffers(location)
					.get();

			for (Offer offer : offers) {
				System.out.print("OFFER :");
				System.out.println(offer);
				LazyResponse<List<OfferOption>> offerOptions = offerRelations
						.getOfferOptions(offer, null);
				offerOptions
						.addSuccessCallback(new Callback<LazyResponse<List<OfferOption>>>() {

							public void call(
									LazyResponse<List<OfferOption>> result) {
								List<OfferOption> options = result.get_no_throw();
								for (OfferOption o: options){
									System.out.println(o);
								}
								
								// Decimal price = options.get(0).getPrice();
								// double priceFinal = price.getValue() *
								// Math.pow(10, price.getScale());

								// BigDecimal ddd = new BigDecimal(new
								// BigInteger(String.valueOf(price.getValue()),
								// price.getScale());
							}
						});

				// for(OfferOption offerOption: offerOptions ){
				// System.out.print("OFFER_OPTIONS(OFFER.id=" + offer.getId() +
				// ")");
				// System.out.println(offerOption);
				// }
				//
				// offerOptions.get(1);

			}

			System.out.println(location.toString());
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void printLocation(LocationBit location) {
		this.printLocation(location, "TEST:");
	}

	private void printLocation(LocationBit location, String prefix) {
		if (location == null) {
			System.out.print(prefix);
			System.out.println("Location is null!!");
			return;
		}
		System.out.print(prefix);
		System.out.println("Location Object: " + location.toString());

		System.out.print(prefix);
		System.out.println("Location id: " + location.getId());

		System.out.print(prefix);
		System.out.println("Location short_name: " + location.getName());

		System.out.print(prefix);

	}

	public void testOfferGet() {
		TransportManager m = Context.get().getTransportManager();
		try {
			Transport transport = m.addTransport(this.url);
			transport.pullUp();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		LocationQuery locationQuery = RPCHandlerManager
				.getHandler(LocationQuery.class);

		LocationBit location;
		try {
			location = locationQuery.getLocation(1.0, 2.0, 150).get();
			List<Offer> offers = locationQuery.getCurrentActiveOffers(location).get() ;
			OfferQuery offer_q = RPCHandlerManager.getHandler(OfferQuery.class);
			offer_q.getOfferOptions(offers.get(0));
			
					/**, new Callback<List<Offer>> () {

				@Override
				public void call(List<Offer> result) {
					System.out.println("UPDATE for getCurrentActiveOffers received:");
					System.out.println(result);
				}
				
			})
					.get();
					*/
			List<LazyResponse<Restaurant>> restaurants = new ArrayList<LazyResponse<Restaurant>>();
			for (Offer offer : offers) {
				// Launch the Queries
				restaurants.add(offer_q.getRestaurant(offer));
			}

			for (LazyResponse<Restaurant> restaurant : restaurants) {
				// Do something with the results
				// Note that the restaurant.get() call will block until a
				// response arrives from the Network
				System.out.println(restaurant.get());
				// An alternative which does not block inndefinitely would be:
				try {
					System.out.println(restaurant
							.get(10, TimeUnit.MILLISECONDS));
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

	public void fetchRestaurant() {
		TransportManager m = Context.get().getTransportManager();
		try {
			Transport transport = m.addTransport(this.url);
			transport.pullUp();
		} catch (Throwable t) {
			t.printStackTrace();
		}
		LocationQuery locationQuery = RPCHandlerManager
				.getHandler(LocationQuery.class);

		LocationBit location;
		try {
			location = locationQuery.getLocation(1.0, 2.0, 150).get();
			List<Offer> offers = locationQuery.getCurrentActiveOffers(location)
					.get();
			Offer offer = offers.get(0); // pick first for example
			OfferQuery offer_q = RPCHandlerManager.getHandler(OfferQuery.class);
			LazyResponse<Restaurant> restaurant = offer_q.getRestaurant(offer);

			// Note that the next line might block
			System.out.println(restaurant.get());

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}