package com.maivic.android.app.custom.locatoinselector.model;

import java.util.List;


interface ItemsProvider<T> {
	public Item<T> getParentForItem(Item<T> item);
	public List<Item<T>> getChildsForItem(Item<T> parent);
}
