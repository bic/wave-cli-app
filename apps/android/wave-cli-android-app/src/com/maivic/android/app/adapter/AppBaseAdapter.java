package com.maivic.android.app.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class AppBaseAdapter<T> extends BaseAdapter{
	
	protected List<T> mData;
	protected Context mContext;
	private LayoutInflater mLayoutInflater;

	
	public LayoutInflater getLayoutInflater(){
		return mLayoutInflater;
	}
	
	public AppBaseAdapter(Context context, List<T> data) {
		mLayoutInflater = LayoutInflater.from(context);
		mData = data;
	}

	@Override
	public int getCount() {
		return mData.size();
	}

	@Override
	public Object getItem(int position) {
		return mData.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 
	
}
