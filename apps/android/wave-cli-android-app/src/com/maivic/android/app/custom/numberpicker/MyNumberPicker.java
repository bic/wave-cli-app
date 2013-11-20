package com.maivic.android.app.custom.numberpicker;

import android.content.Context;
import android.util.AttributeSet;

import com.maivic.android.widget.NumberPicker;

public class MyNumberPicker extends NumberPicker {

	public MyNumberPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public MyNumberPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public MyNumberPicker(Context context) {
		super(context);
	}
	
	protected void initializeInternal(){
		setLayoutSetupHelper(new MySimpleLayoutSetupHelper(getContext()));
	}
	
}
