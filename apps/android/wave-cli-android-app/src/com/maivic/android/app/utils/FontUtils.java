package com.maivic.android.app.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.Button;
import android.widget.TextView;

public class FontUtils {
	
	// Assets font constants
	public static final String FONT_AVENIR_ROMAN = "fonts/avenir_roman.ttf";
	public static final String FONT_AVENIR_MEDIUM = "fonts/avenir_medium.ttf";
	public static final String FONT_AVENIR_OBLIQUE = "fonts/avenir_oblique.ttf";
	public static final String FONT_AVENIR_MEDIUM_OBLIQUE = "fonts/avenir_medium_oblique.ttf";
	public static final String FONT_AVENIR_LIGHT = "fonts/avenir_light.ttf";
	public static final String FONT_AVENIR_LIGHT_OBLIQUE = "fonts/avenir_light_oblique.ttf";
	public static final String FONT_AVENIR_HEAVY = "fonts/avenir_heavy.ttf";
	public static final String FONT_AVENIR_HEAVY_OBLIQUE = "fonts/avenir_heavy_oblique.ttf";
	public static final String FONT_AVENIR_BOOK = "fonts/avenir_book.ttf";
	public static final String FONT_AVENIR_BOOK_OBLIQUE = "fonts/avenir_book_oblique.ttf";
	public static final String FONT_AVENIR_BLACK = "fonts/avenir_black.ttf";
	public static final String FONT_AVENIR_BLACK_OBLIQUE = "fonts/avenir_black_oblique.ttf";
	
	public static void setFontFotTextView(Context context, TextView textView, String assetsFontName){
		Typeface tf = Typeface.createFromAsset(context.getAssets(), assetsFontName);
		textView.setTypeface(tf);
	}
	
	public static void setFontForTextView(Context context, TextView textView, int resId){
		setFontFotTextView(context, textView, context.getString(resId));
	}
	
	public static void setFontForButton(Context context, Button buttonView, String assetsFontName){
		if(assetsFontName != null){
			Typeface tf = Typeface.createFromAsset(context.getAssets(), assetsFontName);
			buttonView.setTypeface(tf);			
		}
	}
	
	public static void setFontForButton(Context context, Button buttonView, int resId){
		setFontForButton(context, buttonView, context.getString(resId));
	}
}
