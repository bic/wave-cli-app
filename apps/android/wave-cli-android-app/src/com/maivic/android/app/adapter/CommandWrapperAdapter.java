package com.maivic.android.app.adapter;

import java.util.List;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * This addapter used in confirmation list view. It sotre 3 adapters:
 * {@link CommandCourseAdapter}, {@link CommandOptionsAdapter},
 * {@link OLDCommandBillAdapter}
 * 
 * @author Serghei
 * 
 */
public class CommandWrapperAdapter extends BaseAdapter {

	private static final String T = "CommandWrapperAdapter";

	List<BaseAdapter> mAdapters;

	public CommandWrapperAdapter(List<BaseAdapter> adapters) {
		mAdapters = adapters;
	}

	@Override
	public boolean isEnabled(int position) {
		return false;
	}

	@Override
	public int getItemViewType(int position) {
		BaseAdapter adapter;		
		int viewTypes = 0;
		int adapterIndex = getAdapterIndex(position);
		for (int i = 0; i < adapterIndex; i++) {
			adapter = mAdapters.get(i);
			viewTypes += adapter.getViewTypeCount();
		}

		int positionInAdapter = getPositionInAdapter(position);
		
		adapter = mAdapters.get(adapterIndex);		
		int viewType = viewTypes
				+ adapter.getItemViewType(positionInAdapter);
		
		return viewType;
	}

	@Override
	public int getCount() {

		int size = 0;
		for (int i = 0; i < mAdapters.size(); i++)
			size += mAdapters.get(i).getCount();

		return size;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	int getAdapterIndex(int position) {
		int s = 0;
		for (int i = 0; i < mAdapters.size(); i++) {
			s += mAdapters.get(i).getCount();
			if (position < s) {
				return i;
			}
		}
		return 0;
	}

	int getPositionInAdapter(/* int adapterIndex, */int position) {

		int adapterIndex = getAdapterIndex(position);
		int s = 0;
		for (int i = 0; i < adapterIndex; i++) {
			s += mAdapters.get(i).getCount();
		}

		if (position < s) {
			return position;
		} else {
			return position - s;
		}
	}

	@Override
	public int getViewTypeCount() {
		int itemViewTypeCount = 0;
		for (int i = 0; i < mAdapters.size(); i++) {
			itemViewTypeCount += mAdapters.get(i).getViewTypeCount();
		}
		return itemViewTypeCount;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		int adapterIndex = getAdapterIndex(position);
		int positionInAdapter = getPositionInAdapter(/* adapterIndex, */position);
		BaseAdapter adapter = mAdapters.get(adapterIndex);

		return adapter.getView(positionInAdapter, convertView, parent);
	}

}