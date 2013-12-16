package com.maivic.android.app.custom.locationpicker;

import java.util.List;

public class Dataset {
	List<List<LocationBitDataset>> location_bits_content_by_picker_level;

	// set to true if the location definition of the last picker in dataset is
	// complete
	boolean complete;

	// if 2 than the first 2 location bits are fixed.
	int fixed_location_bits_count;

	public List<List<LocationBitDataset>> getLocation_bits_content_by_picker_level() {
		return location_bits_content_by_picker_level;
	}

	public void setLocation_bits_content_by_picker_level(
			List<List<LocationBitDataset>> location_bits_content_by_picker_level) {
		this.location_bits_content_by_picker_level = location_bits_content_by_picker_level;
	}

	public boolean isComplete() {
		return complete;
	}

	public void setComplete(boolean complete) {
		this.complete = complete;
	}

	public int getFixed_location_bits_count() {
		return fixed_location_bits_count;
	}

	public void setFixed_location_bits_count(int fixed_location_bits_count) {
		this.fixed_location_bits_count = fixed_location_bits_count;
	}

}