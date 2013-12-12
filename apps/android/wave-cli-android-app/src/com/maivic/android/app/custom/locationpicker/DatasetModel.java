package com.maivic.android.app.custom.locationpicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class DatasetModel extends Observable {
	Dataset dataset;

	public DatasetModel(){
		dataset = createTestDataset();
	}
	
	public Dataset getDataset() {
		return dataset;
	}

	public void setDataset(Dataset newDataset) {
		this.dataset = newDataset;
		
		setChanged();
		notifyObservers(newDataset);
	}
	
	private Dataset createTestDataset() {
		Dataset test = new Dataset();
		test.complete = false;
		test.fixed_location_bits_count = 5;
		test.location_bits_content_by_picker_level = new ArrayList<List<LocationBitDataset>>();

		List<LocationBitDataset> datasetList = new ArrayList<LocationBitDataset>();
		// level 1
		datasetList.add(new LocationBitDataset("Romania"));
		datasetList.add(new LocationBitDataset("Bucharest"));
		datasetList.add(new LocationBitDataset("Dacia"));
		datasetList.add(new LocationBitDataset("Addis Ababa"));
		datasetList.add(new LocationBitDataset("Alofi"));
		datasetList.add(new LocationBitDataset("Andorra la Vella"));
		datasetList.add(new LocationBitDataset("Apia1"));
		datasetList.add(new LocationBitDataset("Apia"));
		datasetList.add(new LocationBitDataset("Zona industriala Pipera"));
		test.location_bits_content_by_picker_level.add(datasetList);

		// level 2
		datasetList = new ArrayList<LocationBitDataset>();		
		datasetList.add(new LocationBitDataset("Chisinew"));
		datasetList.add(new LocationBitDataset("Hinceht"));
		datasetList.add(new LocationBitDataset("Leva"));
		datasetList.add(new LocationBitDataset("Lenin"));
		datasetList.add(new LocationBitDataset("Putin"));
//		datasetList.add(new LocationBitDataset("Obama"));
		test.location_bits_content_by_picker_level.add(datasetList);		
		return test;
	}
}
