package com.maivic.android.app.adapter;

import net.maivic.protocol.Model.OfferOption;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.maivic.android.app.R;

/**
 * Part of confirmation view, to show list of courses
 * 
 * @author Serghei
 *
 */
public class CommandCourseAdapter extends BaseAdapter{

	private Context mContext;
	private LayoutInflater mInflater;
	public CommandCourseAdapter(Context context){
		mContext = context;
		mInflater = LayoutInflater.from(context);
//		OfferOption option;
//		option.
	}
	
	@Override
	public int getCount() {
		//TODO implement de 
		return 2;
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
	public View getView(int position, View convertView, ViewGroup parent) {
		if(convertView == null){
			convertView = mInflater.inflate(R.layout.command_course_item, null);
		}
		return convertView;
	}
}
