package com.maivic.android.app.custom.locatoinselector;

import com.maivic.android.app.custom.locatoinselector.model.Item;

import android.content.Context;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

class SegmentView extends LinearLayout{

	private Item<Object> item; 
	
	private TextView mTextValueView;
	private Button mDeleteButton;
	
	public SegmentView(Context context) {
		super(context);
	}
	
	public void setItem(Item<Object> item){
		this.item = item;
	}

}
