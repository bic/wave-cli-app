package com.maivic.android.app.custom;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.TextView;

import com.maivic.android.app.R;
import com.maivic.android.app.utils.FontUtils;

public class FontedTextView extends TextView {

	public FontedTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initlizeAttr(attrs);
	}

	public FontedTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initlizeAttr(attrs);
	}

	public FontedTextView(Context context) {
		super(context);
	}

	private void initlizeAttr(AttributeSet attrs) {

		if (isInEditMode()) {
			// Can't load font from assets in edit mode
			return;
		}

		TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
				R.styleable.FontedTextView, 0, 0);
		try {
			String assetsFontName = a
					.getString(R.styleable.FontedTextView_fontName);
			FontUtils.setFontFotTextView(getContext(), this, assetsFontName);
		} catch (Exception e) {

		} finally {
			a.recycle();
		}
	}
}
