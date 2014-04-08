package com.maivic.restaurant;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.example.restaurantapp.R;
import com.maivic.restaurant.data.ActivityDataHolder;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NavUtils;
import android.text.format.Time;

public class ControlPanelMainActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_control_panel_main);
		Intent myIntent = getIntent();
		String valueToReceive = myIntent.getStringExtra(OfferActivity.EXTRA_DATA);
		
		/*TextView tVLocation = (TextView) findViewById(R.id.tv_location);
		tVLocation.setText(valueToReceive);*/
		
		LinearLayout activeOffersFragmentContainer = (LinearLayout) findViewById(R.id.active_offers_fragments_container);
		LinearLayout activeOffers = new LinearLayout(this);
		activeOffers.setOrientation(LinearLayout.HORIZONTAL);
		activeOffers.setId(121);
		
		android.app.FragmentManager fragMan = getFragmentManager();
		android.app.FragmentTransaction fragTransaction = fragMan.beginTransaction();
		
		ActiveOfferFragment frag1 = new ActiveOfferFragment();
		ActiveOfferFragment frag2 = new ActiveOfferFragment();
		
		fragTransaction.add(activeOffers.getId(), frag1, "frag1");
		
		//Time deadline = new Time();
		
		
		
		fragTransaction.commit();
		activeOffersFragmentContainer.addView(activeOffers);
		
	}



}
