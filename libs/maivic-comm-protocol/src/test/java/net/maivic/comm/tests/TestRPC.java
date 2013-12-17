package net.maivic.comm.tests;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.PlatformSupport;
import net.maivic.comm.Transport;
import net.maivic.comm.TransportManager;
import net.maivic.comm.RPC.LocationQuery;
import net.maivic.comm.RPC.OfferQuery;
import net.maivic.comm.impl.RPCHandlerManager;
import net.maivic.context.Context;
import net.maivic.context.UnsupportedType;
import net.maivic.context.WrongType;
import net.maivic.protocol.Model.Decimal;
import net.maivic.protocol.Model.Location;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.protocol.relations.OfferRelations;

import org.junit.Test;

public class TestRPC {
		public static class TestPlatformSupport implements PlatformSupport {
			private static byte[] uuid = new byte[] {0,0,0,0,0,0,0,0,0,0,0,0,0};
			public byte[] getUUID() {
				return uuid;
			}
			
		}
	@Test
	public void test() {
		try {
			Context.get().register("PlatformSupport", new TestPlatformSupport());
		} catch (WrongType e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (UnsupportedType e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		TransportManager  m = Context.get().getTransportManager();
		try {
			Transport transport =m.addTransport("nettytcp://81.181.146.250");
			transport.pullUp();
		} catch(Throwable t) {
			t.printStackTrace();
		}
		LocationQuery locationQuery = RPCHandlerManager.getHandler(LocationQuery.class);
		
		Location location;
		OfferRelations offerRelations= RPCHandlerManager.getHandler(OfferRelations.class);
		try {
			location = locationQuery.getLocation(1.0, 2.0, 150).get();
			List<Offer> offers = locationQuery.getCurrentActiveOffers(location).get();
			
			for (Offer offer: offers){
				System.out.print("OFFER :");
				System.out.println(offer);
				LazyResponse<List<OfferOption>> offerOptions= offerRelations.getOfferOptions(offer,null);
				offerOptions.addSuccessCallback(new Callback<LazyResponse<List<OfferOption>>>() {
					
					public void call(LazyResponse<List<OfferOption>> result) {
//						List<OfferOption> options =  result.get();
//						Decimal price = options.get(0).getPrice();
//						double priceFinal = price.getValue() * Math.pow(10, price.getScale());
						
//						BigDecimal ddd = new BigDecimal(new BigInteger(String.valueOf(price.getValue()), price.getScale());
					}
				});
				
				
//				for(OfferOption offerOption: offerOptions ){
//					System.out.print("OFFER_OPTIONS(OFFER.id=" + offer.getId() + ")");
//					System.out.println(offerOption);
//				}
//				
//				offerOptions.get(1);
				
			}
			
			this.printLocation(location);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	private void printLocation(Location location){
		this.printLocation(location, "TEST:");
	}
	private void printLocation(Location location, String prefix) {
		if(location == null) {
			System.out.print(prefix);
			System.out.println("Location is null!!");
			return;
		}
		System.out.print(prefix);
		System.out.println("Location Object: " + location.toString());

		System.out.print(prefix);
		System.out.println("Location id: " + location.getId());

		System.out.print(prefix);
		System.out.println("Location short_name: " + location.getNameShort());

		System.out.print(prefix);
		System.out.println("Location long_name: " + location.getNameLong());
		
	
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
		public void fetchRestaurant() {
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
				Offer offer= offers.get(0); //pick first for example
				OfferQuery offer_q= RPCHandlerManager.getHandler(OfferQuery.class);			
				LazyResponse<Restaurant>	restaurant = offer_q.getRestaurant(offer);
				
				//Note that the next line might block
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
