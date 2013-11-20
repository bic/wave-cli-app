package com.maivic.android.app;

import android.os.Bundle;
import android.view.View;

public class ProfileActivity extends BaseActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	
	}

	@Override
	protected View getContentView() {
		return getLayoutInflater().inflate(R.layout.activity_profile, null);
	}
}
