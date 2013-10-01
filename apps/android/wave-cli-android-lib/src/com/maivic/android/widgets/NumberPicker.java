package com.maivic.android.widgets;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Implementation of widget for issue https://github.com/bic/wave-cli-app/issues/2
 * 
 * Note this is first version, and it support only 
 * 2 change buttons: increment/decrement
 */
public class NumberPicker extends RelativeLayout{

	private static final String T = "NumberPicker";
	
	// gui widgets
	// buttons / checkbox / textview 
	
//	protected CheckBox mZeroCheckBox;
	
	// an TextView to display current value
	protected TextView mValueTextView;
	
	// TextView to display label
	protected TextView mLabelTextView;
	
	protected OnValueChangedListener mValueChangedListener;
	
	protected LayoutSetupHelper mLayoutSetupHelper;

	/*
	 * variable marking the number of buttons grouped together
	 */
	private int group_size;
	
//	/*
//	 * variable marking the button before which the selected number with the
//	 * label is displayed
//	 */
//	private group_inline_splil;
	
	/*
	 * Steps of in/decrements of the pickers value. The value consists of signed
	 * numbers delimited by ";" (example "-1;1").Each number is put on a button.
	 * The values contained in the steps property value must be a multiple of
	 * group_size
	 */
	private String steps_pattern = "-1; +1";
	
	private float [] mUpdateSteps = null;
	
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
	private AlignType align = AlignType.HORISONTAL;
	
	/*
	 * (Default = not set = False) if this property is set the selected number
	 * is displayed inbetween group buttons. The position is set in
	 * group_inline_split. The Label is displayed: 
	 *   right of the selected number if allign = vertical and inline_style= True 
	 *   below the selected number if allign = horizontal and inline_style= True
	 */
	private boolean inline_style = false;
	
	/*
	 * an (unchecked) checkbox is used in case the value is 0
	 */
	private boolean checkbox_on_zero = false;
	
	/*
	 * the value to adopt when the unchecked checkbox is checked
	 */
	private float checkbox_first_value = 1;
	
	/*
	 * When value is already on a limit value (__min__ or max) next to 0 a
	 * button is till active to change the value to 0 (use with
	 * checkbox_on_zero)
	 */
	private boolean force_accept_zero = false;	
	
	/*
	 * Max allowed value
	 */
	private float max_value = Float.MAX_VALUE;
	
	/*
	 * Min allowed value
	 */
	private float min_value = -Float.MAX_VALUE;
	
	/*
	 * Current number picker value
	 */
	private float value;
	
	public NumberPicker(Context context) {
		super(context);
		setupWidget();
	}

	public NumberPicker(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setupWidget();
	}

	public NumberPicker(Context context, AttributeSet attrs) {
		super(context, attrs);
		setupWidget();
	}
	
	public OnValueChangedListener getmValueChangedListener() {
		return mValueChangedListener;
	}

	public void setmValueChangedListener(
			OnValueChangedListener mValueChangedListener) {
		this.mValueChangedListener = mValueChangedListener;
	}

	public float getMaxValue() {
		return max_value;
	}

	public void setMax_value(float max_value) {
		this.max_value = max_value;
		// TODO update
	}

	public float getMinValue() {
		return min_value;
	}

	public void setMin_value(float min_value) {
		this.min_value = min_value;
		// TODO update 
	}

	public int getGroup_size() {
		return group_size;
	}

	public void setGroup_size(int group_size) {
		this.group_size = group_size;
		setupWidget();
	}

	public String getButtonlabels() {
		return buttonlabels;
	}

	public void setButtonlabels(String buttonlabels) {
		this.buttonlabels = buttonlabels;
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
		if(this.align != align){
			this.align = align;
			setupWidget();			
		}
	}

	public boolean isInline_style() {
		return inline_style;
	}

	public void setInline_style(boolean inline_style) {
		if(this.inline_style != inline_style){
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

	public float getCheckbox_first_value() {
		return checkbox_first_value;
	}

	public void setCheckbox_first_value(float checkbox_first_value) {
		this.checkbox_first_value = checkbox_first_value;
	}

	public boolean isForce_accept_zero() {
		return force_accept_zero;
	}

	public void setForce_accept_zero(boolean force_accept_zero) {
		this.force_accept_zero = force_accept_zero;
	}
	
	private LayoutSetupHelper getLayoutSetupHelper(){
		LayoutSetupHelper result = null; 
		switch (align) {
		case HORISONTAL:
			result =  new HorisontalLayoutSetupHelper(this);
			break;
		case VERTICAL:
			result = new VerticalLayoutSetupHelper(this);
			break;

		default:
			Log.w(T, "can't determine layouAligner for align: "+align +" use HorisontalLayoutSetupHelper");
			result = new HorisontalLayoutSetupHelper(this);
		}
		
		return result;
	}

	protected void setupWidget(){
		removeAllViews();
		
		mLayoutSetupHelper = getLayoutSetupHelper();
		
		if(mLayoutSetupHelper != null){
			mLayoutSetupHelper.setupLayout();
			
			if(!isInEditMode())
				setupActionButtons();
			
			if(!isInEditMode())
				updateGuiState();
		} else{
			Log.w(T, "the layout setup helper is null!!!");
		}
	}
	
	/*
	 * This method must called, to attach onclick listeners for action buttons and zerocheckbox button
	 */
	private void setupActionButtons(){
		if(mLayoutSetupHelper != null){

			final List<View> actionButtons = mLayoutSetupHelper.getActionButtons();
			if(actionButtons  != null){
				int pos = 0;
				float [] updateSteps = getStepsValue();				
				for(View ab: actionButtons){
					ab.setOnClickListener(mOnActionButtonClickListener);
					
					if (updateSteps != null && pos < updateSteps.length) {
						ab.setTag(updateSteps[pos++]);
					}
				}
			} else{
				Log.w(T, "action buttons is null");
			}
			
			CheckBox zeroCheckboxButton = mLayoutSetupHelper.getZeroCheckbox();
			if(zeroCheckboxButton != null){
				zeroCheckboxButton.setOnCheckedChangeListener(mOnZeroCheckboxListener);
			}
			
		} else{
			Log.w(T, "Can't set listeners, the layoutSetupHelper is null. Do is called method setupWidget ?");
		}
	}
	
	/**
	 * Call this method to get number picker value
	 * 
	 * @return value
	 */
	public float getValue(){
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
	public synchronized void setValue(float newValue, boolean fireNotification){
		boolean valueUpdatedFlag = false;
		
		final float oldValue = value;
		
		if(newValue != oldValue){
			
			if(newValue <= max_value && newValue >= min_value){
				// enable all action buttons(increment, decrement)
				value = newValue;
				enableDisableActionButtons(true,  newValue != max_value);
				enableDisableActionButtons(false, newValue != min_value);
				
			} else if((newValue < min_value)){
				//TODO specification logic
				
				newValue = min_value;
				enableDisableActionButtons(false, false);
				
			} else if(newValue > max_value){
				//TODO specification logic
				
				value = max_value;
				enableDisableActionButtons(true, false);
			}
			
			valueUpdatedFlag = true;
			
			updateGuiState();
		}
		
		if (valueUpdatedFlag && fireNotification
				&& mValueChangedListener != null) {
			mValueChangedListener.onValueChanged(this, oldValue, value);
		}
	}
	
	public void setStepsPattern(String stepsPattern ) throws StepsParseException{
		this.steps_pattern = stepsPattern;
		parsePatternSteps();
		setupActionButtons();
	}
	
	private void parsePatternSteps() throws StepsParseException{
		if(steps_pattern != null){
			
			String [] steps = steps_pattern.split(";");
			
			if(steps.length > 0){
				
				mUpdateSteps = new float[steps.length];
				for(int i = 0; i < steps.length; i++){
					String step = steps[i];
					try {
						float value = Float.parseFloat(step.trim());
						mUpdateSteps[i] = value;
					} catch (NumberFormatException e) {
						e.printStackTrace();
						mUpdateSteps = null;
						throw new StepsParseException("Can't parse float value for " + step + " of pattern "+steps_pattern);
					}
				}
				
			} else{
				throw new StepsParseException("Can't parse steps pattern " + steps_pattern+", you are sure that use ");
			}
		}
	}
	
	float [] getStepsValue(){
		
		if(mUpdateSteps == null){
			try {
				parsePatternSteps();
				return null;
				
			} catch (StepsParseException e) {
				e.printStackTrace();
			}
		}
		
		return mUpdateSteps;
	}
 
	void enableDisableActionButtons(boolean applyForIncrementButtons, boolean enabled){
		List<View> actionButtons = mLayoutSetupHelper.getActionButtons();
		
		for(View ab: actionButtons){
			float deltaValue = getIncrementValueOfActionButton(ab);
			if(deltaValue < 0 && !applyForIncrementButtons){
				// update decrement button state 
//				if(ab.isEnabled() != enabled)
					ab.setEnabled(enabled);
				
			} else if(deltaValue > 0 && applyForIncrementButtons){
				// update increment button state
//				if(ab.isEnabled() != enabled)
					ab.setEnabled(enabled);
			}
		}
	}

	// call this method to render current NumberPicker value state.
	protected void updateGuiState(){
		if(value == 0 && checkbox_on_zero){
			// show zero checkbox button
			mLayoutSetupHelper.showZeroCheckbox();
			
			// setup checkbox
			final CheckBox zeroCheckbox = mLayoutSetupHelper.getZeroCheckbox();
			zeroCheckbox.setText(label);
			zeroCheckbox.setOnCheckedChangeListener(null);
			zeroCheckbox.setChecked(false);
			zeroCheckbox.setOnCheckedChangeListener(mOnZeroCheckboxListener);
			
		} else{
			mLayoutSetupHelper.showActionButtons();
			
			mValueTextView.setText(String.valueOf(value));
			
			if(value == 1 || value == -1){
				mLabelTextView.setText(label);
				
			} else {
				if(label_plural != null)
					mLabelTextView.setText(label_plural);
				else mLabelTextView.setText(label);
			}			
		}
	}
	
	protected float getIncrementValueOfActionButton(View actionButton){
//		if(actionButton.getTag() != null)
		return (Float) actionButton.getTag();
	}
	
	protected void doValueUpdateAction(View sourceActionButton, float deltaValue){
		setValue(value + deltaValue, true);
	}
	
	// listeners
	private OnClickListener mOnActionButtonClickListener = new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			float deltaValue = getIncrementValueOfActionButton(v);
			doValueUpdateAction(v, deltaValue);
		}
	};
	
	private OnCheckedChangeListener mOnZeroCheckboxListener = new OnCheckedChangeListener() {
		
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(isChecked){
				setValue(checkbox_first_value, true);
			}
		}
	};
	// ~listeners
	
	/**
	 * <p>Interface definition for a callback to be invoked when the value changed.</p>
	 */
	public static interface OnValueChangedListener{
		public void onValueChanged(NumberPicker widget, float oldValue, float newValue);
//		public boolean allowUpdateValue(NumberPicker widget, float oldValue, float newValue);
		
	}
	
	public static enum AlignType{
		VERTICAL,
		HORISONTAL
	}
	
	
	/**
	 * Base setup helper, used to setup buttons in layout depend of align type,
	 * and group_count.
	 */
	static abstract class LayoutSetupHelper{
		protected List<View> mActionButtons;

		protected NumberPicker mNumberPicker;
		
		protected CheckBox mZeroCheckbox;
		
		protected Context mContext;
		
		/**
		 * Layout helper constructor
		 * 
		 * @param np
		 *            the instance of {@link NumberPicker}
		 */
		public LayoutSetupHelper(NumberPicker np){
			mNumberPicker = np;
			mContext = mNumberPicker.getContext();
		}
		
		/**
		 * Call this method to release helper.
		 */
		public void releaseLayout(){
			mActionButtons = null;
			mNumberPicker = null;			
			mContext = null;
		}
		
		public List<View> getActionButtons(){
			return mActionButtons;
		}
		
		public CheckBox getZeroCheckbox(){
			return mZeroCheckbox;
		}

		/**
		 * This method setup layout, and assign touch listeners
		 */
		public void setupLayout(){
			setupLayoutInternal();
			
			// attach touch listeners
			if(!mNumberPicker.isInEditMode())
				if(mActionButtons != null){
					for(View ab: mActionButtons){
						ab.setOnTouchListener(mOnTouchListener);
					}
				}
		}
		
		/**
		 * Implement this method, to setup layout
		 */
		protected abstract void setupLayoutInternal();
		
		/**
		 * This method must called from {@link NumberPicker} to show action
		 * buttons.
		 */
		public abstract void showActionButtons();
		
		/**
		 * This method must called from {@link NumberPicker} to show check box,
		 * for zero value.
		 */
		public abstract void showZeroCheckbox();
		
		/**
		 * Override this method, to show visual effects, when action button down
		 * 
		 * @param button
		 *            the action button
		 */
		protected void handleActionButtonDown(View button){}
		
		/**
		 * Override this method, to show visual effects, when action button up
		 * 
		 * @param button
		 *            the action button
		 * 
		 * @see {@link handleActionButtonUp}
		 */
		protected void handleActionButtonUp(View button){}
		
		/**
		 *  An touch listener, that will attach to all action buttons, to inform 
		 *  layout about button reaction(down, up), to do some visual effects
		 */
		private OnTouchListener mOnTouchListener = new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				int action = event.getAction();
				switch (action) {
				
				case MotionEvent.ACTION_DOWN:
					handleActionButtonDown(v);
					break;
					
				case MotionEvent.ACTION_UP:
					handleActionButtonUp(v);
					break;
					
				default:
					break;
				}

				return false;
			}
		};
		
	}
	
	static class HorisontalLayoutSetupHelper extends LayoutSetupHelper{
		View mButtonsContainer;

		public HorisontalLayoutSetupHelper(NumberPicker np) {
			super(np);
		}

		@Override
		public void setupLayoutInternal() {
			mActionButtons = new ArrayList<View>();
			LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			inflater.inflate(R.layout.widget_number_picker_horizontal, mNumberPicker, true);
			
			mButtonsContainer = mNumberPicker.findViewById(R.id.actionButtonsContainer);
			mZeroCheckbox = (CheckBox) mNumberPicker.findViewById(R.id.cbxZeroValued);
			
			mNumberPicker.mLabelTextView = (TextView) mNumberPicker.findViewById(R.id.tvLabel);
			mNumberPicker.mValueTextView = (TextView) mNumberPicker.findViewById(R.id.tvValue);
			
			//TODO implement dynamic adding buttons depended of steps
			mActionButtons.add(mNumberPicker.findViewById(R.id.btnDecremental));
			mActionButtons.add(mNumberPicker.findViewById(R.id.btnIncremental));
			
//			mNumberPicker.mLabelTextView.setVisibility(View.GONE);
		}

		@Override
		public void showActionButtons() {
			mZeroCheckbox.setVisibility(View.GONE);
			mButtonsContainer.setVisibility(View.VISIBLE);
		}

		@Override
		public void showZeroCheckbox() {
			mZeroCheckbox.setVisibility(View.VISIBLE);
			mButtonsContainer.setVisibility(View.GONE);
		}

		@Override
		protected void handleActionButtonDown(View button) {
			super.handleActionButtonDown(button);
			mButtonsContainer.setBackgroundColor(Color.RED);
		}

		@Override
		protected void handleActionButtonUp(View button) {
			super.handleActionButtonUp(button);
			mButtonsContainer.setBackgroundColor(Color.WHITE);
		}
	}
	
	static class VerticalLayoutSetupHelper extends LayoutSetupHelper{

		private View mButtonsContainer;
		
		public VerticalLayoutSetupHelper(NumberPicker np) {
			super(np);
		}

		@Override
		public void setupLayoutInternal() {
			mActionButtons = new ArrayList<View>();
			LayoutInflater layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			layoutInflater.inflate(R.layout.widget_number_picker_vertical, mNumberPicker, true);
			
			mButtonsContainer = mNumberPicker.findViewById(R.id.wcl_ActionButtonsContainer);
			mZeroCheckbox = (CheckBox) mNumberPicker.findViewById(R.id.wcl_cbZeroCheckbox);
			
			mNumberPicker.mLabelTextView = (TextView) mNumberPicker.findViewById(R.id.wcl_tvNumberLabel);
			mNumberPicker.mValueTextView = (TextView) mNumberPicker.findViewById(R.id.wcl_tvNumberValue);
			
			//TODO implement dynamic adding buttons depended of steps
			mActionButtons.add(mNumberPicker.findViewById(R.id.wcl_btnDecremetn));
			mActionButtons.add(mNumberPicker.findViewById(R.id.wcl_btnIncrement));
		}

		@Override
		public void showActionButtons() {
			mButtonsContainer.setVisibility(View.VISIBLE);
			mZeroCheckbox.setVisibility(View.GONE);
		}

		@Override
		public void showZeroCheckbox() {
			mButtonsContainer.setVisibility(View.GONE);
			mZeroCheckbox.setVisibility(View.VISIBLE);
		}

		@Override
		protected void handleActionButtonDown(View button) {
			super.handleActionButtonDown(button);
//			mButtonsContainer.setBackgroundColor(Color.RED);
		}

		@Override
		protected void handleActionButtonUp(View button) {
			super.handleActionButtonUp(button);
//			mButtonsContainer.setBackgroundColor(Color.WHITE);
		}
		
		
	}
	
//	private static abstract class LayoutAligner{
//		protected Context mContex;
//		
//		public LayoutAligner(Context context){
//			mContex = context;
//		}
//		
//		public abstract void setupNumberPicker(NumberPicker np);
//	}
//		
//	private static class LayoutVerticalAligner extends LayoutAligner{
//
//		public LayoutVerticalAligner(Context context) {
//			super(context);
//		}
//
//		@Override
//		public void setupNumberPicker(NumberPicker np) {
//		}
//		
//	}
//	
//	private static class  LayoutHorizontalAligner extends LayoutAligner{
//
//		public LayoutHorizontalAligner(Context context) {
//			super(context);
//		}
//
//		@Override
//		public void setupNumberPicker(NumberPicker np) {
//			LayoutInflater layoutInflater = (LayoutInflater) mContex.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			layoutInflater.inflate(R.layout.widget_number_picker_horizontal, np, true);
//			np.mDecrementButton = (Button) np.findViewById(R.id.btnDecremental);
//			np.mIncrementButton = (Button) np.findViewById(R.id.btnIncremental);
//		}
//		
//	}
}
