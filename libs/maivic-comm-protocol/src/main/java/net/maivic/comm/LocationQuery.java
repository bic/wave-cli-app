package net.maivic.comm;

import net.maivic.comm.impl.RPCInterface;
import net.maivic.protocol.Model.Location;

@RPCInterface
public interface LocationQuery {
	Location getLocation( double lon, double lat, double accuracy_in_meters);
}
