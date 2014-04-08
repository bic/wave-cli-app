package com.maivic.restaurant.numberpicker;

import android.content.Context;
import android.util.AttributeSet;

import com.maivic.android.widget.NumberPicker;

public class AvailableOffersNumberPicker extends NumberPicker {

		public AvailableOffersNumberPicker(Context context, AttributeSet attrs, int defStyle) {
			super(context, attrs, defStyle);
		}

		public AvailableOffersNumberPicker(Context context, AttributeSet attrs) {
			super(context, attrs);
		}

		public AvailableOffersNumberPicker(Context context) {
			super(context);
		}
		
		protected void initializeInternal(){
			setLayoutSetupHelper(new AvailableOffersSimpleLayoutSetupHelper(getContext()));
		}
		
	}