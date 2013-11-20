package com.maivic.android.app.adapter;

import com.maivic.android.app.R;
import com.maivic.android.app.utils.FontUtils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class OffersAdapter extends BaseAdapter{
	Context mContext;
	LayoutInflater mInflater;
	
	public OffersAdapter(Context mContext) {
		super();
		this.mContext = mContext;
		mInflater = LayoutInflater.from(mContext);
	}

	@Override
	public int getCount() {
		return 10;
	}

	@Override
	public Object getItem(int arg0) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Tag tag;
		if(convertView == null){
			tag = new Tag();
			convertView = mInflater.inflate(R.layout.offer_item, null);
			tag.tvCourseName0 = (TextView) convertView.findViewById(R.id.tvCourse0);
			tag.tvCourseDesc0 = (TextView) convertView.findViewById(R.id.tvCourseDesc0);
			tag.tvCourseName1 = (TextView) convertView.findViewById(R.id.tvCourse1);
			tag.tvCourseDesc1 = (TextView) convertView.findViewById(R.id.tvCourseDesc1);
			tag.tvCourseName2 = (TextView) convertView.findViewById(R.id.tvCourse2);
			tag.tvCourseDesc2 = (TextView) convertView.findViewById(R.id.tvCourseDesc2);
			tag.tvBY = (TextView) convertView.findViewById(R.id.tvBy);
			tag.tvRestaurant = (TextView) convertView.findViewById(R.id.tvRestaraunt);
			
			convertView.setTag(tag);
			
			// apply custom font for textViews
			setupConvertView(convertView);
		} else{
			tag = (Tag) convertView.getTag();
		}
		//TODO bind data
		return convertView;
	}

	private void setupConvertView(View convertView){
		Tag tag = (Tag) convertView.getTag();
		FontUtils.setFontFotTextView(mContext, tag.tvCourseName0, FontUtils.FONT_AVENIR_LIGHT);
		FontUtils.setFontFotTextView(mContext, tag.tvCourseName1, FontUtils.FONT_AVENIR_LIGHT);
		FontUtils.setFontFotTextView(mContext, tag.tvCourseName2, FontUtils.FONT_AVENIR_LIGHT);
		
		FontUtils.setFontFotTextView(mContext, tag.tvCourseDesc0, FontUtils.FONT_AVENIR_LIGHT);
		FontUtils.setFontFotTextView(mContext, tag.tvCourseDesc1, FontUtils.FONT_AVENIR_LIGHT);
		FontUtils.setFontFotTextView(mContext, tag.tvCourseDesc2, FontUtils.FONT_AVENIR_LIGHT);
		
		FontUtils.setFontFotTextView(mContext, tag.tvBY, FontUtils.FONT_AVENIR_LIGHT);
		FontUtils.setFontFotTextView(mContext, tag.tvRestaurant, FontUtils.FONT_AVENIR_LIGHT);
	}
	
	static class Tag{
		TextView tvCourseName0;
		TextView tvCourseDesc0;
		TextView tvCourseName1;
		TextView tvCourseDesc1;
		TextView tvCourseName2;
		TextView tvCourseDesc2;
		TextView tvBY;
		TextView tvRestaurant;
	}
}
