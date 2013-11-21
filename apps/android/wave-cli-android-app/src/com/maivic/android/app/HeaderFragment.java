package com.maivic.android.app;

import java.util.Arrays;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.maivic.android.app.custom.DeliveryIntervalDialog;
import com.maivic.android.app.custom.DeliveryIntervalDialog.OnIntervalSelectionListener;
import com.maivic.android.app.utils.FontUtils;

public class HeaderFragment extends Fragment{
	TextView mDeliveryIntervalText;
	TextView mLocationText;
	TextView mCurrentPageText;
	TextView mActiveComandsCountText;
	RelativeLayout mActiveCommands;
	ImageButton mDeliveryInterButton;
	ImageButton mLocationButton;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_header, null);
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		
		mDeliveryIntervalText = (TextView) view.findViewById(R.id.tvDeliveryInterval);
		mLocationText = (TextView) view.findViewById(R.id.tvLocation);
		mActiveComandsCountText = (TextView) view.findViewById(R.id.tvActiveCommandsCount);
		mCurrentPageText = (TextView) view.findViewById(R.id.tvCurrentPage);
		mActiveCommands = (RelativeLayout) view.findViewById(R.id.rlActiveCommands);
		mLocationButton = (ImageButton) view.findViewById(R.id.btnLocation);
		
		final Context context = getActivity();
		FontUtils.setFontFotTextView(context, mDeliveryIntervalText, FontUtils.FONT_AVENIR_MEDIUM);
		FontUtils.setFontFotTextView(context, mCurrentPageText, FontUtils.FONT_AVENIR_MEDIUM);
		FontUtils.setFontFotTextView(context, mLocationText, FontUtils.FONT_AVENIR_MEDIUM);
		FontUtils.setFontFotTextView(context, mActiveComandsCountText, FontUtils.FONT_AVENIR_MEDIUM);
		
		
		mDeliveryInterButton = (ImageButton) view.findViewById(R.id.btnTimer);

		setListeners();
	}
		
	private void setListeners(){
		mLocationButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// just open profile activity
				
				Intent intent = new Intent(getActivity(), ProfileActivity.class);
				startActivity(intent);
			}
		});
		
		mDeliveryInterButton.setOnClickListener(mDeliveriIntervalClickListrener);		
	}
	
	public void setLocationText(String location){
		
	}
	
	public void setCurrentPage(String pageName){
		
	}
	
	private OnClickListener mDeliveriIntervalClickListrener = new OnClickListener() {
		
		// data just for test
		private final String [] DUMMY_INTERVALS = new String[]{
			"12:30 - 13:00",
			"12:30 - 13:00",
			"12:30 - 13:00",
			"12:30 - 13:00",
			"12:30 - 13:00"
		};
		
		
		@Override
		public void onClick(View arg0) {
			
			// define selection listener
			DeliveryIntervalDialog.OnIntervalSelectionListener listener = new OnIntervalSelectionListener() {
				
				@Override
				public void onIntervalSelected(DeliveryIntervalDialog dialog,
						Object selectedInterval) {
					
					// TODO handle selected interval
					Toast.makeText(getActivity(),
							"Selected interval is " + selectedInterval,
							Toast.LENGTH_SHORT).show();
					
				}
			};
			
			DeliveryIntervalDialog dialog = new DeliveryIntervalDialog(
					getActivity(), Arrays.asList(DUMMY_INTERVALS), listener);
			
			dialog.show();
		}
	}; 
}
