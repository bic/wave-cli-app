package com.maivic.android.app;

import java.util.List;
import java.util.concurrent.ExecutionException;

import net.maivic.comm.LazyResponse;
import net.maivic.comm.Transport;
import net.maivic.comm.TransportManager;
import net.maivic.comm.RPC.LocationQuery;
import net.maivic.comm.impl.RPCHandlerManager;
import net.maivic.context.Context;
import net.maivic.protocol.Model.Location;
import net.maivic.protocol.Model.Offer;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.maivic.android.app.adapter.OffersAdapter;

/**
 * Activity with list of offers
 * 
 * @author Serghei
 * 
 */
public class OffersActivity extends BaseActivity {
	ListView offersListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		offersListView = (ListView) findViewById(R.id.listView);
		offersListView.setOnItemClickListener(onItemClickListener);

		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO){
			loadOffers();			
		}
	}

	private void loadOffers() {

//		LocationQuery locationQuery = RPCHandlerManager
//				.getHandler(LocationQuery.class);

//		final Location location;
		// OfferRelations offerRelations =
		// RPCHandlerManager.getHandler(OfferRelations.class);
		// try {
		// TODO my location
		// location = locationQuery.getLocation(1.0, 2.0, 150).get();
		// List<Offer> offers =
		// locationQuery.getCurrentActiveOffers(location).get();

		// final LazyResponse<List<Offer>> lazyOffers =
		// locationQuery.getCurrentActiveOffers(location);

		new AsyncTask<Void, Void, List<Offer>>() {

			@Override
			protected List<Offer> doInBackground(Void... params) {
				
				TransportManager m = Context.get().getTransportManager();

				// TODO settings
				try {
					Transport transport = m.addTransport("nettytcp://81.181.146.250");
					transport.pullUp();
				} catch (Throwable t) {
					t.printStackTrace();
				}
				
				LocationQuery locationQuery = RPCHandlerManager.getHandler(LocationQuery.class);

//				MenuRelations menuRelation = RPCHandlerManager.getHandler(MenuRelations.class);
//				OfferRelations offerRelations = RPCHandlerManager.getHandler(OfferRelations.class);
				
				List<Offer> offers = null;
				try {
					System.out.println("[API] getting location..");
					
					LazyResponse<Location> locaitonLazy = locationQuery.getLocation(1.0,2.0, 150);
					if(locaitonLazy == null){
						throw new RuntimeException("Location Query is null!!!!");
					}

					final Location location = locaitonLazy.get();//locationQuery.getLocation(1.0,2.0, 150).get();

					System.out.println("[API] lcoation done " + location);
					
					System.out.println("[API] getting offers...");
					LazyResponse<List<Offer>> lazyOffers = locationQuery.getCurrentActiveOffers(location);

					offers = lazyOffers.get();
					
					System.out.println("[API] offers done");
					
//					System.err.println("====Offers:");
//					for(Offer offer: offers){
//						
//						System.err.print("Offer:");
//						System.err.println(offer);
//						
//						List<Menu> menus = offerRelations.getMenus(offer, null).get();
//						System.err.println("===Menus");
//						for(Menu menu: menus){
//							System.err.print("Menu:");
//							System.err.println(menu);
//							System.err.print("Restaurant");
//							Restaurant rest = menuRelation.getRestaurant(menu, null).get();
//							System.err.println(rest);
//						}
//						
//						List<OfferOption> offerOptions = offerRelations.getOfferOptions(offer, null).get();
//						
//						System.err.println("===Options:");
//						for(OfferOption option: offerOptions){
//							System.err.println("Option:");
//							System.err.println(option);
//						}
//					}
					
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}

				return offers;
			}

			@Override
			protected void onPostExecute(List<Offer> result) {
				hideProgressDlg();
				offersListView.setAdapter(new OffersAdapter(OffersActivity.this, result));
			}

			@Override
			protected void onPreExecute() {
				showProgressDlg();
			}

		}.execute();

		// lazyOffers.addSuccessCallback(new
		// Callback<LazyResponse<List<Offer>>>() {
		//
		// @Override
		// public void call(LazyResponse<List<Offer>> offers) {
		// hideProgressDlg();
		//
		// if(offers.isDone()){
		// Toast.makeText(OffersActivity.this, "Offers list Done",
		// Toast.LENGTH_SHORT).show();
		//
		// List<Offer> offerList = null;
		// try {
		// offerList = offers.get();
		//
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } catch (ExecutionException e) {
		// e.printStackTrace();
		// }
		//
		// Toast.makeText(OffersActivity.this, "get " + offerList.size()
		// +" offers" , Toast.LENGTH_LONG).show();
		//
		// offersListView.setAdapter(new OffersAdapter(OffersActivity.this,
		// offerList));
		//
		// }
		// }
		// });
		//
		// lazyOffers.addFailureCallback(new
		// Callback<LazyResponse<List<Offer>>>() {
		//
		// @Override
		// public void call(LazyResponse<List<Offer>> arg0) {
		// hideProgressDlg();
		// Toast.makeText(OffersActivity.this, "Faulure",
		// Toast.LENGTH_SHORT).show();
		// }
		// });

		// lazyOffers.addSuccessCallback(new
		// Callback<LazyResponse<List<Offer>>>() {
		//
		// @Override
		// public void call(LazyResponse<List<Offer>> response) {
		//
		// Toast.makeText(OffersActivity.this, "onSuccessCallback",
		// Toast.LENGTH_SHORT).show();
		//
		// hideProgressDlg();
		//
		// List<Offer> offers = null;
		//
		// if(response.isDone()){
		// try {
		// offers = response.get();
		// offersListView.setAdapter(new OffersAdapter(OffersActivity.this,
		// offers));
		//
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// Toast.makeText(OffersActivity.this, "InterruptedException",
		// Toast.LENGTH_SHORT).show();
		//
		// } catch (ExecutionException e) {
		// Toast.makeText(OffersActivity.this, "ExecutionException",
		// Toast.LENGTH_SHORT).show();
		// e.printStackTrace();
		// }
		//
		// } else{
		// Toast.makeText(OffersActivity.this, "response not done",
		// Toast.LENGTH_SHORT).show();
		// }
		// }
		// });
		//
		// lazyOffers.addDoneCallback(new Callback<LazyResponse<List<Offer>>>()
		// {
		//
		// @Override
		// public void call(LazyResponse<List<Offer>> arg0) {
		// Toast.makeText(OffersActivity.this, "onDoneCallback call",
		// Toast.LENGTH_SHORT).show();
		// }
		// });
		//
		// lazyOffers.addFailureCallback(new
		// Callback<LazyResponse<List<Offer>>>() {
		//
		// @Override
		// public void call(LazyResponse<List<Offer>> arg0) {
		// Toast.makeText(OffersActivity.this, "onFailyreCallback call",
		// Toast.LENGTH_SHORT).show();
		// }
		// });

		// List<Offer> offerList = lazyOffers.get();
		// offersListView.setAdapter(new OffersAdapter(OffersActivity.this,
		// offerList));
		//
		// hideProgressDlg();

		// for (Offer offer: offers){
		// System.out.print("OFFER :");
		// System.out.println(offer);
		// LazyResponse<List<OfferOption>> offerOptions=
		// offerRelations.getOfferOptions(offer,null);
		//
		// offerOptions.addFailureCallback(new
		// Callback<LazyResponse<List<OfferOption>>>() {
		//
		// public void call(LazyResponse<List<OfferOption>> result) {
		//
		// }
		// });
		//
		// offerOptions.addSuccessCallback(new
		// Callback<LazyResponse<List<OfferOption>>>() {
		//
		// public void call(LazyResponse<List<OfferOption>> result) {
		// // List<OfferOption> options = result.get();
		// // Decimal price = options.get(0).getPrice();
		// // double priceFinal = price.getValue() * Math.pow(10,
		// price.getScale());
		//
		// // BigDecimal ddd = new BigDecimal(new
		// BigInteger(String.valueOf(price.getValue()), price.getScale());
		// }
		// });
		//
		//
		// // for(OfferOption offerOption: offerOptions ){
		// // System.out.print("OFFER_OPTIONS(OFFER.id=" + offer.getId() + ")");
		// // System.out.println(offerOption);
		// // }
		// //
		// // offerOptions.get(1);
		//
		// }
		// } catch (InterruptedException e) {
		// e.printStackTrace();
		// } catch (ExecutionException e) {
		// e.printStackTrace();
		// }
	}

	@Override
	protected View getContentView() {
		return mInflater.inflate(R.layout.activity_offers, null);
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(OffersActivity.this,
					CommandConfirmationActivty.class);
			startActivity(intent);
		}
	};
}
