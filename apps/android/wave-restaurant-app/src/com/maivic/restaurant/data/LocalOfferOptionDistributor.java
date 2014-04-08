package com.maivic.restaurant.data;

import java.util.List;

import net.maivic.protocol.Model.OfferOption;
import android.app.Activity;

public class LocalOfferOptionDistributor extends QueryRegistry<OfferOption> {

	public LocalOfferOptionDistributor(Activity a) {
		super(a);
	}

	@Override
	public long getValueId(OfferOption value) {
		// TODO Auto-generated method stub
		return value.getId();
	}
	@Override
	public void setreceivedValue(OfferOption newValue) {
		// TODO Auto-generated method stub
		super.setreceivedValue(newValue);
	}

}
