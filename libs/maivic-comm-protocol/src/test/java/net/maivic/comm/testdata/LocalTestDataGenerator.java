package net.maivic.comm.testdata;


import java.util.ArrayList;
import java.util.List;

import net.maivic.protocol.Model.Address;


public class LocalTestDataGenerator {
	List<Address> addresses_by_version_id = new ArrayList< Address>();
	public LocalTestDataGenerator() {
		for (Address.Builder a: addresses){
			addresses_by_version_id.add( a.build());
		}
	}
	public  Address getAddress() {
		return addresses_by_version_id.get(addresses_by_version_id.size()-1);
				
	}
	
	private static Address.Builder genAddress(int id,String city, String street, String nr, String zip, String country, String county, double lat, double lon) {
		return Address.newBuilder()
				.setId(id)
				.setCity(city)
				.setStreet(street)
				.setNumber(nr)
				.setCountry(country)
				.setCounty(county)
								;	

	}
	private static final Address.Builder[] addresses = {
		genAddress(0,"$Bucharest$","Calea Serban Voda","66","102123","$Romania$","$Bucharest$",44.406368,26.095635),
		genAddress(1,"$Bucharest$","Piata Charles de Gaulle","5A","101123","$Romania$","$Bucharest$",44.406368,26.095635),
		genAddress(2,"$Bucharest$","Piata Charles de Gaulle","1","101123","$Romania$","$Bucharest$",44.406368,26.095635),
		genAddress(3,"$Bucharest$","Alea Alexandru","9","101123","$Romania$","$Bucharest$",44.406368,26.095635),
		genAddress(4,"$Bucharest$","Popa Savu","75","101123","$Romania$","$Bucharest$",44.406368,26.095635),
		
	};
	
}
