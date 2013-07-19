package net.maivic.comm.testdata;


import java.util.HashMap;
import java.util.Map;

import net.maivic.protobuf.Maivic.Address;
import net.maivic.protobuf.Maivic.Location;
import net.maivic.protobuf.Maivic.VersionedID;

public class LocalTestDataGenerator {
	Map<VersionedID, Address> addresses_by_version_id = new HashMap<VersionedID, Address>();
	public LocalTestDataGenerator() {
		for (Address.Builder a: addresses){
			addresses_by_version_id.put(a.getId(), a.build());
		}
	}
	public  Address getAddress(VersionedID ver) {
		return addresses_by_version_id.get(ver);
				
	}
	
	private static VersionedID.Builder id(long id, long ver){
		return VersionedID.newBuilder().setId(id).setVersion(ver);
	}
	private static Address.Builder genAddress(VersionedID.Builder ver, String city, String street, String nr, String zip, String country, String county, double lat, double lon) {
		return Address.newBuilder()
				.setId(ver)
				.setCity(city)
				.setStreet(street)
				.setNumber(nr)
				.setZipcode(zip)
				.setCountry(country)
				.setCounty(county)
				.setLocation(Location.newBuilder()
								.setLat(lat)
								.setLon(lon));	

	}
	private static final Address.Builder[] addresses = {
		genAddress(id(0,0),"$Bucharest$","Calea Serban Voda","66","102123","$Romania$","$Bucharest$",44.406368,26.095635),
		genAddress(id(1,0),"$Bucharest$","Piata Charles de Gaulle","5A","101123","$Romania$","$Bucharest$",44.406368,26.095635),
		genAddress(id(2,0),"$Bucharest$","Piata Charles de Gaulle","1","101123","$Romania$","$Bucharest$",44.406368,26.095635),
		genAddress(id(3,0),"$Bucharest$","Alea Alexandru","9","101123","$Romania$","$Bucharest$",44.406368,26.095635),
		genAddress(id(4,0),"$Bucharest$","Popa Savu","75","101123","$Romania$","$Bucharest$",44.406368,26.095635),
		
	};
	
}
