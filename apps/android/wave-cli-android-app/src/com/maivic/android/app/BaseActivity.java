package com.maivic.android.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class BaseActivity extends FragmentActivity {
	protected LinearLayout containerView;
	protected LayoutInflater mInflater;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		
		setContentView(R.layout.activity_base);
		containerView = (LinearLayout) findViewById(R.id.container);
		mInflater = LayoutInflater.from(this);

		View content = getContentView();
		if (content != null) {
			LinearLayout.LayoutParams params = new LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);

			containerView.addView(content, params);
		}
	}

	protected View getContentView() {
		return null;
	}
}
