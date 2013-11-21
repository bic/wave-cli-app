package com.maivic.android.app.custom.locatoinselector.model;

import java.util.List;

public interface Item<T> {
	public T getData();
	public Item<T> getParent();
	public String getTextValue();
	public boolean isFixed();
	public boolean isParent();
	public boolean isLast();
	public boolean hasChilds();
	public List<Item<T>> getChilds();
}
