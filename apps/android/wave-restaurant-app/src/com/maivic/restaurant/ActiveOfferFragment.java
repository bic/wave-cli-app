package com.maivic.restaurant;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import android.app.Activity;
import android.app.Fragment;
import android.app.TimePickerDialog;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import com.example.restaurantapp.R;
import com.maivic.android.widget.NumberPicker;
import com.maivic.android.widget.NumberPicker.AlignType;
import com.maivic.android.widget.exception.StepsParseException;




public class ActiveOfferFragment extends Fragment{
	TextView txt_time=null;


	  /*@Override
	   public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        
	  }*/

	    @Override
	    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    	super.onCreateView(inflater, container, savedInstanceState);
	        View v =  inflater.inflate(R.layout.cp_active_offers_fragment_layout, container, false);
	        //TextView tv = (TextView) v.findViewById(R.id.tv_location);
	        //tv.setText("New fragment created");
	        
	        Calendar deadline = Calendar.getInstance();
			deadline.set(Calendar.HOUR_OF_DAY, 19);
			deadline.set(Calendar.MINUTE, 25);
			
			
			MathContext mc = new MathContext(2);
			//txt_time = (TextView) v.findViewById(R.id.cp_active_orders_deadline);
			//MyNumberPicker availableOffers =(MyNumberPicker) v.findViewById(R.id.cp_np_available_offers);
			final NumberPicker orderDeadlineNP = (NumberPicker) v.findViewById(R.id.cp_np_orders_deadline);
			NumberPicker availableOrders =(NumberPicker) v.findViewById(R.id.cp_np_available_offers);
			availableOrders.setGroup_size(4);
			availableOrders.setInline_style(true);
			availableOrders.setGroup_inline_split(2);
			availableOrders.setAlign(AlignType.HORIZONTAL);
			BigDecimal maxValueAvOffers = new BigDecimal(10);
			
			availableOrders.setMax_value(maxValueAvOffers);
			
			BigDecimal minValueAvOffers = new BigDecimal(5);
			
			availableOrders.setMin_value(minValueAvOffers);
			try {
				availableOrders.setStepsPattern("-5;-1;+1;+5");
			} catch (StepsParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			
			orderDeadlineNP.setGroup_size(4);
			orderDeadlineNP.setInline_style(true);
			//orderDeadlineNP.set
			orderDeadlineNP.setGroup_inline_split(2);
			orderDeadlineNP.setAlign(AlignType.HORIZONTAL);
			long timeMillis = System.currentTimeMillis();


			long now = TimeUnit.MINUTES.convert(timeMillis, TimeUnit.MILLISECONDS);
			//BigDecimal priceChanger = BigDecimal.valueOf(offerOption.getPrice().getValue(), offerOption.getPrice().getScale()*(-1));
			BigDecimal bdnow = BigDecimal.valueOf(now);

			long ddlT = TimeUnit.MINUTES.convert(timeMillis, TimeUnit.MILLISECONDS) + 90;
			BigDecimal valueFromDB = BigDecimal.valueOf(ddlT);
			 
			orderDeadlineNP.setValue(valueFromDB , false);
			
			BigDecimal maxValueOrderDdl = new BigDecimal(960);
			
			final BigDecimal minValueOrderDdl = new BigDecimal(660);
			orderDeadlineNP.setMax_value(maxValueOrderDdl);
			orderDeadlineNP.setMin_value(minValueOrderDdl);
			
			BigDecimal remainingT = orderDeadlineNP.getValue().subtract(bdnow, mc);
			TextView remainingTV = (TextView) v.findViewById(R.id.cp_active_offers_time_remaining);
			long  remainingTlong = remainingT.longValue();
			String strRemTime = Long.toString(remainingTlong);
			remainingTV.setText(strRemTime);
			
			Button stopOrdering = (Button) v.findViewById(R.id.cp_btn_stop_ordering);
			stopOrdering.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					orderDeadlineNP.setMin_value(minValueOrderDdl);
					orderDeadlineNP.setMax_value(minValueOrderDdl);
					
				}
			});
			
			try {
				orderDeadlineNP.setStepsPattern("-15;-5;+5;+15");
			} catch (StepsParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			orderDeadlineNP.setButtonlabels("min");
			//orderDeadlineNP.
			Button maxDeadline = (Button) v.findViewById(R.id.cp_set_max_timer);
			maxDeadline.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					 

					orderDeadlineNP.setValue(orderDeadlineNP.getMaxValue(), false);
					
				}
			});
			
	        return v;
	    }
	    


	   
	}
	    
	    
	    
	



