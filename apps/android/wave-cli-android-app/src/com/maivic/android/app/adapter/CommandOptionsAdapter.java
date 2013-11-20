package com.maivic.android.app.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.maivic.android.app.R;

/**
 * Adapter show options of courses
 * 
 * @author Serghei
 * 
 */
public class CommandOptionsAdapter extends BaseAdapter {

	private static final int VIEW_TYPE_HEADER = 0;
	private static final int VIEW_TYPE_OPTION = 1;
	
	Context mContext;
	LayoutInflater mInflater;
	
	public CommandOptionsAdapter(Context context){
		mInflater = LayoutInflater.from(context);
		mContext = context;
	}
	
	@Override
	public int getCount() {
		return 3;
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}
	
	@Override
	public int getItemViewType(int position) {
		if(position == 0)
			return VIEW_TYPE_HEADER;
		
		return VIEW_TYPE_OPTION;
	}

	@Override
	public int getViewTypeCount() {
		return 2;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			if(getItemViewType(position) == VIEW_TYPE_HEADER){
				convertView = mInflater.inflate(R.layout.command_option_header, null);				
			} else{
				convertView = mInflater.inflate(R.layout.command_option_item, null);
			}
		}
		return convertView;
	}

}
