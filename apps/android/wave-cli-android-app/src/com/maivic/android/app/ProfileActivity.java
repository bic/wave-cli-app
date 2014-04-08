package com.maivic.android.app;

import android.os.Bundle;
import android.view.View;

import com.maivic.android.app.custom.locationpicker.DatasetModel;
import com.maivic.android.app.custom.locationpicker.LocationPicker;

public class ProfileActivity extends BaseActivity {

	LocationPicker locationPicker1;
	LocationPicker locationPicker2;
	
	DatasetModel datasetModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		locationPicker1 = (LocationPicker) findViewById(R.id.locationPicker1);
		locationPicker2 = (LocationPicker) findViewById(R.id.locationPicker2);
		
		datasetModel = new DatasetModel();
		
		locationPicker1.setDatasetModel(datasetModel);
		locationPicker2.setDatasetModel(datasetModel);
	}

	@Override
	protected View getContentView() {
		return getLayoutInflater().inflate(R.layout.activity_profile, null);
	}
}
