package com.maivic.android.widget;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;
import android.widget.RelativeLayout;

import com.maivic.android.utils.Utils;
import com.maivic.android.widget.exception.StepsParseException;
import com.maivic.android.widgets.R;

/**
 * NumberPicker widget. This is implementation of specification:
 * https://github.com/bic/wave-cli-app/issues/2
 */
public class NumberPicker extends RelativeLayout implements
		LayoutSetupHelper.ActionButtonClickCallback {

	private static final String T = "NumberPicker";

	private static final BigDecimal zero = new BigDecimal(0);

	private static final BigDecimal one = new BigDecimal(1);

	private static final BigDecimal minusOne = new BigDecimal(-1);

	protected OnValueChangedListener mValueChangedListener;

	/**
	 * Layout helper, used for creating layout
	 */
	protected LayoutSetupHelper mLayoutSetupHelper;

	/*
	 * variable marking the number of buttons grouped together
	 */
	private int group_size = 2;

	/*
	 * variable marking the button before which the selected number with the
	 * label is displayed
	 */
	private int group_inline_split = 1;

	/*
	 * Steps of in/decrements of the pickers value. The value consists of signed
	 * numbers delimited by ";" (example "-1;1").Each number is put on a button.
	 * The values contained in the steps property value must be a multiple of
	 * group_size
	 */
	private String steps_pattern = "-1; +1";

	private BigDecimal[] mUpdateSteps = null;

	/*
	 * The button label is a substitution pattern string where "%%" is
	 * substituted for each value contained in steps.
	 */
	private String buttonlabels;

	/*
	 * The label of a TextView containing the text to be displayed next to the
	 * number picker's value. The label is replaced by label_plural when the
	 * number is not -1,0 or 1
	 */
	private String label;

	/*
	 * The value is used instead of label as number picker label for values
	 * other than -1,0,1
	 */
	private String label_plural;

	/*
	 * one of [_vertical_,_horizontal_] sets the visual allignment of each
	 * element within a group. Vertical groups of buttons are joind together by
	 * the leftmost button rounded to the left, the rightmost to the right. The
	 * buttons in the middle are rectangle-shaped.
	 */
	private AlignType align = AlignType.HORIZONTAL;

	/*
	 * (Default = not set = False) if this property is set the selected number
	 * is displayed inbetween group buttons. The position is set in
	 * group_inline_split. The Label is displayed: right of the selected number
	 * if allign = vertical and inline_style= True below the selected number if
	 * allign = horizontal and inline_style= True
	 */
	private boolean inline_style = false;

	/*
	 * an (unchecked) checkbox is used in case the value is 0
	 */
	private boolean checkbox_on_zero = false;

	/*
	 * the value to adopt when the unchecked checkbox is checked
	 */
	private BigDecimal checkbox_first_value = new BigDecimal(1);

	/*
	 * When value is already on a limit value (__min__ or max) next to 0 a
	 * button is till active to change the value to 0 (use with
	 * checkbox_on_zero)
	 */
	private boolean force_accept_zero = false;

	/*
	 * Max allowed value
	 */
	private BigDecimal max_value = new BigDecimal(Float.MAX_VALUE);

	/*
	 * Min allowed value
	 */
	private BigDecimal min_value = new BigDecimal(-Float.MAX_VALUE);

	/*
	 * Current number picker value
	 */
	private BigDecimal value = zero;

	/*
	 * Reference value
	 */
	private BigDecimal reference = new BigDecimal(0);

	public NumberPicker(Context context) {
		super(context);
		initializeInternal();
		
		setupWidget();
	}
	
	protected void initializeInternal(){
		setLayoutSetupHelper(new SimpleLayoutSetupHelper(getContext()));
	}

	public NumberPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initlizeFormAttrs(attrs);
		initializeInternal();
		setupWidget();
	}

	public NumberPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		initlizeFormAttrs(attrs);
		initializeInternal();
		setupWidget();
	}

	private void initlizeFormAttrs(AttributeSet attrs) {
		TypedArray a = getContext().getTheme().obtainStyledAttributes(attrs,
				R.styleable.NumberPicker, 0, 0);

		try {
			group_size = a.getInt(R.styleable.NumberPicker_group_size, 2);
			group_inline_split = a.getInt(R.styleable.NumberPicker_group_inline_split, 1);

			steps_pattern = a.getString(R.styleable.NumberPicker_steps);
			if (steps_pattern == null)
				steps_pattern = "-1; +1";

			buttonlabels = a.getString(R.styleable.NumberPicker_buttonlabels);
			
			label = a.getString(R.styleable.NumberPicker_label);
			label_plural = a.getString(R.styleable.NumberPicker_label_plural);
			
			if(a.getInt(R.styleable.NumberPicker_align, 0) == 0){
				align = AlignType.HORIZONTAL;
			} else{
				align = AlignType.VERTICAL;	
			}

			inline_style = a.getBoolean(R.styleable.NumberPicker_inline_style, false);

			checkbox_on_zero = a.getBoolean(R.styleable.NumberPicker_checkbox_on_zero, false);

			float firstValue = a.getFloat(R.styleable.NumberPicker_checkbox_first_value, 1);
			checkbox_first_value = new BigDecimal(firstValue);
			
			force_accept_zero = a.getBoolean(R.styleable.NumberPicker_force_accept_zero, false);
			
			float maxValue = a.getFloat(R.styleable.NumberPicker_max, Float.MAX_VALUE);
			max_value = new BigDecimal(maxValue);

			float minValue = a.getFloat(R.styleable.NumberPicker_min, -Float.MAX_VALUE);
			min_value = new BigDecimal(minValue);
			
			float val = a.getFloat(R.styleable.NumberPicker_value, 0);
			value = new BigDecimal(val);
			
		} finally {
			a.recycle();
		}
	}

	public OnValueChangedListener getValueChangedListener() {
		return mValueChangedListener;
	}

	public void setValueChangedListener(
			OnValueChangedListener mValueChangedListener) {
		this.mValueChangedListener = mValueChangedListener;
	}

	public BigDecimal getMaxValue() {
		return max_value;
	}

	public void setMax_value(BigDecimal max_value) {
		this.max_value = max_value;
	}

	public LayoutSetupHelper getLayoutSetupHelper() {
		return mLayoutSetupHelper;
	}

	public void setLayoutSetupHelper(LayoutSetupHelper layoutSetupHelper) {
		if (layoutSetupHelper != null) {
			this.mLayoutSetupHelper = layoutSetupHelper;
			this.mLayoutSetupHelper.setNumberPicker(this);
			setupWidget();
		}
	}

	public BigDecimal getMinValue() {
		return min_value;
	}

	public void setMin_value(BigDecimal min_value) {
		this.min_value = min_value;
	}

	public int getGroup_size() {
		return group_size;
	}

	public int getGroup_inline_split() {
		return group_inline_split;
	}

	public void setGroup_inline_split(int group_inline_split) {
		this.group_inline_split = group_inline_split;
		setupWidget();
	}

	public void setGroup_size(int group_size) {
		if (group_size == 0)
			throw new IllegalStateException(
					"The group_size value must be more 0");

		this.group_size = group_size;
		setupWidget();
	}

	public String getButtonlabels() {
		return buttonlabels;
	}

	public void setButtonlabels(String buttonlabels) {
		this.buttonlabels = buttonlabels;
		setupWidget();
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
		updateGuiState();
	}

	public String getLabel_plural() {
		return label_plural;
	}

	public void setLabel_plural(String label_plural) {
		this.label_plural = label_plural;

		updateGuiState();
	}

	public AlignType getAlign() {
		return align;
	}

	public void setAlign(AlignType align) {
		if (this.align != align) {
			this.align = align;
			setupWidget();
		}
	}

	public boolean isInline_style() {
		return inline_style;
	}

	public void setInline_style(boolean inline_style) {
		if (this.inline_style != inline_style) {
			this.inline_style = inline_style;
			setupWidget();
		}
	}

	public boolean isCheckbox_on_zero() {
		return checkbox_on_zero;
	}

	public void setCheckbox_on_zero(boolean checkbox_on_zero) {
		this.checkbox_on_zero = checkbox_on_zero;
		updateGuiState();
	}

	public BigDecimal getCheckbox_first_value() {
		return checkbox_first_value;
	}

	public void setCheckbox_first_value(BigDecimal checkbox_first_value) {
		this.checkbox_first_value = checkbox_first_value;
	}

	public boolean isForce_accept_zero() {
		return force_accept_zero;
	}

	public void setForce_accept_zero(boolean force_accept_zero) {
		this.force_accept_zero = force_accept_zero;
	}

	protected LayoutSetupHelper getDefaultLayoutHelper() {
		SimpleLayoutSetupHelper layoutHelper = new SimpleLayoutSetupHelper(
				getContext());
		layoutHelper.setNumberPicker(this);
		return layoutHelper;
	}

	// private LayoutSetupHelperOLD getLayoutSetupHelper(){
	// LayoutSetupHelperOLD result = null;
	// switch (align) {
	// case HORIZONTAL:
	// result = new HorisontalLayoutSetupHelper(this);
	// break;
	// case VERTICAL:
	// result = new VerticalLayoutSetupHelper(this);
	// break;
	//
	// default:
	// Log.w(T, "can't determine layouAligner for align: "+align
	// +" use HorisontalLayoutSetupHelper");
	// result = new HorisontalLayoutSetupHelper(this);
	// }
	//
	// result = new MyLayoutSetupHelper(this);
	//
	// return result;
	// }

	protected void setupWidget() {
		removeAllViews();

		if (mLayoutSetupHelper == null) {
			mLayoutSetupHelper = getDefaultLayoutHelper();
		}

		if (true || !isInEditMode()) {
			ViewGroup content = mLayoutSetupHelper.createContentView();
//			RelativeLayout.LayoutParams params = new LayoutParams(
//					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			addView(content/*, params*/);
			
		} else{
//			TextView tv = new TextView(getContext());
//			tv.setText("HHH");
//			addView(tv);
			setBackgroundColor(Color.GRAY);
		}

		if (true || !isInEditMode())
			updateGuiState();
	}

	/**
	 * Call this method to get number picker value
	 * 
	 * @return value
	 */
	public BigDecimal getValue() {
		return value;
	}

	/**
	 * Set number picker value
	 * 
	 * @param newValue
	 *            number picker value
	 * @param fireNotification
	 *            notify listener
	 */
	public synchronized void setValue(BigDecimal newValue,
			boolean fireNotification) {
		
		Log.d(T, "setValue "+newValue.floatValue());
		
		boolean valueUpdatedFlag = false;
		final BigDecimal oldValue = value;

		if (newValue.compareTo(value) != 0) {
			int cmp_new_max = newValue.compareTo(max_value);
			int cmp_new_min = newValue.compareTo(min_value);

			if (cmp_new_max <= 0 && cmp_new_min >= 0) {
				// newValue <= max_value && newValue >= minValue
				// then set value = newValue,
				// and disable correspond buttons if need

				value = newValue;
				enableDisableActionButtons(true, cmp_new_max != 0);
				enableDisableActionButtons(false, cmp_new_min != 0);

			} else if (cmp_new_min < 0) {
				// newValue < min_value
				// disable decrement buttons
				value = min_value;
				enableDisableActionButtons(false, false);

			} else if (cmp_new_max > 0) {
				// newValue > max_value
				// disable increment buttons
				value = max_value;
				enableDisableActionButtons(true, false);
			}

			valueUpdatedFlag = true;

			updateGuiState();
		}

		// if(newValue != value){
		//
		// if(newValue <= max_value && newValue >= min_value){
		// // enable all action buttons(increment, decrement)
		// value = newValue;
		// enableDisableActionButtons(true, newValue != max_value);
		// enableDisableActionButtons(false, newValue != min_value);
		//
		// } else if((newValue < min_value)){
		//
		// value = min_value;
		// enableDisableActionButtons(false, false);
		//
		// } else if(newValue > max_value){
		//
		// value = max_value;
		// enableDisableActionButtons(true, false);
		// }
		//
		// valueUpdatedFlag = true;
		//
		// updateGuiState();
		// }

		if (valueUpdatedFlag && fireNotification
				&& mValueChangedListener != null) {
			mValueChangedListener.onValueChanged(this, oldValue, value);
		}
	}

	public void setStepsPattern(String stepsPattern) throws StepsParseException {
		this.steps_pattern = stepsPattern;
		mUpdateSteps = null;

		setupWidget();
		// parsePatternSteps();
		// setupActionButtons();
	}

	@Override
	public void onZerroCheckboxClick(Checkable buttonView, boolean checkedValue) {
		if (checkedValue) {
			setValue(checkbox_first_value, true);
		} else {
			setValue(new BigDecimal(0), true);
		}
	}

	@Override
	public void onActionButtonClick(View buttonView, BigDecimal stepValue) {
		if (stepValue != null) {
			BigDecimal nextValue = Utils.next(value, stepValue, reference,
					min_value, max_value);
			setValue(nextValue, true);
			// doValueUpdateAction(value, stepValue);
		}
	}

	private void parsePatternSteps() throws StepsParseException {
		if (steps_pattern != null) {
			String[] steps = steps_pattern.split(";");

			if (steps.length > 0) {

				mUpdateSteps = new BigDecimal[steps.length];
				for (int i = 0; i < steps.length; i++) {
					String step = steps[i];
					try {
						mUpdateSteps[i] = new BigDecimal(step.trim());
					} catch (NumberFormatException e) {
						e.printStackTrace();
						mUpdateSteps = null;
						throw new StepsParseException(
								"Can't parse float value for " + step
										+ " of pattern " + steps_pattern);
					}
				}

			} else {
				throw new StepsParseException("Can't parse steps pattern "
						+ steps_pattern + ", you are sure that use ");
			}
		}
	}

	/**
	 * Call this method, to get steps value for every button
	 * 
	 * @return array of {@link BigDecimal}
	 * @throws StepsParseException
	 *             if some errors occurs during parsing
	 */
	public BigDecimal[] getStepsValue() throws StepsParseException {

		if (mUpdateSteps == null) {
			parsePatternSteps();
		}

		return mUpdateSteps;
	}

	void enableDisableActionButtons(boolean applyForIncrementButtons,
			boolean enabled) {
		List<View> actionButtons = mLayoutSetupHelper.getActionButtons();

		int buttonIndex = 0;
		for (View ab : actionButtons) {
			BigDecimal deltaValue = mLayoutSetupHelper
					.getStepValueForActoinButton(ab);
			if (deltaValue != null) {
				if (deltaValue.signum() < 0 && !applyForIncrementButtons) {
					// update decrement button state
					mLayoutSetupHelper.setButtonEnabledState(ab, buttonIndex,
							enabled);

				} else if (deltaValue.signum() > 0 && applyForIncrementButtons) {
					// update increment button state
					mLayoutSetupHelper.setButtonEnabledState(ab, buttonIndex,
							enabled);
				}
			}
			buttonIndex++;
		}
	}

	// call this method to render current NumberPicker value state.
	protected void updateGuiState() {

		if (checkbox_on_zero
		// && (value == 0 || (value == 1 && min_value == 0 && max_value == 1)))
		// {
				&& (value.equals(zero) || (value.equals(one)
						&& min_value.equals(zero) && max_value.equals(one)))) {

			// show zero checkbox button
			mLayoutSetupHelper.showZeroCheckbox();
			mLayoutSetupHelper.setLabelValue(label, "");
			mLayoutSetupHelper
					.setZeroCheckboxChecked(value.compareTo(one) == 0);

		} else {
			mLayoutSetupHelper.showActionButtons();

			if (value.equals(one) || value.equals(minusOne)) {
				mLayoutSetupHelper.setLabelValue(label, String.valueOf(value));

			} else {
				if (label_plural != null)
					mLayoutSetupHelper.setLabelValue(label_plural,
							String.valueOf(value));
				else
					mLayoutSetupHelper.setLabelValue(label,
							String.valueOf(value));

			}
		}

	}

	/**
	 * <p>
	 * Interface definition for a callback to be invoked when the value changed.
	 * </p>
	 */
	public static interface OnValueChangedListener {
		public void onValueChanged(NumberPicker widget, BigDecimal oldValue,
				BigDecimal newValue);
		// public boolean allowUpdateValue(NumberPicker widget, float oldValue,
		// float newValue);

	}

	public static enum AlignType {
		VERTICAL, HORIZONTAL
	}

	// /**
	// * Base setup helper, used to setup buttons in layout depend of align
	// type,
	// * and group_count.
	// */
	// static abstract class LayoutSetupHelperOLD{
	// protected List<View> mActionButtons;
	//
	// protected NumberPicker mNumberPicker;
	//
	// protected CheckBox mZeroCheckbox;
	//
	// protected Context mContext;
	//
	// /**
	// * Layout helper constructor
	// *
	// * @param np
	// * the instance of {@link NumberPicker}
	// */
	// public LayoutSetupHelperOLD(NumberPicker np){
	// mNumberPicker = np;
	// mContext = mNumberPicker.getContext();
	// }
	//
	// /**
	// * Call this method to release helper.
	// */
	// public void releaseLayout(){
	// mActionButtons = null;
	// mNumberPicker = null;
	// mContext = null;
	// }
	//
	// public List<View> getActionButtons(){
	// return mActionButtons;
	// }
	//
	// public CheckBox getZeroCheckbox(){
	// return mZeroCheckbox;
	// }
	//
	// /**
	// * This method setup layout, and assign touch listeners
	// */
	// public void setupLayout(){
	// setupLayoutInternal();
	//
	// // attach touch listeners
	// if(!mNumberPicker.isInEditMode())
	// if(mActionButtons != null){
	// for(View ab: mActionButtons){
	// ab.setOnTouchListener(mOnTouchListener);
	// }
	// }
	// }
	//
	// /**
	// * Implement this method, to setup layout
	// */
	// protected abstract void setupLayoutInternal();
	//
	// /**
	// * This method must called from {@link NumberPicker} to show action
	// * buttons.
	// */
	// public abstract void showActionButtons();
	//
	// /**
	// * This method must called from {@link NumberPicker} to show check box,
	// * for zero value.
	// */
	// public abstract void showZeroCheckbox();
	//
	// /**
	// * Override this method, to show visual effects, when action button down
	// *
	// * @param button
	// * the action button
	// */
	// protected void handleActionButtonDown(View button){}
	//
	// /**
	// * Override this method, to show visual effects, when action button up
	// *
	// * @param button
	// * the action button
	// *
	// * @see {@link handleActionButtonUp}
	// */
	// protected void handleActionButtonUp(View button){}
	//
	// /**
	// * An touch listener, that will attach to all action buttons, to inform
	// * layout about button reaction(down, up), to do some visual effects
	// */
	// private OnTouchListener mOnTouchListener = new OnTouchListener() {
	//
	// @Override
	// public boolean onTouch(View v, MotionEvent event) {
	// int action = event.getAction();
	// switch (action) {
	//
	// case MotionEvent.ACTION_DOWN:
	// handleActionButtonDown(v);
	// break;
	//
	// case MotionEvent.ACTION_UP:
	// handleActionButtonUp(v);
	// break;
	//
	// default:
	// break;
	// }
	//
	// return false;
	// }
	// };
	//
	// }
	//
	// static class HorisontalLayoutSetupHelper extends LayoutSetupHelperOLD{
	// View mButtonsContainer;
	//
	// public HorisontalLayoutSetupHelper(NumberPicker np) {
	// super(np);
	// }
	//
	// @Override
	// public void setupLayoutInternal() {
	// mActionButtons = new ArrayList<View>();
	// LayoutInflater inflater = (LayoutInflater)
	// mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// inflater.inflate(R.layout.widget_number_picker_horizontal, mNumberPicker,
	// true);
	//
	// mButtonsContainer =
	// mNumberPicker.findViewById(R.id.actionButtonsContainer);
	// mZeroCheckbox = (CheckBox)
	// mNumberPicker.findViewById(R.id.cbxZeroValued);
	//
	// // mNumberPicker.mLabelTextView = (TextView)
	// mNumberPicker.findViewById(R.id.tvLabel);
	// // mNumberPicker.mValueTextView = (TextView)
	// mNumberPicker.findViewById(R.id.tvValue);
	//
	// //TODO implement dynamic adding buttons depended of steps
	// mActionButtons.add(mNumberPicker.findViewById(R.id.btnDecremental));
	// mActionButtons.add(mNumberPicker.findViewById(R.id.btnIncremental));
	//
	// // mNumberPicker.mLabelTextView.setVisibility(View.GONE);
	// }
	//
	// @Override
	// public void showActionButtons() {
	// mZeroCheckbox.setVisibility(View.GONE);
	// mButtonsContainer.setVisibility(View.VISIBLE);
	// }
	//
	// @Override
	// public void showZeroCheckbox() {
	// mZeroCheckbox.setVisibility(View.VISIBLE);
	// mButtonsContainer.setVisibility(View.GONE);
	// }
	//
	// @Override
	// protected void handleActionButtonDown(View button) {
	// super.handleActionButtonDown(button);
	// mButtonsContainer.setBackgroundColor(Color.RED);
	// }
	//
	// @Override
	// protected void handleActionButtonUp(View button) {
	// super.handleActionButtonUp(button);
	// mButtonsContainer.setBackgroundDrawable(null);
	// }
	// }
	//
	// static class VerticalLayoutSetupHelper extends LayoutSetupHelperOLD{
	//
	// private View mButtonsContainer;
	//
	// public VerticalLayoutSetupHelper(NumberPicker np) {
	// super(np);
	// }
	//
	// @Override
	// public void setupLayoutInternal() {
	// mActionButtons = new ArrayList<View>();
	// LayoutInflater layoutInflater = (LayoutInflater)
	// mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	// layoutInflater.inflate(R.layout.widget_number_picker_vertical,
	// mNumberPicker, true);
	//
	// mButtonsContainer =
	// mNumberPicker.findViewById(R.id.wcl_ActionButtonsContainer);
	// mZeroCheckbox = (CheckBox)
	// mNumberPicker.findViewById(R.id.wcl_cbZeroCheckbox);
	//
	// // mNumberPicker.mLabelTextView = (TextView)
	// mNumberPicker.findViewById(R.id.wcl_tvNumberLabel);
	// // mNumberPicker.mValueTextView = (TextView)
	// mNumberPicker.findViewById(R.id.wcl_tvNumberValue);
	//
	// //TODO implement dynamic adding buttons depended of steps
	// mActionButtons.add(mNumberPicker.findViewById(R.id.wcl_btnDecremetn));
	// mActionButtons.add(mNumberPicker.findViewById(R.id.wcl_btnIncrement));
	// }
	//
	// @Override
	// public void showActionButtons() {
	// mButtonsContainer.setVisibility(View.VISIBLE);
	// mZeroCheckbox.setVisibility(View.GONE);
	// }
	//
	// @Override
	// public void showZeroCheckbox() {
	// mButtonsContainer.setVisibility(View.GONE);
	// mZeroCheckbox.setVisibility(View.VISIBLE);
	// }
	//
	// @Override
	// protected void handleActionButtonDown(View button) {
	// super.handleActionButtonDown(button);
	// // mButtonsContainer.setBackgroundColor(Color.RED);
	// }
	//
	// @Override
	// protected void handleActionButtonUp(View button) {
	// super.handleActionButtonUp(button);
	// // mButtonsContainer.setBackgroundColor(Color.WHITE);
	// }
	// }
	//
	// static class MyLayoutSetupHelper extends LayoutSetupHelperOLD{
	//
	// // private static final int ID_ACTION_BUTTON_BASE = 1;
	// private static final int ID_TV_LABEL = 1;
	// private static final int ID_TV_VALUE = 2;
	// private static final int ID_LABEL_CONTAINER = 3;
	// // private static final int
	//
	// public MyLayoutSetupHelper(NumberPicker np) {
	// super(np);
	// }
	//
	// private RelativeLayout createLabelContainer(){
	// RelativeLayout labelLayout = new RelativeLayout(mContext);
	// TextView tvLabel = new TextView(mContext);
	// TextView tvValue = new TextView(mContext);
	//
	// tvLabel.setId(ID_TV_LABEL);
	// tvValue.setId(ID_TV_VALUE);
	//
	// RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
	// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
	// lp.addRule(RelativeLayout.CENTER_VERTICAL);
	// labelLayout.addView(tvLabel, lp);
	//
	// lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
	// LayoutParams.WRAP_CONTENT);
	// lp.addRule(RelativeLayout.RIGHT_OF, ID_TV_LABEL);
	// lp.addRule(RelativeLayout.CENTER_VERTICAL);
	//
	// labelLayout.addView(tvValue, lp);
	// labelLayout.setId(ID_LABEL_CONTAINER);
	// return labelLayout;
	// }
	//
	// /**
	// *
	// * @param orientation
	// * @return
	// */
	// protected LinearLayout createButtonGroup(int orientation){
	// LinearLayout result = new LinearLayout(mContext);
	// result.setOrientation(orientation);
	// result.setBackgroundColor(Color.DKGRAY);
	// return result;
	// }
	//
	// @Override
	// protected void setupLayoutInternal() {
	// LinearLayout container = new LinearLayout(mContext);
	// LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
	// LinearLayout.LayoutParams.WRAP_CONTENT,
	// LinearLayout.LayoutParams.WRAP_CONTENT, 0);
	// container.setOrientation(LinearLayout.HORIZONTAL);
	// // container.addView(child, params);
	//
	// // RelativeLayout.LayoutParams p = new LayoutParams;
	// // p.
	// ViewGroup labelContainer = createLabelContainer();
	//
	// if(false && mNumberPicker.isInline_style()){
	// // inline style
	// int groupSize = mNumberPicker.getGroup_size();
	// int inlineSplit = mNumberPicker.getGroup_inline_split();
	// } else{
	// // not inline style
	// // the label layout align left, button container right
	// LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
	// LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
	// lp.gravity = LinearLayout.VERTICAL;
	// container.addView(labelContainer, lp);
	//
	// // generate button container
	// LinearLayout buttonsContainer = new LinearLayout(mContext);
	// int orientation = mNumberPicker.getAlign() == AlignType.HORIZONTAL ?
	// LinearLayout.HORIZONTAL
	// : LinearLayout.VERTICAL;
	// buttonsContainer.setOrientation(orientation);
	//
	// LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
	// LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1);
	//
	// mActionButtons = new ArrayList<View>();
	//
	// List<LinearLayout> buttonGroups = new ArrayList<LinearLayout>();
	//
	// int totalButtons = 5;
	// LinearLayout group = null;
	// for(int i = 0; i < totalButtons; i++){
	//
	// if(i % mNumberPicker.getGroup_size() == 0){
	// group = createButtonGroup(orientation);
	// buttonGroups.add(group);
	// }
	//
	// Button button = new Button(mContext);
	// button.setTag(1);
	// button.setText("Button" + i);
	// mActionButtons.add(button);
	// group.addView(button, lp2);
	// }
	//
	// buttonsContainer
	// .setOrientation(mNumberPicker.getAlign() == AlignType.HORIZONTAL ?
	// LinearLayout.VERTICAL
	// : LinearLayout.HORIZONTAL);
	//
	// for(int i = 0; i < buttonGroups.size(); i++){
	// lp2.rightMargin = lp2.leftMargin = lp2.topMargin = lp2.bottomMargin = 5;
	// buttonsContainer.addView(buttonGroups.get(i), lp2);
	// }
	//
	// container.addView(buttonsContainer, lp);
	// }
	//
	//
	// mNumberPicker.addView(container);
	// }
	//
	// @Override
	// public void showActionButtons() {
	//
	// }
	//
	// @Override
	// public void showZeroCheckbox() {
	// }
	// }
}
