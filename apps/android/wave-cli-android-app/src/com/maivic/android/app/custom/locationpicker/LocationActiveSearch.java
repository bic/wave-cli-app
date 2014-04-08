package com.maivic.android.app.custom.locationpicker;

import java.util.List;

import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

public class LocationActiveSearch extends AutoCompleteTextView{
	public LocationActiveSearch(Context context) {
		super(context);
		initizlizeInternal();
	}

	public LocationActiveSearch(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initizlizeInternal();
	}

	public LocationActiveSearch(Context context, AttributeSet attrs) {
		super(context, attrs);
		initizlizeInternal();
	}
	
	private void initizlizeInternal(){
		addTextChangedListener(textWatcher);
	}

	// default serch interval in mls
	private static final long DEFAULT_SEARCH_DELAY = 1000L;
	
	// A list of the location pickers which get input from this LocationActiveSearch
	private List<LocationPicker> locationPickers;
	
	// Delay in milliseconds to send a query after the search_text has changed
	private long searchDelay = DEFAULT_SEARCH_DELAY;
	
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
	
	private long lastTextUpdateTimestamp;
	
	private SearchRequestTask requestTask;
	
	private void doServerRequestAction(String userInput){
		if(requestTask != null && (AsyncTask.Status.RUNNING == requestTask.getStatus() )){
			requestTask.cancel(true);
		}

		DatasetModel datasetModel = new DatasetModel();
		LocationActiveSearchRequest request = new LocationActiveSearchRequest(
				userInput, datasetModel.getDataset(), null);
		
		// TODO server request
		LocationSearchUpdate searchUpdate = new LocationSearchUpdate();
		requestTask = new SearchRequestTask();
		requestTask.execute(request);
	}
	
	private TextWatcher textWatcher = new TextWatcher() {
		
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			Toast.makeText(getContext(), "searchQueryL " + s, Toast.LENGTH_SHORT).show();
			
			if (lastTextUpdateTimestamp != 0
					&& (System.currentTimeMillis() - lastTextUpdateTimestamp > DEFAULT_SEARCH_DELAY)) {
				
				doServerRequestAction(s.toString());
			}
			
			lastTextUpdateTimestamp = System.currentTimeMillis();
		}
		
		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			
		}
		
		@Override
		public void afterTextChanged(Editable s) {
		}
	};
	
	class SearchRequestTask extends AsyncTask<LocationActiveSearchRequest, Void, LocationSearchUpdate>{

		@Override
		protected LocationSearchUpdate doInBackground(
				LocationActiveSearchRequest... params) {
			
			return null;
		}
		
	}
	
	private class ActiveSearchAdapter extends ArrayAdapter<ResultSection>{
		
		public ActiveSearchAdapter(Context context, List<ResultSection> data){
			super(context, android.R.layout.simple_list_item_1, data);
		}
	}
}
