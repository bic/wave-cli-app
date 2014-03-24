package com.maivic.restaurant.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.app.Activity;
import android.content.Context;

import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.RPC.LocationQuery;
import net.maivic.comm.impl.RPCHandlerManager;
import net.maivic.protocol.Model.LocationBit;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.protocol.relations.OfferRelations;

public class OfferOptionsContent extends QueryRegistry<OfferOption>{
	public OfferOptionsContent(Activity a) {
		super(a);
	}
	int id_seq=0;
	

	Map<Integer,UpdateDataCallback<OfferOption>> optionsListener = new HashMap<Integer,UpdateDataCallback<OfferOption>>();


	private LocationQuery locationQuery;


	private OfferRelations offerRelations ;	

	private OfferRelations getOfferRelations() {
		if(offerRelations == null) offerRelations = RPCHandlerManager.getHandler(OfferRelations.class);
		return offerRelations;
	}



	public void getOfferOption(Offer o){
		
		LazyResponse<List<OfferOption>> offerOptionsHandler = getOfferRelations()
				.getOfferOptions(o, null);

		offerOptionsHandler
				.addSuccessCallback(new Callback<LazyResponse<List<OfferOption>>>() {

					@Override
					public void call(
							LazyResponse<List<OfferOption>> arg0) {
						List<OfferOption> result = arg0.get_no_throw();
						setreceivedValues(result);

					}
				});
	}
	


	@Override
	public long getValueId(OfferOption value) {
		return value.getId();
	}

}
