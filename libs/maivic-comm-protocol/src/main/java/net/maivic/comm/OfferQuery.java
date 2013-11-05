package net.maivic.comm;

import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.Restaurant;

public interface OfferQuery {
	/**
	 * Returns the restaurant that made the offer
	 * @param offer the offer
	 * @return the restaurant which made the offer
	 */
	LazyResponse<Restaurant> getRestaurant( Offer offer);
}
