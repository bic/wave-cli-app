package com.maivic.android.app.custom.locatoinselector.model;

import java.util.List;

public class Model<T>{
	List<Item<T>> selectedPath;
	
	public void deletePathSegment(Item<T> segmentItem){
		//TODO delete 
		
		notifyUpdate();
	}
	
	public void addData(){
		
	}
	
	public void notifyUpdate(){
		
	}
}