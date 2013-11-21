package com.maivic.android.widget;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

import com.maivic.android.widget.NumberPicker.AlignType;
import com.maivic.android.widget.exception.StepsParseException;

/**
 * This is layout setup helper, described in specification. Also you can
 * customaze it, overriding correspond methods.
 * 
 * <ul>
 *   <li> {@link SimpleLayoutSetupHelper#onButtonDown(View buttonView)}</li>
 *   <li> {@link SimpleLayoutSetupHelper#onButtonUp(View buttonView)}</li>
 *   <li> {@link SimpleLayoutSetupHelper#decorateActionButton(View buttonView, int buttonIndex)}</li>
 *   <li> {@link LayoutSetupHelper#setButtonEnabledState(View buttonView, int buttonPosition, boolean enabled)}</li>
 * </ul>
 * 
 * @author Serghei
 * 
 */
public class SimpleLayoutSetupHelper extends LayoutSetupHelper {
	private static final String T = "SimpleLayoutSetupHelper";
	private static final int ID_TV_LABEL = 1;
	private static final int ID_TV_VALUE = 2;
	private static final int ID_LABEL_CONTAINER = 3;

	/**
	 * TextView which stores label of {@link NumberPicker}
	 */
	protected TextView txtLabelView;

	/**
	 * TextView which stores value of {@link NumberPicker}
	 */
	protected TextView txtValueView;

	/**
	 * ZeroValueCheckbox
	 */
	protected CheckBox cbxZeroValueCheckbox;
	
	/**
	 * ViewGroup container, who contain all action buttons and label layout
	 */
	protected ViewGroup buttonsContainer;
	
	/**
	 * ViewGroup container contains label value textviews
	 */
	protected ViewGroup labelValueContainer;
	
	/**
	 * Store list of action buttons
	 */
	protected List<View> mActionButtons;
	
//	public SimpleLayoutSetupHelper(NumberPicker numberPicker,
//			ActionButtonClickCallback buttonCallback) {
//		super(numberPicker, buttonCallback);
//	}
	
	public SimpleLayoutSetupHelper(Context context){
		super(context);
	}

	@Override
	public void setLabelValue(String label, String value) {
		cbxZeroValueCheckbox.setText(label);
		txtLabelView.setText(label);
		txtValueView.setText(value);
	}
	
	private LinearLayout createButtonsGroup(int orientation){
		LinearLayout layout = new LinearLayout(mContext);
		layout.setOrientation(orientation);
		return layout;
	}
	
	private ViewGroup createButtonsContainer(){
		labelValueContainer = createLabelValueContainer();
		LinearLayout content = new LinearLayout(mContext);
		
		createActionButtons();
		
		if(mNumberPicker.isInline_style()){
			// prepare buttons containers
			LinearLayout halfInlineButtonsGroup1 = new LinearLayout(mContext);
			LinearLayout halfInlineButtonsGroup2 = new LinearLayout(mContext);
			int orientation;
			if(mNumberPicker.getAlign() == AlignType.HORIZONTAL){
				orientation = LinearLayout.HORIZONTAL;
				halfInlineButtonsGroup1.setOrientation(LinearLayout.VERTICAL);
				halfInlineButtonsGroup2.setOrientation(LinearLayout.VERTICAL);
			} else{
				orientation = LinearLayout.VERTICAL;
				halfInlineButtonsGroup1.setOrientation(LinearLayout.HORIZONTAL);
				halfInlineButtonsGroup2.setOrientation(LinearLayout.HORIZONTAL);				
			}
			content.setOrientation(orientation);

			final int inlineSplitValue = mNumberPicker.getGroup_inline_split();
			final int groupSize = mNumberPicker.getGroup_size();
			
			if (inlineSplitValue <= 0
					|| inlineSplitValue >= mNumberPicker.getGroup_size()) {

				throw new IllegalStateException(
						"Can't split buttons group. The group_inline_split value must > 0 and < group_size - 1");
			}
			
			LinearLayout.LayoutParams layoutParamsForButton = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParamsForButton.weight = 1;
			
			LinearLayout.LayoutParams layoutParamsForGroup = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.WRAP_CONTENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			layoutParamsForGroup.weight = 1;
			layoutParamsForGroup.topMargin = 1;
			layoutParamsForGroup.bottomMargin = 1;
			layoutParamsForGroup.leftMargin = 1;
			layoutParamsForGroup.rightMargin = 1;
			
			LinearLayout buttonGroup = null;
			for(int i = 0; i < mActionButtons.size(); i++){
				int positionInGroup = i % groupSize;
				
				if(buttonGroup == null || positionInGroup == 0){
					// create button group
					buttonGroup = createButtonsGroup(orientation);
				}

				// add button in group
				View button = mActionButtons.get(i);
				buttonGroup.addView(button, layoutParamsForButton);
				
				boolean isLastButton = i == mActionButtons.size() - 1;
				if(positionInGroup == inlineSplitValue -1 || (positionInGroup < inlineSplitValue && isLastButton)){
//				if(i == 0){
					// add buttons to first half inline group
					halfInlineButtonsGroup1.addView(buttonGroup, layoutParamsForGroup);
					buttonGroup = null;
				}
				
//				if(i == 1){
				if(positionInGroup == groupSize - 1 || (positionInGroup >= inlineSplitValue && isLastButton)){
					// add buttons to second half inline group
					halfInlineButtonsGroup2.addView(buttonGroup, layoutParamsForGroup);					
					buttonGroup = null;
				}
			}
			
			// create content
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.weight = 0;
			lp.gravity = Gravity.CENTER;
			content.addView(halfInlineButtonsGroup1, lp);
			content.addView(labelValueContainer, lp);
			
			LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(
					LinearLayout.LayoutParams.MATCH_PARENT,
					LinearLayout.LayoutParams.WRAP_CONTENT);
			p.weight = 1;
//			p.leftMargin = p.rightMargin = 20;
			labelValueContainer.setLayoutParams(p);			
//			labelValueContainer.setPadding(10, 10, 10, 10);
			content.addView(halfInlineButtonsGroup2, lp);
			
		} else{
			// not inline layout
			content.setOrientation(LinearLayout.HORIZONTAL);
			
			LinearLayout groupsContainer = new LinearLayout(mContext);
			if(mNumberPicker.getAlign() != AlignType.HORIZONTAL)
				groupsContainer.setOrientation(LinearLayout.HORIZONTAL);
			else groupsContainer.setOrientation(LinearLayout.VERTICAL);				
			
			int orientation = mNumberPicker.getAlign() == AlignType.HORIZONTAL ? LinearLayout.HORIZONTAL
					: LinearLayout.VERTICAL;
			
			final int groupSize = mNumberPicker.getGroup_size();
			LinearLayout buttonGroup = null;
			for(int i = 0; i < mActionButtons.size(); i++){
				final int positionInGroup = i % groupSize;
				if(positionInGroup == 0){
					buttonGroup = createButtonsGroup(orientation);
				}
				
				LinearLayout.LayoutParams buttonParam = new android.widget.LinearLayout.LayoutParams(
						android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
						android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
				buttonParam.bottomMargin = buttonParam.leftMargin = buttonParam.rightMargin = buttonParam.topMargin = 10;
				buttonGroup.addView(mActionButtons.get(i), buttonParam);
				
				boolean isLastButton = i == mActionButtons.size() - 1;
				if(positionInGroup == groupSize -1 || isLastButton){
					groupsContainer.addView(buttonGroup);
				}
			}
			
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, 
					LinearLayout.LayoutParams.WRAP_CONTENT);
			lp.weight = 1;
			lp.gravity = Gravity.CENTER;
			// add label value container
			lp.rightMargin = 10;
			content.addView(labelValueContainer, lp);
			
			lp.rightMargin = 0;
			content.addView(groupsContainer, lp);
		}
		
		return content;
	}

	@Override
	public ViewGroup createContentView() {
		RelativeLayout content = new RelativeLayout(mContext);
		buttonsContainer = createButtonsContainer();
		cbxZeroValueCheckbox = new CheckBox(mContext);
		
		RelativeLayout.LayoutParams params;
		if(mNumberPicker.getAlign() == AlignType.HORIZONTAL){
			params = new RelativeLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
		} else{
			params = new RelativeLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);			
		}
		params.addRule(RelativeLayout.CENTER_HORIZONTAL | RelativeLayout.CENTER_VERTICAL);
		content.addView(buttonsContainer, params);
		content.addView(cbxZeroValueCheckbox/*, params*/);
		
		onLayoutCreated();
		
		return content;
	}
	
	/**
	 * this method called when layout created
	 */
	protected void onLayoutCreated(){}

	@Override
	public void showActionButtons() {
		buttonsContainer.setVisibility(View.VISIBLE);
		cbxZeroValueCheckbox.setVisibility(View.GONE);
	}

	@Override
	public void showZeroCheckbox() {
		buttonsContainer.setVisibility(View.GONE);
		cbxZeroValueCheckbox.setVisibility(View.VISIBLE);
	}
	
	@Override
	public void setZeroCheckboxChecked(boolean checked) {
		if(cbxZeroValueCheckbox != null){
			cbxZeroValueCheckbox.setOnCheckedChangeListener(null);
			cbxZeroValueCheckbox.setChecked(checked);
			cbxZeroValueCheckbox.setOnCheckedChangeListener(mZeroCheckboxChangeListener);
		}
	}

	@Override
	public List<View> getActionButtons() {
		return mActionButtons;
	}
	
	private void createActionButtons(){
		// create buttons
		BigDecimal[] stepsValue = null;
		try {
			stepsValue = mNumberPicker.getStepsValue();
		} catch (StepsParseException e) {
			e.printStackTrace();
		}
		mActionButtons = new ArrayList<View>();
		
		if(stepsValue != null){
			for(int i = 0; i < stepsValue.length; i++){
				View button = createActionButton();
				setupActionButton(button, stepsValue[i]);
				decorateActionButton(button, i);
				mActionButtons.add(button);
			}			
		} else{
			Log.e(T, "Can't create action buttons, because stepsValue is null(maybe parse error)");
		}
	}
	
	protected void setupActionButton(View button, BigDecimal stepValue){
		button.setTag(stepValue);
		button.setOnClickListener(mOnButtonClickListener);
		button.setOnTouchListener(mOnTouchListener);
	}
		
	protected ViewGroup createLabelValueContainer(){
//		RelativeLayout labelLayout = new RelativeLayout(mContext);			
//		txtLabelView = new TextView(mContext);
//		txtValueView = new TextView(mContext);			
//		
//		txtLabelView.setId(ID_TV_LABEL);
//		txtValueView.setId(ID_TV_VALUE);
//		
//		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
//				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		lp.leftMargin = 5;
//		lp.rightMargin = 5;
//		lp.topMargin = 5;
//		lp.bottomMargin = 5;
//		lp.addRule(RelativeLayout.CENTER_VERTICAL);
//		labelLayout.addView(txtLabelView, lp);
//		
//		lp = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT);
//		lp.addRule(RelativeLayout.RIGHT_OF, ID_TV_LABEL);
//		lp.addRule(RelativeLayout.CENTER_VERTICAL);
//		
//		labelLayout.addView(txtValueView, lp);
//		labelLayout.setId(ID_LABEL_CONTAINER);
		
		LinearLayout labelLayout = new LinearLayout(mContext);			
		labelLayout.setOrientation(LinearLayout.HORIZONTAL);
		txtLabelView = new TextView(mContext);
		txtValueView = new TextView(mContext);			

		//
		txtLabelView.setLines(2);
//		txtLabelView.setBackgroundColor(Color.GREEN);
		txtLabelView.setSingleLine(false);

		
		txtLabelView.setId(ID_TV_LABEL);
		txtValueView.setId(ID_TV_VALUE);
		
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
//		lp.leftMargin = 5;
//		lp.rightMargin = 5;
//		lp.topMargin = 5;
//		lp.bottomMargin = 5;
		
//		lp.gravity = Gravity.CENTER_HORIZONTAL;
		lp.weight = 1;
//		lp.addRule(RelativeLayout.CENTER_VERTICAL);
		labelLayout.addView(txtLabelView, lp);
		txtLabelView.setGravity(Gravity.CENTER);
		
		lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT);
		lp.gravity = Gravity.CENTER;
		lp.weight = 0;
		labelLayout.addView(txtValueView, lp);
		labelLayout.setId(ID_LABEL_CONTAINER);
		return labelLayout;
	}
	
	/**
	 * Override this method to provide some reaction when action button is down
	 * 
	 * @param buttonView
	 *            action button view
	 * @see {@link SimpleLayoutSetupHelper#onButtonUp(View buttonView)}
	 */
	protected void onButtonDown(View buttonView) {}
	
	/**
	 * Override this method, to provide some reaction when action button is up
	 * 
	 * @param buttonView
	 *            action button view
	 * @see {@link SimpleLayoutSetupHelper#onButtonDown(View buttonView)} method
	 */
	protected void onButtonUp(View buttonView) {}
	
	/**
	 * Override this method, to castomizate action button
	 * 
	 * @param buttonView
	 * @param buttonPosition
	 */
	protected void decorateActionButton(View buttonView, int buttonPosition){
		int groupNumber = buttonPosition / mNumberPicker.getGroup_size();
		int buttonInGroupPosition = buttonPosition % mNumberPicker.getGroup_size();
		
		BigDecimal[] steps = null;
		try {
			steps = mNumberPicker.getStepsValue();
		} catch (StepsParseException e) {
			e.printStackTrace();
		}
		
		if(buttonView instanceof Button){
			if(steps != null){
				BigDecimal increment = steps[buttonPosition];
				
				String buttonTitle;
				if(mNumberPicker.getButtonlabels() != null){
					buttonTitle = String.format("%s %s", mNumberPicker.getButtonlabels(), (increment.signum() > 0 ? "+ ": "") + increment);					
				} else{
					buttonTitle = (increment.signum() > 0 ? "+ ": "") + increment;
				}
				((Button)buttonView).setText(buttonTitle);				
			} else{
				((Button)buttonView).setText("Button: "+buttonPosition);
			}
		}
		
		if(buttonInGroupPosition == 0){
			
		}
	}
	
	protected View createActionButton(){
		Button button = new Button(mContext);
		return button;
	}
	
	private OnClickListener mOnButtonClickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(mButtonClickCallback != null){
				BigDecimal stepValue = getStepValueForActoinButton(v);			
				mButtonClickCallback.onActionButtonClick(v, stepValue);
			}
		}
	};
	
	private OnCheckedChangeListener mZeroCheckboxChangeListener = new OnCheckedChangeListener() {
		@Override
		public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
			if(mButtonClickCallback != null){
				mButtonClickCallback.onZerroCheckboxClick(buttonView, isChecked);
			}
		}
	};
	
	/**
	 *  An touch listener, that will attach to all action buttons, to inform 
	 *  layout about button reaction(down, up), to do some visual effects
	 */
	private OnTouchListener mOnTouchListener = new OnTouchListener() {
		
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			int action = event.getAction();
			Log.d(T, "onTouch "+event);
			switch (action) {
			
			case MotionEvent.ACTION_DOWN:
				onButtonDown(v);
				break;
				
			case MotionEvent.ACTION_CANCEL:				
			case MotionEvent.ACTION_UP:
				onButtonUp(v);
				break;
				
			default:
				break;
			}

			return false;
		}
	};
}
