package com.maivic.android.app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.maivic.android.app.adapter.OffersAdapter;

/**
 * Activity with list of offers
 * 
 * @author Serghei
 * 
 */
public class OffersActivity extends BaseActivity {
	ListView offersListView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		offersListView = (ListView) findViewById(R.id.listView);
		offersListView.setAdapter(new OffersAdapter(this));
		offersListView.setOnItemClickListener(onItemClickListener);
	}
	

	@Override
	protected View getContentView() {
		return mInflater.inflate(R.layout.activity_offers, null);
	}

	private OnItemClickListener onItemClickListener = new OnItemClickListener() {

		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
				long arg3) {
			Intent intent = new Intent(OffersActivity.this, CommandConfirmationActivty.class);
			startActivity(intent);
		}
	};
}
