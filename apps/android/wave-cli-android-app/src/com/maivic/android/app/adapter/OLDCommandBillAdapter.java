package com.maivic.android.app.adapter;

import com.maivic.android.app.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

/**
 * Used to show in confirmation view part of Payments button, or payment result
 * and detailed note
 * 
 * @author Serghei
 * @deprecated
 */
public class OLDCommandBillAdapter extends BaseAdapter {

	LayoutInflater mInflater;

	public OLDCommandBillAdapter(Context context) {
		mInflater = LayoutInflater.from(context);
	}
	
	public void showSuplimentView(){
		
	}
	
	public void showToPaidView(){
		
	}
	
//	public void 

	@Override
	public int getCount() {
		return 1;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.command_bill_item, null);
		}
		return convertView;
	}

	
	
}
