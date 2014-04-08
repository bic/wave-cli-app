package com.maivic.android.app.api;

import java.util.List;
import java.util.concurrent.ExecutionException;

import net.maivic.comm.Transport;
import net.maivic.comm.TransportManager;
import net.maivic.comm.impl.RPCHandlerManager;
import net.maivic.context.Context;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.protocol.relations.OfferRelations;

public class APIHelper {

	private static APIHelper mInstance = null;
	
	private APIHelper(){
	}
	
	private void initlizeTransport(){
		TransportManager m = Context.get().getTransportManager();
		
		try {
			Transport transport = m.addTransport("nettytcp://81.181.146.250");
			transport.pullUp();
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public static APIHelper getInstance(){
		if(mInstance == null){
			
			mInstance = new APIHelper();
			
		}
		return mInstance;
	}
	
	public List<OfferOption> getOfferOptions(Offer offer) throws InterruptedException, ExecutionException{
		initlizeTransport();
		
		OfferRelations offerRelations = RPCHandlerManager.getHandler(OfferRelations.class);
		List<OfferOption> options = null;
		options = offerRelations.getOfferOptions(offer, null).get();
		
		
		System.out.print("Options:");
		for(OfferOption option: options){
			System.out.println(option);
		}
		
		return options;
	}
	
	
}
