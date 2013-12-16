package com.maivic.android.app.custom.locationpicker;

import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;

import com.maivic.android.app.R;
import com.maivic.android.app.custom.locationpicker.DatasetModel.UpdateEvent;
import com.maivic.android.app.custom.locationpicker.LocationBitView.OnLocationBitRemoveListener;

@SuppressLint("NewApi")
public class LocationPicker extends LinearLayout implements Observer{
	private static final String T = "LocationPicker";
	
	private LinearLayout mContentLayout;
	private ViewGroup mValidatorContatiner;
	private TextView mLocationKindTV;
	
	private int pickerLevel;
	private Dataset dataset;
	private DatasetModel datasetModel;
	private LocationActiveSearch activeSearch;
	
	private LocationPicker child; 
	
	private LocationPicker parent; 

	private int rowFreeSpace;
	
	public LocationPicker(Context context, AttributeSet attrs,
			int defStyle) {
		super(context, attrs, defStyle);
		initilizeWidget(attrs);
	}

	public LocationPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		initilizeWidget(attrs);
	}
	
	public LocationPicker(Context context) {
		super(context);
		initilizeWidget(null);
	}
	
	
	private void initilizeWidget(AttributeSet attrs){
		activeSearch = new LocationActiveSearch(getContext());
		activeSearch.setHint("The activie search!!!");
		
		LayoutInflater inflater = LayoutInflater.from(getContext());
		inflater.inflate(R.layout.view_location_picker, this);
		mValidatorContatiner = (ViewGroup) findViewById(R.id.validationContent);
		mContentLayout = (LinearLayout) findViewById(R.id.layoutContent);
		
		if(attrs != null){
			TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs, R.styleable.LocationPicker, 0, 0);
			
			try{
				TextView textViewLocationKind = (TextView) findViewById(R.id.tvText1);
				String locationKind = a.getString(R.styleable.LocationPicker_location_kind);
				textViewLocationKind.setText(locationKind);
				
				pickerLevel = a.getInt(R.styleable.LocationPicker_picker_level, 0);
			} finally{
				a.recycle();
			}
		}
	}
	
	public void setDatasetModel(DatasetModel datasetModel){
		// unregister observer from old dataset
		if(this.datasetModel != null){
			this.datasetModel.deleteObserver(this);
		}

		this.datasetModel = datasetModel;
		datasetModel.addObserver(this);
		dataset = datasetModel.getDataset();
		updateDatasetState();
	}
	
	public void updateDatasetState(){
		measureAndFillLocationBits();
	}
		
	public boolean isValidated(){
		// The location picker segment is validet, if dataset is complete or if
		// has child location bits
		// Note, the complete flag is actual only for location picker with
		// pickerLevel = location_bits_content_by_picker_level.size()
		
		return dataset.complete
				|| pickerLevel < dataset.location_bits_content_by_picker_level.size() - 1;
	}
	
	public void setDataset(Dataset dataset){
		this.dataset = dataset;
		validateDataset();
		
		measureAndFillLocationBits();
	}
	
	private void validateDataset(){
		// TODO this is wrong, because the dataset is shared over all!!!
		// update this according with specification behavior
		if(dataset.getLocation_bits_content_by_picker_level().size() <= pickerLevel){
			throw new IllegalStateException("The dataset is not consistent(pickerLevel is more than dimension of dataset)");
		}
	}
	
	private void measureAndFillLocationBits(){
		updateValidationView();
		
		mContentLayout.removeAllViews();
		
		// determine width of content layout
		View dummyView = new View(getContext());
		mContentLayout.addView(dummyView);
		
		ViewTreeObserver vto = mContentLayout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			
			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				Log.d(T, "The width of contentView is " + mContentLayout.getWidth());

				mContentLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				fillLocationBits();
			}
		});		
	}
	
	private void updateValidationView(){
		View completeView = findViewById(R.id.completeView);
		View incompleteView = findViewById(R.id.incompleteView);
		
		if(isValidated()){
			completeView.setVisibility(View.VISIBLE);
			incompleteView.setVisibility(View.GONE);
			
		} else{
			completeView.setVisibility(View.GONE);
			incompleteView.setVisibility(View.VISIBLE);
		}
	}
	
	private void fillLocationBits(){
		rowFreeSpace = mContentLayout.getMeasuredWidth();
				
		mContentLayout.removeAllViews();
		
		if(pickerLevel >= dataset.location_bits_content_by_picker_level.size()){
			setVisibility(View.GONE);
			return;
			
		} else{
			setVisibility(View.VISIBLE);
		}
		
		List<LocationBitDataset> locationBits = dataset.location_bits_content_by_picker_level.get(pickerLevel);
		
		Log.d(T, "Contentlayout width: " + mContentLayout.getWidth());
		
		if(locationBits != null){
			for(int i = 0; i < locationBits.size(); i++){
				final LocationBitDataset locationBit = locationBits.get(i);
				boolean fixedFlag  =   pickerLevel == 0   && i < dataset.fixed_location_bits_count;
				boolean parentFlag =  pickerLevel == 0 && i == 0;
				
				final LocationBitView locationBitView = new LocationBitView(
						getContext(), mOnLocationBitRemoveListener);
				
				locationBitView.setLocationBit(locationBit, parentFlag, fixedFlag);
				addLocationBitView(locationBitView, i == locationBits.size() - 1);
			}
			
		} else{
			Log.d(T, "The location bits is null!!!");
		}
		
		if(!isValidated()){
//			addView(activeSearch);
		}
	}
	
	private void addLocationBitView(LocationBitView view, boolean insertLastView){
		Log.d(T, "add locatoinView " + view);
		
		int childCount = mContentLayout.getChildCount();
		LinearLayout rowContainer;
		
		if (childCount > 0) {
			rowContainer = (LinearLayout) mContentLayout.getChildAt(childCount - 1);

		} else {
			rowContainer = createRowContainer();
			mContentLayout.addView(rowContainer);
		}
		
		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);

		rowContainer.addView(view, params);
		
		//>> start location fix
		view.measure(0, 0);
		int viewMeasuredWidth = view.getMeasuredWidth();
		if(rowFreeSpace < viewMeasuredWidth){
			rowContainer.removeView(view);
			
			// update position
			repositionLocationViewInRow(rowContainer);
			
			rowContainer = createRowContainer();
			mContentLayout.addView(rowContainer);
			rowContainer.addView(view, params);
			rowFreeSpace = mContentLayout.getMeasuredWidth() - viewMeasuredWidth;
			
		} else{
			rowFreeSpace -= viewMeasuredWidth;
		}
		
		if(insertLastView){
			repositionLocationViewInRow(rowContainer);
		}
		//<< end location fix
	}
	
	private void repositionLocationViewInRow(LinearLayout rowContainer){
		int lastViewCalculatedWidth = mContentLayout.getMeasuredWidth();
		
		for(int i = 0; i < rowContainer.getChildCount(); i++){
			View child = rowContainer.getChildAt(i);
			
			if(i < rowContainer.getChildCount() - 1){
				lastViewCalculatedWidth -= child.getMeasuredWidth();
				
			} else{
				Log.i(T, "remeasure view "+child +" currentWidth: " + child.getMeasuredWidth() +" newWidth: "+lastViewCalculatedWidth);
				
				LinearLayout layout = (LinearLayout) child.findViewById(R.id.content);
				ViewGroup.LayoutParams params =  layout.getLayoutParams();
				params.width = lastViewCalculatedWidth;
				layout.setLayoutParams(params);
			}
		}		
	}
	
	private void updatePositionOfLocationViewOLD(){
		int childCount = mContentLayout.getChildCount();
		
		if(childCount > 0){
			LinearLayout rowContainer = (LinearLayout) mContentLayout.getChildAt(childCount - 1);
			
			int rowWidth = mContentLayout.getWidth();
			int totalChildWidth = 0;
			
			for (int i = 0; i < rowContainer.getChildCount(); i++) {
				View v = rowContainer.getChildAt(i);
				v.measure(0, 0);
				
				v.setBackgroundColor(Color.GREEN);
				
				int viewWidth = v.getMeasuredWidth();
				Log.d(T, "view: " + v + ",viewWidth: "+viewWidth);
				totalChildWidth += viewWidth;
				if(totalChildWidth >= rowWidth && i > 0){
					rowContainer.removeView(v);
					
					for(int j = 0; j < i; j++){
						View v2 = rowContainer.getChildAt(j);
						if(j % 2 == 0){
							v2.setBackgroundColor(Color.RED);
							
						} else{
							v2.setBackgroundColor(Color.BLUE);
						}
						
						LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT, 
								LayoutParams.WRAP_CONTENT);
//						params.weight = 1;
						v2.setLayoutParams(params);
					}
					
					LinearLayout ll2 = createRowContainer();
					ll2.addView(v, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
					mContentLayout.addView(ll2);
					break;
				}
			}
		}
	}
	
	private LinearLayout createRowContainer(){
		LinearLayout layout = new LinearLayout(getContext());
		layout.setOrientation(LinearLayout.HORIZONTAL);
		return layout;
	}
	
	@Override
	public void update(Observable observable, Object data) {
		Log.d(T, "call update...");
		
		UpdateEvent event = (UpdateEvent) data;
		dataset = event.dataset;
		if(pickerLevel >= event.level){
			updateDatasetState();
		}
		
//		dataset = (Dataset) data;
//		updateDatasetState();			
	}
	
	// listeners
	private OnLocationBitRemoveListener mOnLocationBitRemoveListener = new OnLocationBitRemoveListener() {
		
		@Override
		public void onLocationBitRemove(View view, LocationBitDataset locationBit) {
			// update data set
			Log.d(T, "onLocationBitRemove " + locationBit);
			
			if(pickerLevel < dataset.location_bits_content_by_picker_level.size()){
				// remove all location bits in this picker
				List<LocationBitDataset> locationBits = dataset.location_bits_content_by_picker_level.get(pickerLevel);
				for(int i = 0; i < locationBits.size(); i++){
					if(locationBit == locationBits.get(i)){
						Log.d(T, "remove elements after " + i);

						int last = locationBits.size() - 1;
						while(last >= i){
							Log.d(T, "rempove element " + last);
							locationBits.remove(last);
							last = locationBits.size() - 1;
						}
						
						break;
					}
				}
				
				// remove location bits for childs
				for(int i = pickerLevel + 1; i < dataset.location_bits_content_by_picker_level.size(); i++){
					dataset.location_bits_content_by_picker_level.remove(i);
				}
			}
			
			// update dataset model, and inform all child location pickers
			datasetModel.setDataset(dataset);
			datasetModel.notifyObservers(new UpdateEvent(dataset, pickerLevel));
		}
	};
	// ~listeners
}
