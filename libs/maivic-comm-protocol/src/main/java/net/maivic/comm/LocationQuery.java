package net.maivic.comm;

import java.util.List;

import net.maivic.comm.impl.RPCInterface;
import net.maivic.protocol.Model.Location;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.Restaurant;

@RPCInterface
public interface LocationQuery {
	/**
	 * Returns the Location Reference for the current Position as specified in the Arguments
	 * @param lon Longitude
	 * @param lat Latitude
	 * @param accuracy_in_meters
	 * @return the Location object, or null in case the position is not at a currently offered location.
	 */
	LazyResponse<Location> getLocation( double lon, double lat, double accuracy_in_meters);
	/**
	 * Returns the currently active orders for the given Location
	 * 
	 * The Method is offered in RPC - Style until relation filters are implemented for the model
	 * @param location The Location for which to return the offers
	 * @return
	 */
	LazyResponse<List<Offer> > getCurrentActiveOffers(Location location);
	
	
	
	
	
}
