package com.maivic.restaurant.data;

import java.util.List;


import android.app.Activity;

import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.RPC.LocationQuery;
import net.maivic.comm.impl.RPCHandlerManager;
import net.maivic.protocol.Model.LocationBit;
import net.maivic.protocol.Model.Offer;

public class OfferContent extends QueryRegistry<Offer> {
	private LocationQuery locationQuery;
	private NotificationGenerator not;

	public OfferContent(Activity a) {
		super(a);
	}
	

	public void getOffersAsync() {
		this.locationQuery = RPCHandlerManager.getHandler(LocationQuery.class);

		locationQuery.getLocation(0, 1, 2).addSuccessCallback(
				new Callback<LazyResponse<LocationBit>>() {

					@Override
					public void call(LazyResponse<LocationBit> arg0) {
						
						LocationBit result = arg0.get_no_throw();
						LazyResponse<List<Offer>> myOfferHandler = locationQuery
								.getCurrentActiveOffers(result);
						myOfferHandler
								.addSuccessCallback(new Callback<LazyResponse<List<Offer>>>() {

									@Override
									public void call(
											LazyResponse<List<Offer>> arg0) {
										
										List<Offer> result = arg0.get_no_throw();
										setreceivedValues(result);

										
									}

									
								});
					/*	myOfferHandler
							.addFailureCallback(new Callback<LazyResponse<List<Offer>>>() {
								this.not = new NotificationMessageHandler();
								DisplayNotification();
							});*/ //
						

					}
				});
		// return receivedOffer;
	}
	
	@Override
	public void setreceivedValues(List<Offer> newValues) {
		
		super.setreceivedValues(newValues);
	}

	

	@Override
	public long getValueId(Offer value) {
		return value.getId();
	}

}
