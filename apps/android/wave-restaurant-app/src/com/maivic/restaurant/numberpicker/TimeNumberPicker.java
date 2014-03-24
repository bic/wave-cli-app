package com.maivic.restaurant.numberpicker;

import android.content.Context;
import android.util.AttributeSet;

import com.maivic.android.widget.NumberPicker;

public class TimeNumberPicker extends NumberPicker {

		public TimeNumberPicker(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}

		public TimeNumberPicker(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public TimeNumberPicker(Context context) {
			super(context);
		}
		
		protected void initializeInternal(){
			setLayoutSetupHelper(new MultipleButtonsSimpleLayoutNumberPicker(getContext()));
		}
		
	}

