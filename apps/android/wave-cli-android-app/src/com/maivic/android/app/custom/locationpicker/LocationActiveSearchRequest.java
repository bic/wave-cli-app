package com.maivic.android.app.custom.locationpicker;

import android.location.Location;

public class LocationActiveSearchRequest {
	public static final int DEFAULT_MAX_RESULT = 100;
	
	String user_input;
	
	Dataset  current_location_picker_data_set;
	
	Location current_location;
	
	// Maximum number of results
	int max_results = DEFAULT_MAX_RESULT;
	
	public LocationActiveSearchRequest(String user_input,
			Dataset current_location_picker_data_set,
			Location current_location, int max_results) {
		super();
		this.user_input = user_input;
		this.current_location_picker_data_set = current_location_picker_data_set;
		this.current_location = current_location;
		this.max_results = max_results;
	}

	public LocationActiveSearchRequest(String user_input,
			Dataset current_location_picker_data_set, Location current_location) {
		super();
		this.user_input = user_input;
		this.current_location_picker_data_set = current_location_picker_data_set;
		this.current_location = current_location;
		this.max_results = DEFAULT_MAX_RESULT;
	}  
}
