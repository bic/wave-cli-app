package com.maivic.restaurant;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.maivic.context.Log;
import net.maivic.protocol.Model;
import net.maivic.protocol.Model.OfferOption;
import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.restaurantapp.R;
import com.maivic.android.NumberPickerTestActivity;
import com.maivic.android.widget.NumberPicker;
import com.maivic.android.widget.NumberPicker.OnValueChangedListener;
import com.maivic.restaurant.data.LocalOfferOptionDistributor;
import com.maivic.restaurant.data.UpdateDataCallback;

public class CustomArrayAdapter extends ArrayAdapter<OfferOption> {
	Context context;
	Map<Integer, Integer> offer_option_id_to_position = new HashMap<Integer, Integer>();
	private static Log log=net.maivic.context.Context.get().log();
	private static final String TAG = CustomArrayAdapter.class.getName();

	public CustomArrayAdapter(Context context, int textViewResourceId, int layoutId) {
		// super(context, android.R.layout.simple_list_item_1);
		super(context, textViewResourceId);
		this.context = context;

		OfferActivity a = (OfferActivity) context;
		
		int idx = myIndex(layoutId);
		LocalOfferOptionDistributor source = a.optionsConfigurationContent[idx];
		
		
		source.registerValueListener(new UpdateDataCallback<Model.OfferOption>() {
			
			@Override
			public void updateValues(OfferOption value) {
				if(value.getIsOfferChanger()) {
					int i=0;
					i=1;
				}
				add(value);
			}
		});
		
		/*a.perOfferOfferOption
				.registerValueListener(new UpdateDataCallback<OfferOption>() {

					@Override
					public void updateValues(OfferOption value) {
						Integer old_position = offer_option_id_to_position
								.get(value.getId());
						if (old_position != null) {
							remove(getItem(old_position));
							insert(value, old_position);
						} else {

							add(value);
						}
					}
				});*/
		// Course rowItem = getItem(position);

	}



	/**
	 * Populate new items in the list.
	 */
	@Override
	public View getView(int position, View convertViewParam, ViewGroup parent) {

		LayoutInflater mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		final View convertView = convertViewParam != null ? convertViewParam
				: mInflater.inflate(R.layout.list_row, null);
		if (this.getCount() == 0)
			return convertView;

		OfferOption offerOption = getItem(position);

		// holder.courseCount = (TextView)
		// convertView.findViewById(R.id.courseCount);
		this.setTitle(R.id.courseName, offerOption.getNameSingular(),
				convertView);
		this.setTitle(R.id.edit_courseName, offerOption.getNameSingular(),
				convertView);
		
		BigDecimal priceChanger = BigDecimal.valueOf(offerOption.getPrice().getValue(), offerOption.getPrice().getScale()*(-1));
		BigDecimal max = new BigDecimal(offerOption.getMaxPerOffer());
		BigDecimal included =new BigDecimal(offerOption.getIncludedQty());
		//pcirMath.round(priceChanger.doubleValue());
		
		
		
		/*this.initNumberPickerValue(R.id.npOptionLeft, max,
				convertView);
		this.initNumberPickerValue(R.id.npOptionMiddle, included,
				convertView);		
		this.initNumberPickerValue(R.id.npOptionRight, priceChanger, 
				convertView);*/
		
		this.updateLeftNumberPickerValue(R.id.npOptionLeft, max, convertView);
		this.updateMiddleNumberPickerValue(R.id.npOptionMiddle, included, convertView);
		this.updateRightNumberPickerValue(R.id.npOptionRight, priceChanger, convertView);
		
		//this.updateNumberPickerValue(R.id.npOptionLeft, max,
		//		convertView);
		
		if (position == 0) {
			this.setTitle(R.id.first_number_picker_title,
					this.firstNumberPickerTitle, convertView);

			this.setTitle(R.id.second_number_picker_title,
					this.secondNumberPickerTitle, convertView);
			this.setTitle(R.id.third_number_picker_title,
					this.thirdNumberPickerTitle, convertView);

			this.showChild(R.id.first_number_picker_title, convertView);
			this.showChild(R.id.second_number_picker_title, convertView);
			this.showChild(R.id.third_number_picker_title, convertView);
		} else {
			this.hideChild(R.id.first_number_picker_title, convertView);
			this.hideChild(R.id.second_number_picker_title, convertView);
			this.hideChild(R.id.third_number_picker_title, convertView);
		}
		// holder.courseCount.setText(rowItem.getCourse_type());
		// notifyDataSetChanged();
		/*
		 * rowItem.addConfigurableListener(new DataCallback<Boolean>() {
		 * 
		 * @Override public void updated(Boolean value) { if(value)
		 * convertView.setVisibility(View.VISIBLE); else
		 * convertView.setVisibility(View.GONE);
		 * 
		 * } });
		 */

		final ImageButton edit_course_name_btn = (ImageButton) convertView
				.findViewById(R.id.edit_config_options);
		final ImageButton save_course_name_btn = (ImageButton) convertView
				.findViewById(R.id.save_config_options);
		final EditText edit_courseName = (EditText) convertView
				.findViewById(R.id.edit_courseName);
		final TextView courseName1 = (TextView) convertView
				.findViewById(R.id.courseName);
		edit_course_name_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
			
				edit_courseName.setVisibility(View.VISIBLE);
				courseName1.setVisibility(View.GONE);
				edit_course_name_btn.setVisibility(View.GONE);

				save_course_name_btn.setVisibility(View.VISIBLE);
			}
		});

		save_course_name_btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				edit_courseName.setVisibility(View.GONE);
				courseName1.setVisibility(View.VISIBLE);
				edit_course_name_btn.setVisibility(View.VISIBLE);

				save_course_name_btn.setVisibility(View.GONE);

			}
		});

		edit_courseName.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				

			}

			@Override
			public void afterTextChanged(Editable s) {
				
				// cb.setEnabled(true);
				// cb.setChecked(this.course.isConfigurable());
				// course_name.setText(s);
				courseName1.setText(s);

			}
		});

		edit_courseName
				.setOnFocusChangeListener(new View.OnFocusChangeListener() {
					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						if (!hasFocus) {
							// course_name.setKeyListener(null);
							edit_courseName.setFocusable(true);
							edit_courseName.setFocusableInTouchMode(true);
						}
					}
				});

		//notifyDataSetChanged();
		return convertView;
	}

	private void hideChild(int resId, View v) {
		v.findViewById(resId).setVisibility(View.GONE);
	}

	private void showChild(int resId, View v) {
		v.findViewById(resId).setVisibility(View.VISIBLE);
	}

	public void setTitle(int textViewId, String title, View parent) {
		TextView t = (TextView) parent.findViewById(textViewId);
		t.setText(title);
	}
	
	public void initNumberPickerValue(int resId, BigDecimal value, View parent){
		NumberPicker np= (NumberPicker) parent.findViewById(resId);
		//np.getValue();
		np.setValue(value, true);
	}
	
	//from NP widget get action buttons - on + call onButtonClickListener 
	
	public void updateLeftNumberPickerValue(int resId, BigDecimal value, View parent){
		NumberPicker np= (NumberPicker) parent.findViewById(resId);
		//initNumberPickerValue(resId, value, parent);
		np.setValue(value, true);
		log.e(TAG, "update left number picker on np " + np);
		np.setValueChangedListener(new OnValueChangedListener() {
			
			@Override
			public void onValueChanged(NumberPicker arg0, BigDecimal arg1,
					BigDecimal arg2) {
					arg0.setValue(arg2, true);
					BigDecimal oldValue = arg1;
					BigDecimal newValue = arg2;
					//what.onValueChanged(arg0, oldValue, newValue);
					//BigDecimal savedValue = np.getValue();
					
					
			}
		});
		
		
	}
	
	public void updateMiddleNumberPickerValue(int resId, BigDecimal value, View parent){
		NumberPicker np= (NumberPicker) parent.findViewById(resId);
		//initNumberPickerValue(resId, value, parent);
		np.setValue(value, true);
		log.e(TAG, "update middle number picker on np " + np);
		/*np.setValueChangedListener(new OnValueChangedListener() {
			
			@Override
			public void onValueChanged(NumberPicker arg0, BigDecimal arg1,
					BigDecimal arg2) {
					arg0.setValue(arg2, true);
					BigDecimal seeValue = arg2;
					
					notifyDataSetChanged();
			}
		});*/
		
		
	}
	
	public void updateRightNumberPickerValue(int resId, BigDecimal value, View parent){
		NumberPicker np= (NumberPicker) parent.findViewById(resId);
		//initNumberPickerValue(resId, value, parent);
		np.setValue(value, true);
		log.e(TAG, "update right number picker on np " + np);
		/*np.setValueChangedListener(new OnValueChangedListener() {
			
			@Override
			public void onValueChanged(NumberPicker arg0, BigDecimal arg1,
					BigDecimal arg2) {
					arg0.setValue(arg2, true);
					
					
					notifyDataSetChanged();
			}
		});
		*/
		
	}
	

	private String firstNumberPickerTitle;

	public void setFirstNumberPickerTitle(String first_nr_picker) {
		
		this.firstNumberPickerTitle = first_nr_picker;

	}

	public String getFirstNumberPickerTitle() {
		return firstNumberPickerTitle;
	}

	private String secondNumberPickerTitle;

	public void setSecondNumberPickerTitle(String second_nr_picker) {
		
		this.secondNumberPickerTitle = second_nr_picker;

	}

	public String getSecondNumberPickerTitle() {
		return secondNumberPickerTitle;
	}

	private String thirdNumberPickerTitle;
	

	public void setThirdNumberPickerTitle(String third_nr_picker) {
		
		this.thirdNumberPickerTitle = third_nr_picker;

	}

	public String getThirdNumberPickerTitle() {
		return thirdNumberPickerTitle;
	}

	private int myOptions;
	public void setOptionsFragmentId(int myOptions){
		this.myOptions = myOptions;
	}
	
	private int myIndex(int R_id) {

		switch (R_id) {
		case R.id.options_per_course:
			return 0;
		case R.id.options_per_offer:
			return 1;
		case R.id.options_per_order:
			return 2;

		default:
			return -1;
		}
	
	}
	
	
	/*public void SetData(List<OfferOption> list) {
		int type = 1;

		List<OfferOption> myList = new ArrayList<OfferOption>();

		clear();
		for (OfferOption option : list) {
			switch (type) {
			case 1:
				if (option.getIsOfferChanger()) {
					add(option);
				}

				break;
			case 2:
				if (!option.getIsOfferChanger()) {
					add(option);

				}

				break;

			case 3:
				if (!option.getIsOfferChanger()) {
					add(option);
				}

				break;

			default:
				break;
			}
		}
		

		
		((OfferActivity) getContext()).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				
				notifyDataSetChanged();
			}
		});
		// notifyDataSetChanged();

	}
*/



}