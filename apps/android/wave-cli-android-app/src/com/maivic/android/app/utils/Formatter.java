package com.maivic.android.app.utils;

import net.maivic.protocol.Model.Decimal;
import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

public class Formatter {
	private static final String T = "Formatter";
	
	@SuppressLint("DefaultLocale")
	public static String formatPrice(Context context, Decimal price){
		Log.d(T, "Formate price: " + price);
		
		if(price == null){
			return "";
		}
		
		long value = price.getValue();
		int scale = price.getScale();
		
		return String.format("%.2f", value * Math.pow(10, scale));
	}
}
