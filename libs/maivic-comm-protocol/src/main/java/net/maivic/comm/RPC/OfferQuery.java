package net.maivic.comm.RPC;

import java.util.List;

import net.maivic.comm.LazyResponse;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.protocol.Model.Restaurant;

public interface OfferQuery {
	/**
	 * Returns the restaurant that made the offer
	 * @param offer the offer
	 * @return the restaurant which made the offer
	 */
	LazyResponse<Restaurant> getRestaurant( Offer offer);
	/**
	 * Returns the options for the given offer
	 * @param offer
	 * @return List of Offer options
	 */
	LazyResponse<List<OfferOption>> getOfferOptions(Offer offer);
	/**
	 * cre
	 */

}
