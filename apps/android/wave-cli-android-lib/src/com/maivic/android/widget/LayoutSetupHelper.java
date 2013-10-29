package com.maivic.android.widget;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Checkable;

/**
 * This is base class, used by {@link NumberPicker} to setup buttons layout
 * 
 * @author Serghei
 * 
 */
public abstract class LayoutSetupHelper {

	static interface ActionButtonClickCallback {
		public void onZerroCheckboxClick(Checkable buttonView,
				boolean checkedValue);

		public void onActionButtonClick(View buttonView, BigDecimal stepValue);
	}

	protected ActionButtonClickCallback mButtonClickCallback;

	protected Context mContext;

	protected NumberPicker mNumberPicker;
	
	void setNumberPicker(NumberPicker numberPicker){
		this.mNumberPicker = numberPicker;
		mButtonClickCallback = numberPicker;
	}

	public LayoutSetupHelper(Context context) {
		mContext = context;
	}

	public BigDecimal getStepValueForActoinButton(View actionButton) {
		try {
			return (BigDecimal) actionButton.getTag();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Set label value
	 * @param title
	 * @param value
	 */
	abstract public void setLabelValue(String title, String value);

	abstract public void setZeroCheckboxChecked(boolean checked);

	abstract public ViewGroup createContentView();

	abstract public List<View> getActionButtons();

	abstract public void showActionButtons();

	abstract public void showZeroCheckbox();
	
	/**
	 * Override this method, to provide visual presentation of action button for
	 * enabled or disabled state. By default, this method just
	 * call buttonView.setEnabled(enabled)
	 * 
	 * @param buttonIndex
	 *            action button index in steps pattern
	 * @param buttonView
	 *            action button view
	 * @param enabled
	 *            enable or disable value
	 */
	public void setButtonEnabledState(View buttonView,
			int buttonIndex, boolean enabled){
		buttonView.setEnabled(enabled);
	}
}
