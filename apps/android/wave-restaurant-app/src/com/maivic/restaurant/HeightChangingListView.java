package com.maivic.restaurant;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;
import android.widget.ListView;

public class HeightChangingListView extends ListView{

	public HeightChangingListView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}
	
	
	 public HeightChangingListView(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}


	public HeightChangingListView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}


	@Override
	 protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	 {
	     super.onMeasure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(MeasureSpec.UNSPECIFIED, 0));

	     int childHeight = getMeasuredHeight() - (getListPaddingTop() + getListPaddingBottom() +  getVerticalFadingEdgeLength() * 2);

	     // on a first run let's have a space for at least one child so it'll trigger remeasurement
	     int fullHeight = getListPaddingTop() + getListPaddingBottom() + childHeight*(getCount());

	     int newChildHeight = 0;
	     for (int x = 0; x<getChildCount(); x++ ){
	         View childAt = getChildAt(x);

	         if (childAt != null) {
	             int height = childAt.getHeight();
	             newChildHeight += height;
	         }
	     }

	     //on a second run with actual items - use proper size
	     if (newChildHeight != 0)
	         fullHeight = getListPaddingTop() + getListPaddingBottom() + newChildHeight;

	     setMeasuredDimension(getMeasuredWidth(), fullHeight);
	 }

}