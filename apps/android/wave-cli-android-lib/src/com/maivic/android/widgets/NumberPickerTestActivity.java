package com.maivic.android.widgets;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.maivic.android.widgets.NumberPicker.AlignType;
import com.maivic.android.widgets.NumberPicker.OnValueChangedListener;

/**
 * Activity to test NumberPicker widget 
 */
@SuppressLint("DefaultLocale")
public class NumberPickerTestActivity extends Activity {

	private NumberPicker mNumberPicker;
	
	private CheckBox mOnZeroCB, mInlineStyleCB;
	private EditText mValueET, mMaxValueET, mMinValueET, mLabelET,
			mPluralLabelET, mCheckBoxFirstValueET, mStepsPatternET;
	private Spinner mAlignTypeSP;
	
	private TextView mUpdateLabelTV;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_widgets_test);
		mNumberPicker = (NumberPicker) findViewById(R.id.numberPixker1);
		
		getIds();
		setupDefaultNumberPisker();
		initilizeControls();
		setControlListeners();
	}

	private void setupDefaultNumberPisker() {
		// for test
		mNumberPicker.setMax_value(10);
		mNumberPicker.setMin_value(-10);
		mNumberPicker.setLabel("Minute");
		mNumberPicker.setLabel_plural("Minutes");
		mNumberPicker.setCheckbox_first_value(1);
		mNumberPicker.setAlign(AlignType.HORISONTAL);
		try {
			mNumberPicker.setStepsPattern("-1; +1");
		} catch (StepsParseException e) {
			e.printStackTrace();
		}
		
		// attach value changed listener
		mNumberPicker.setmValueChangedListener(new OnValueChangedListener() {
			@Override
			public void onValueChanged(NumberPicker widget, float oldValue,
					float newValue) {
				String label = String.format("onValueChanged oldValue: %.1f, newValue: %.1f", oldValue, newValue);
				mUpdateLabelTV.setText(label);
			}
		});
	}

	private void initilizeControls() {
		mValueET.setText(String.valueOf(mNumberPicker.getValue()));
		mMaxValueET.setText(String.valueOf(mNumberPicker.getMaxValue()));
		mMinValueET.setText(String.valueOf(mNumberPicker.getMinValue()));
		mOnZeroCB.setChecked(mNumberPicker.isCheckbox_on_zero());
		mInlineStyleCB.setChecked(mNumberPicker.isInline_style());
		mLabelET.setText(mNumberPicker.getLabel());
		mPluralLabelET.setText(mNumberPicker.getLabel_plural());
		mCheckBoxFirstValueET.setText(String.valueOf(mNumberPicker.getCheckbox_first_value()));
		
		// initilize spiner
		mAlignTypeSP.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] {
						"HORISONTAL", "VERTICAL" }));
		if(mNumberPicker.getAlign() == AlignType.HORISONTAL){
			mAlignTypeSP.setSelection(0);
			
		} else{
			mAlignTypeSP.setSelection(1);
		}
	}

	private float getEditTextValue(EditText et){
		float value = 0;
		
		if(et != null){
			try {
				value = Float.parseFloat(et.getText().toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		
		return value;
	}
	
	private void setControlListeners() {
		mValueET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				mNumberPicker.setValue(getEditTextValue(mValueET), true);
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
		
		mMaxValueET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mNumberPicker.setMax_value(getEditTextValue(mMaxValueET));
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		mMinValueET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mNumberPicker.setMin_value(getEditTextValue(mMinValueET));
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		mLabelET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mNumberPicker.setLabel(mLabelET.getText().toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		mPluralLabelET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				mNumberPicker.setLabel_plural(mPluralLabelET.getText().toString());
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
			}
		});
		
		mStepsPatternET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				try {
					mNumberPicker.setStepsPattern(mStepsPatternET.getText().toString());
				} catch (StepsParseException e) {
					Toast.makeText(
							NumberPickerTestActivity.this,
							"Error while set steps parser \n " + e.getMessage(),
							Toast.LENGTH_LONG).show();
					
					e.printStackTrace();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}
			
			@Override
			public void afterTextChanged(Editable s) {
				
			}
		});
		
		mAlignTypeSP.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> adapter, View view,
					int position, long id) {
				
				switch (position) {
				case 0:
					mNumberPicker.setAlign(AlignType.HORISONTAL);
					break;

				case 1:
					mNumberPicker.setAlign(AlignType.VERTICAL);
					break;
					
				default:
					break;
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				
			}
			
		});
		
		mCheckBoxFirstValueET.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
				mNumberPicker.setCheckbox_first_value(getEditTextValue(mCheckBoxFirstValueET));
			}
			
			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			
			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});
		
		mOnZeroCB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mNumberPicker.setCheckbox_on_zero(isChecked);
			}
		});
		
		mInlineStyleCB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				mNumberPicker.setInline_style(isChecked);
			}
		});
	}

	private void getIds() {
		mUpdateLabelTV = (TextView) findViewById(R.id.tvOnValueChangedLabel);
		mValueET = (EditText) findViewById(R.id.edValue);
		mMaxValueET = (EditText) findViewById(R.id.edMaxValue);
		mMinValueET = (EditText) findViewById(R.id.edMinValue);
		mLabelET = (EditText) findViewById(R.id.edLabel); 
		mPluralLabelET = (EditText) findViewById(R.id.edPluralLabel);
		mStepsPatternET = (EditText) findViewById(R.id.edStepsPattern);
		mCheckBoxFirstValueET = (EditText) findViewById(R.id.edCheckBoxFirstValue);
		
		mOnZeroCB = (CheckBox) findViewById(R.id.chZeroValue);
		mInlineStyleCB = (CheckBox) findViewById(R.id.chInlineStyle);
		mAlignTypeSP = (Spinner) findViewById(R.id.spEaligmentType);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.widgets_test, menu);
		return true;
	}

}
