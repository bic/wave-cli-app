package com.maivic.android.app.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;

import com.maivic.android.app.R;
import com.maivic.android.app.utils.FontUtils;

/**
 * This class is extension of {@link Button}, to provide feature with custom
 * font
 * 
 * @author Serghei
 * 
 */
public class FontedButton extends Button {

	public FontedButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initializeAttributes(attrs);		
	}

	public FontedButton(Context context, AttributeSet attrs) {
		super(context, attrs);
		initializeAttributes(attrs);
	}

	public FontedButton(Context context) {
		super(context);
	}

	
	private void initializeAttributes(AttributeSet attrs){
		if(isInEditMode()){
			// android can't load font from assset in edit mode
			return;
		}
		
		TypedArray array = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.FontedButton, 0, 0);
		try{
			String fontName = array.getString(R.styleable.FontedButton_fontName);
			if(fontName != null)
				FontUtils.setFontForButton(getContext(), this, fontName);
		} catch(Exception e){
		}
		finally{
			array.recycle();
		}
	}
}
