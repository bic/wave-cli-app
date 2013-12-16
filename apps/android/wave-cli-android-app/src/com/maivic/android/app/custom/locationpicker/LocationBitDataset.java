package com.maivic.android.app.custom.locationpicker;

public class LocationBitDataset {
	String name;
	// Location location
	int radius; // radius in meters for which this location bit is still valid

	public LocationBitDataset(String name) {
		super();
		this.name = name;
	}

	public LocationBitDataset(String name, int radius) {
		super();
		this.name = name;
		this.radius = radius;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public boolean equals(Object o) {
		return super.equals(o);
	}

	@Override
	public String toString() {
		return "LocationBitDataset [name=" + name + ", radius=" + radius + "]";
	}
}
