package com.maivic.android.app.custom.locatoinselector.model;

public class Node<T> {
	private T data;
	
	
	
	public boolean isFixed(){
		return false;
	}
	
	public boolean isParent(){
		return false;
	}
	
	public Node getParent(){
		return null;
	}
}
