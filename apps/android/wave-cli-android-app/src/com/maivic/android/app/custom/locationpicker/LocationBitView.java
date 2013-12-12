package com.maivic.android.app.custom.locationpicker;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.maivic.android.app.R;

class LocationBitView extends RelativeLayout {
	
	private static final String T = "LocationBitView"; 

	private TextView mTextValueView;
	
	private boolean mFixedFlag;
	
	private boolean mRootFlag;
	
	private ImageButton mDeleteButton;
	
	private LocationBitDataset mLocationBit;
	
	private OnLocationBitRemoveListener mOnLocationBitRemoveListener;

	public LocationBitView(Context context, OnLocationBitRemoveListener onRemoveListener) {
		super(context);
		mOnLocationBitRemoveListener = onRemoveListener;
		setupWidgets();
	}

	public LocationBitView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupWidgets();
	}

	public LocationBitView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupWidgets();
	}
	
	public LocationBitDataset getLocationBitDataset(){
		return mLocationBit;
	}

	private void setupWidgets() {
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.view_location_bit, this, true);
		mTextValueView = (TextView) findViewById(R.id.tvLabel);
		mDeleteButton = (ImageButton) findViewById(R.id.btnClose);
		
		// assign on click listener, to inform LocationPicker about removing location bit
		mDeleteButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				if(mOnLocationBitRemoveListener != null){
//					mOnLocationBitRemoveListener.onLocationBitRemove(LocationBitView.this, mLocationBit);
				}
			}
		});
		
		// assign on touch listener, to provide selection behavior
		mDeleteButton.setOnTouchListener(mOnTouchListeners);
	}
	
	public void setLocationBit(LocationBitDataset locationBit){
		setLocationBit(locationBit, false, false);
	}
	
	public void setLocationBit(LocationBitDataset locationBit, boolean rootFlag, boolean fixedFlag) {
		mLocationBit = locationBit;
		mRootFlag = rootFlag;
		mFixedFlag = fixedFlag;

		updateGui();
	}
	
	public void setFullMode(boolean full){
		
		if(full){
			findViewById(R.id.spaceView).setVisibility(View.VISIBLE);
		} else{
			findViewById(R.id.spaceView).setVisibility(View.GONE);
		}
	}
	
	private void updateGui(){
		mTextValueView.setText(mLocationBit.name);

		if(mFixedFlag){
			mDeleteButton.setVisibility(View.GONE);
			mTextValueView.setTypeface(Typeface.DEFAULT_BOLD);
			
		} else{
			mDeleteButton.setVisibility(View.VISIBLE);
			mTextValueView.setTypeface(Typeface.DEFAULT);
		}
		
		// 
		if(mRootFlag){
			findViewById(R.id.content).setBackgroundResource(R.drawable.bck_parent_locatoin_bit);
		} else{
			findViewById(R.id.content).setBackgroundResource(R.drawable.bck_locatoin_bit);
		}
	}
	
	

	@Override
	public String toString() {
		return super.toString() + "[" + mLocationBit.name + "]";
	}
	
	private OnTouchListener mOnTouchListeners = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			
			switch(event.getAction()){
			case MotionEvent.ACTION_DOWN:
				// handle down action
//				LocationBitView.this.setSelected(true);
				findViewById(R.id.content).setBackgroundResource(R.drawable.bck_locatoin_bit_pressed);
				break;
				
			case MotionEvent.ACTION_CANCEL:
			case MotionEvent.ACTION_UP:
				// handle up action
				findViewById(R.id.content).setBackgroundResource(R.drawable.bck_locatoin_bit);
//				LocationBitView.this.setSelected(false);
					break;
			}
			
			return false;
		}
	};



	public static interface OnLocationBitRemoveListener{
		public void onLocationBitRemove(View view, LocationBitDataset locationBit);
	}
	
}
