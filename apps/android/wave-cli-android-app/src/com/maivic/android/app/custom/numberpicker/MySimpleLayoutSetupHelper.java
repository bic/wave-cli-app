package com.maivic.android.app.custom.numberpicker;

import java.math.BigDecimal;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import com.maivic.android.app.R;
import com.maivic.android.app.utils.DimensionUtils;
import com.maivic.android.app.utils.FontUtils;
import com.maivic.android.widget.NumberPicker.AlignType;
import com.maivic.android.widget.SimpleLayoutSetupHelper;
import com.maivic.android.widget.exception.StepsParseException;

/**
 * 
 * @author Serghei
 * 
 */
public class MySimpleLayoutSetupHelper extends SimpleLayoutSetupHelper {
	public MySimpleLayoutSetupHelper(Context context) {
		super(context);
	}

	@Override
	protected void decorateActionButton(View buttonView, int buttonPosition) {
//		int groupNumber = buttonPosition / mNumberPicker.getGroup_size();
//		int buttonInGroupPosition = buttonPosition
//				% mNumberPicker.getGroup_size();

		BigDecimal[] steps = null;
		try {
			steps = mNumberPicker.getStepsValue();
		} catch (StepsParseException e) {
			e.printStackTrace();
		}

		if (buttonView instanceof Button) {
			if (steps != null) {
				BigDecimal increment = steps[buttonPosition];

				if (increment.signum() > 0) {
					if (mNumberPicker.getAlign() == AlignType.VERTICAL)
						buttonView
								.setBackgroundResource(R.drawable.btn_shaded_plus);
					else
						buttonView.setBackgroundResource(R.drawable.btn_plus);

				} else {
					if (mNumberPicker.getAlign() == AlignType.VERTICAL)
						buttonView
								.setBackgroundResource(R.drawable.btn_shaded_minus);
					else
						buttonView.setBackgroundResource(R.drawable.btn_minus);
				}
			}
		}
	}

	@Override
	protected View createActionButton() {
		// TODO from xml resource
		Button button = new Button(mContext);

		if (mNumberPicker.getAlign() == AlignType.HORIZONTAL) {
			// update button size for horizontal alignment
			// int size = (int)
			// DimensionUtils.getDimensionFromResource(R.dimen.np_butotn_size);
			int size = (int) DimensionUtils.convertDpToPx(30);
			button.setWidth(size);
			button.setHeight(size);
		}

		return button;
	}

	@Override
	protected void onLayoutCreated() {
		if(mNumberPicker.getAlign() == AlignType.VERTICAL){
			cbxZeroValueCheckbox.setWidth((int)DimensionUtils.convertDpToPx(50));
			cbxZeroValueCheckbox.setHeight( (int)DimensionUtils.convertDpToPx(50));
			cbxZeroValueCheckbox.setButtonDrawable(R.drawable.cbx_big_selector);
			txtValueView.setTextSize(42);
			FontUtils.setFontForTextView(mContext, txtValueView, R.string.font_avenir_light);
			
		} else {
			cbxZeroValueCheckbox.setButtonDrawable(R.drawable.cbx_selector);
			txtValueView.setTextSize(20);
			txtValueView.setTextColor(mContext.getResources().getColorStateList(R.color.np_value_text_color));
			txtLabelView.setTextSize(16);
			
			FontUtils.setFontForTextView(mContext, txtValueView, R.string.font_avenir_black);
			FontUtils.setFontForTextView(mContext, txtLabelView, R.string.font_avenir_medium);
			
			buttonsContainer.setBackgroundResource(R.drawable.np_horizonral_bckg_color);
		}
	}

	@Override
	public void setButtonEnabledState(View buttonView, int buttonIndex,
			boolean enabled) {
		super.setButtonEnabledState(buttonView, buttonIndex, enabled);
		buttonView.setVisibility(enabled ? View.VISIBLE : View.INVISIBLE);
	}

	@Override
	protected void onButtonDown(View buttonView) {
		if(mNumberPicker.getAlign() == AlignType.HORIZONTAL){
			buttonsContainer.setBackgroundResource(R.drawable.np_button_bckg_pressed);			
			labelValueContainer.setSelected(true);
		}
	}

	@Override
	protected void onButtonUp(View buttonView) {
		if(mNumberPicker.getAlign() == AlignType.HORIZONTAL){
			buttonsContainer.setBackgroundDrawable(null);
			labelValueContainer.setSelected(false);
		}
	}
}
