package com.maivic.android.app.custom;

import com.maivic.android.app.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.text.Editable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

@SuppressLint("NewApi")
public class LabeledEditText extends LinearLayout {

	private TextView mLabelView;
	private EditText mValueEditText;

	public LabeledEditText(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initlizeWidget();
		initlizeFromAttrs(attrs);
	}

	public LabeledEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		initlizeWidget();
		initlizeFromAttrs(attrs);
	}

	public LabeledEditText(Context context) {
		super(context);
		initlizeWidget();
	}

	private void initlizeWidget() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.view_label_edittext, this, true);
		setOrientation(LinearLayout.VERTICAL);

		mLabelView = (TextView) getChildAt(0);
		mValueEditText = (EditText) getChildAt(1);
	}

	private void initlizeFromAttrs(AttributeSet attrs) {
		TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
				R.styleable.LabeledEditText, 0, 0);

		try {
			String captionValue = a
					.getString(R.styleable.LabeledEditText_label);
			setLableValue(captionValue);

		} finally {
			a.recycle();
		}
	}

	public Editable getTextValue() {
		return mValueEditText.getText();
	}

	public void setLableValue(CharSequence text) {
		if (text != null) {
			mLabelView.setVisibility(View.VISIBLE);
			mLabelView.setText(text);

		} else {
			mLabelView.setVisibility(View.GONE);
		}
	}
}
