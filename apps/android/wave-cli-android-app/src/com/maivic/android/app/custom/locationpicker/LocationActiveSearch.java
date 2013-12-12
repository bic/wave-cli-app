package com.maivic.android.app.custom.locationpicker;

import java.util.List;

import android.content.Context;
import android.widget.EditText;

public class LocationActiveSearch extends EditText{
	public LocationActiveSearch(Context context) {
		super(context);
	}

	// default serch interval in mls
	private static final int DEFAULT_SEARCH_DELAY = 500;
	
	// A list of the location pickers which get input from this LocationActiveSearch
	private List<LocationPicker> locationPickers;
	
	// Delay in milliseconds to send a query after the search_text has changed
	private int searchDelay = DEFAULT_SEARCH_DELAY;
	
	/*
	 * Search text as input from the user. This can differ from
	 * current_dataset_search_text which will be set delayed, upon receiving a
	 * search response from the server
	 */
	private String searchText;
	
	// Current search text for which the dataset is available
	private String currentDatasetSearchText;
	
	// List of  datasets for each possible choice corresponding to formatted_search_result
	private List<Dataset> currentDataset;
	
	// List of entries to be offered as choice for the user to pick.
	private List<FormattedString> searchResults; 
	
	// Location picker to set the Dataset for a picked result
	private LocationPicker location_picker;
	
}
