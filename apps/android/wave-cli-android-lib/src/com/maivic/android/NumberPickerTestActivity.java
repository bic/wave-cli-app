package com.maivic.android;

import java.math.BigDecimal;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.maivic.android.widget.NumberPicker;
import com.maivic.android.widget.NumberPicker.AlignType;
import com.maivic.android.widget.NumberPicker.OnValueChangedListener;
import com.maivic.android.widget.SimpleLayoutSetupHelper;
import com.maivic.android.widget.exception.StepsParseException;
import com.maivic.android.widgets.R;

/**
 * Activity to test NumberPicker widget
 */
@SuppressLint("DefaultLocale")
public class NumberPickerTestActivity extends Activity {
	private NumberPicker mNumberPicker;

	private CheckBox mOnZeroCB, mInlineStyleCB;

	private EditText mValueET, mMaxValueET, mMinValueET, mLabelET,
			mPluralLabelET, mCheckBoxFirstValueET, mStepsPatternET,
			mGroupSizeET, mGroupInlineSplitET, mButtonLabelsET;

	private Spinner mAlignTypeSP;

	private TextView mUpdateLabelTV;

	private Button mStepsPatternBTN;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
//		Intent intent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
//		Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
//		
//		intent.setType(ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE);
		
//		getContentResolver().query(ContactsContract.Contacts.CONTENT_URI, projection, selection, selectionArgs, sortOrder);
		
//		Intent intent = new Intent(Intent.ACTION_PICK,ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI);

		
//		intent.setType(ContactsContract.CommonDataKinds..CONTENT_TYPE);
//		intent.setType(ContactsContract.CommonDataKinds.Email.CONTENT_TYPE);
//		startActivity(intent);
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_widgets_test);
		mNumberPicker = (NumberPicker) findViewById(R.id.numberPixker1);
		mNumberPicker.setLayoutSetupHelper(new CustomisatedLayoutHelper(this));
		getIds();
		setupDefaultNumberPisker();
		initilizeControls();
		setControlListeners();
	}
	
	private void setupDefaultNumberPisker() {
		// for test
		mNumberPicker.setMax_value(new BigDecimal(10));
		mNumberPicker.setMin_value(new BigDecimal(-10));
		mNumberPicker.setLabel("Minute");
		mNumberPicker.setLabel_plural("Minutes");
		mNumberPicker.setCheckbox_first_value(new BigDecimal(1));
		mNumberPicker.setAlign(AlignType.HORIZONTAL);
		try {
			mNumberPicker.setStepsPattern("-1; +1");
		} catch (StepsParseException e) {
			e.printStackTrace();
		}

		// attach value changed listener
		mNumberPicker.setValueChangedListener(new OnValueChangedListener() {
			@Override
			public void onValueChanged(NumberPicker widget,
					BigDecimal oldValue, BigDecimal newValue) {
				String label = String.format(
						"onValueChanged oldValue: %.1f, newValue: %.1f",
						oldValue, newValue);
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
		mCheckBoxFirstValueET.setText(String.valueOf(mNumberPicker
				.getCheckbox_first_value()));
		mGroupSizeET.setText(String.valueOf(mNumberPicker.getGroup_size()));
		mGroupInlineSplitET.setText(String.valueOf(mNumberPicker
				.getGroup_inline_split()));
		mButtonLabelsET.setText(mNumberPicker.getButtonlabels());

		// initilize spiner
		mAlignTypeSP.setAdapter(new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, new String[] {
						"HORISONTAL", "VERTICAL" }));
		if (mNumberPicker.getAlign() == AlignType.HORIZONTAL) {
			mAlignTypeSP.setSelection(0);

		} else {
			mAlignTypeSP.setSelection(1);
		}
	}

	private BigDecimal getEditTextValue(EditText et) {
		BigDecimal value = new BigDecimal(0);

		if (et != null) {
			try {
				value = new BigDecimal(et.getText().toString());
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}

		return value;
	}

	private void setControlListeners() {
		mValueET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				mNumberPicker.setValue(getEditTextValue(mValueET), true);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});

		mMaxValueET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
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
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
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
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
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
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mNumberPicker.setLabel_plural(mPluralLabelET.getText()
						.toString());
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
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				try {
					mNumberPicker.setStepsPattern(mStepsPatternET.getText()
							.toString());
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

		mGroupSizeET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				int groupSize = getEditTextValue(mGroupSizeET).intValue();
				mNumberPicker.setGroup_size(groupSize > 0 ? groupSize : 1);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});

		mGroupInlineSplitET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				int groupInlineSplit = getEditTextValue(mGroupInlineSplitET)
						.intValue();
				mNumberPicker
						.setGroup_inline_split(groupInlineSplit > 0 ? groupInlineSplit
								: 1);
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
					mNumberPicker.setAlign(AlignType.HORIZONTAL);
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
			public void onTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
				mNumberPicker
						.setCheckbox_first_value(getEditTextValue(mCheckBoxFirstValueET));
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1,
					int arg2, int arg3) {
			}

			@Override
			public void afterTextChanged(Editable arg0) {
			}
		});

		mButtonLabelsET.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				mNumberPicker.setButtonlabels(mButtonLabelsET.getText()
						.toString());
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		mOnZeroCB.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				mNumberPicker.setCheckbox_on_zero(isChecked);
			}
		});

		mInlineStyleCB
				.setOnCheckedChangeListener(new OnCheckedChangeListener() {
					@Override
					public void onCheckedChanged(CompoundButton buttonView,
							boolean isChecked) {
						mNumberPicker.setInline_style(isChecked);
					}
				});

		mStepsPatternBTN.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				final String[] items = new String[] { "-1; +1",
						"-5; +5; -1; +1", "-10; -5; +5; +10",

				};
				AlertDialog.Builder builder = new Builder(
						NumberPickerTestActivity.this);
				builder.setTitle("Select a predefined steps pattern")
						.setItems(items, new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								mStepsPatternET.setText(items[which]);
								dialog.dismiss();
							}
						}).show();
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
		mGroupSizeET = (EditText) findViewById(R.id.edGroupSize);
		mGroupInlineSplitET = (EditText) findViewById(R.id.edGroupIndlineSplit);
		mButtonLabelsET = (EditText) findViewById(R.id.edButtonLabel);

		mOnZeroCB = (CheckBox) findViewById(R.id.chZeroValue);
		mInlineStyleCB = (CheckBox) findViewById(R.id.chInlineStyle);
		mAlignTypeSP = (Spinner) findViewById(R.id.spEaligmentType);
		mStepsPatternBTN = (Button) findViewById(R.id.btnPredefinedSteps);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.widgets_test, menu);
		return true;
	}

	private class CustomisatedLayoutHelper extends SimpleLayoutSetupHelper {

		public CustomisatedLayoutHelper(Context context) {
			super(context);
		}

		@Override
		protected void decorateActionButton(View buttonView, int buttonPosition) {
			super.decorateActionButton(buttonView, buttonPosition);

			// BigDecimal [] steps = mNumberPicker.getStepsValue();
			// BigDecimal step = steps[buttonPosition];
			// if(step.signum() > 0){
			// buttonView.setBackgroundColor(Color.BLACK);
			// }
			//
			// if(step.signum() < 0){
			// buttonView.setBackgroundColor(Color.GREEN);
			// }
		}

		protected void onButtonDown(View buttonView) {
			// buttonsContainer.setBackgroundColor(Color.RED);
			// txtLabelView.setTextColor(Color.GREEN);
			txtLabelView.setSelected(true);
		}

		protected void onButtonUp(View buttonView) {
			// buttonsContainer.setBackgroundDrawable(null);
			// txtLabelView.setTextColor(Color.BLACK);
			txtLabelView.setSelected(false);
		}

	}

}
