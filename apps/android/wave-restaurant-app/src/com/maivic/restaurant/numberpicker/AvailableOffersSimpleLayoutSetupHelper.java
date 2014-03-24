package com.maivic.restaurant.numberpicker;

import java.math.BigDecimal;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.example.restaurantapp.R;
import com.maivic.android.widget.NumberPicker.AlignType;
import com.maivic.android.widget.SimpleLayoutSetupHelper;
import com.maivic.android.widget.exception.StepsParseException;
import com.maivic.restaurant.utils.DimensionUtils;
import com.maivic.restaurant.utils.FontUtils;

public class AvailableOffersSimpleLayoutSetupHelper extends SimpleLayoutSetupHelper{

		public AvailableOffersSimpleLayoutSetupHelper(Context context) {
			super(context);
		}
		
		
		
		
		
		
		
		
		@Override
		protected void decorateActionButton(View buttonView, int buttonPosition) {
			int groupNumber = buttonPosition / mNumberPicker.getGroup_size();
			int buttonInGroupPosition = buttonPosition
					% mNumberPicker.getGroup_size();

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
						buttonTitle = String.format("%s %s",  (increment.signum() > 0 ? "+ ": "") + increment, mNumberPicker.getButtonlabels());					
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
		
		

		@Override
		protected View createActionButton() {
			// TODO from xml resource
			Button button = new Button(mContext);
			return button;
		}
		
		@Override
		public void setLabelValue(String label, String value) {
			cbxZeroValueCheckbox.setText(label);
			txtLabelView.setText(label);
			txtValueView.setText(value);
		
		
		}

		@Override
		protected void onLayoutCreated() {
			
			if(mNumberPicker.getAlign() == AlignType.HORIZONTAL){
				// vertical
				
				txtValueView.setTextSize(28);
				
				
				int buttonSize = (int) DimensionUtils.convertDpToPx(30);
				
				List<View> actionButtons = getActionButtons();
				if(actionButtons != null && actionButtons.size() > 0){
					for(View buttonView: actionButtons){
						Button button = (Button) buttonView;
						
						button.setWidth(buttonSize);
						button.setHeight(80);
					}
				}

			} 
		}

	

	
	}
