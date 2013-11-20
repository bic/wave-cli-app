package com.maivic.android.app.utils;

import android.content.res.Resources;
import android.util.DisplayMetrics;

public class DimensionUtils {
	
	public static float convertDpToPx(float dpSize){
		DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
		return displayMetrics.density * dpSize;
	}
	
	public static float convertPxToDp(float pxSize){
		DisplayMetrics displayMetrix = Resources.getSystem().getDisplayMetrics();
		return displayMetrix.density * pxSize;
	}
	
	public static float getDimensionFromResource(int resId){
		return Resources.getSystem().getDimension(resId);
	}
}
