package com.maivic.restaurant;


import java.math.BigDecimal;
import java.util.TreeMap;
import java.util.UUID;

import net.maivic.context.Log;
import net.maivic.protocol.Model;
import net.maivic.protocol.Model.NotificationOrBuilder;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.restaurantapp.R;
import com.maivic.android.widget.NumberPicker;
import com.maivic.restaurant.data.NotificationGenerator;
import com.maivic.restaurant.data.UpdateDataCallback;

public class ProposalFragment extends Fragment {
	public static final int OPTIONS_PER_COURSES_IDX=0;
	public static final int OPTIONS_PER_OFFER_IDX=1;
	public static final int OPTIONS_PER_ORDER_IDX=2;
	private static Log log=net.maivic.context.Context.get().log();
	private static final String TAG = CustomArrayAdapter.class.getName();
	Context context;
	NumberPicker np;
    int idx;
	private OfferActivity activity;
	private NotificationGenerator notificationGenerator = new NotificationGenerator();

    /**
     * Create a new instance of CountingFragment, providing "num"
     * as an argument.
     * 
     */
   // private static FragmentManager myFragmentManager; 
    
    static ProposalFragment newInstance(int num) {
        ProposalFragment f = new ProposalFragment();

        return f;
    }

    /**
     * When creating, retrieve this instance's number from its arguments.
     */
    private TreeMap<Long,Offer> offerMap = new TreeMap<Long, Model.Offer>();
	private TextView price_tv;
    private UUID uuid;
    private int getActiveTab(){
    	return 0;
    }
    private Offer getActiveDataSet(){
    	int i=0;
    	int end = getActiveTab();
    	for (Offer o : offerMap.values()){
    		
    		
    		if(i==end) return o;
    		i++;
    	}
    	return null;
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        
       
        
        
        idx = getArguments() != null ? getArguments().getInt("id") : 1;
        //myFragmentManager = this.getFragmentManager();
        
        this.activity = (OfferActivity)getActivity();
        
        // this is the old and unnecessary way of displaying a notifcation, via a dialog.  
       
   /*     activity.messageContent.registerValueListener(new UpdateDataCallback<Model.NotificationOrBuilder>(){

			@Override
			public void updateValues(NotificationOrBuilder value) {
				// TODO Auto-generated method stub
				long notificationSeverity = value.getId();
				String notificationMessage = value.getName();
				notificationGenerator.DisplayNotification((int) notificationSeverity, notificationMessage, activity);
			}
        	
        });*/
        
        
        
        activity.offerContent.registerValueListener(new UpdateDataCallback<Model.Offer>() {	
			@Override
			public void updateValues(Offer value) {
				setOfferPrice(value);
				//answerbig.toBigInteger().toString()
			}

			
		});
        activity.offerContent.registerValueListener(new UpdateDataCallback<Model.Offer>() {
			
			@Override
			public void updateValues(Offer value) {
				ProposalFragment.this.offerMap.put(value.getId(), value);
				activity.offerOptionsContent.registerValueListener(new UpdateDataCallback<Model.OfferOption>() {
					
					@Override
					public void updateValues(OfferOption value) {
						if (value.getIsOfferChanger() && value.getSeq()>0 && value.getSeq() <=3 ){
							activity.courseContent[(int) value.getSeq()-1].setreceivedValue(value);
							activity.optionsConfigurationContent[OPTIONS_PER_COURSES_IDX].setreceivedValue(value);
							
								
							}else {
								if(value.hasMinPerOffer()){
									activity.optionsConfigurationContent[OPTIONS_PER_OFFER_IDX].setreceivedValue(value);
								}else{
									if(value.hasMinPerOrder()){
										activity.optionsConfigurationContent[OPTIONS_PER_ORDER_IDX].setreceivedValue(value);
									
								}
								
							}
						}
						
					}
				});
				activity.offerOptionsContent.getOfferOption(getActiveDataSet());
			}
		});
        
        activity.offerContent.getOffersAsync();
        
    }
    


    /**
     * The Fragment's UI is just a simple text view showing its
     * instance number.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
    	super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.tab_fragment_layout, container, false);
        View tv = v.findViewById(R.id.set_delivery_hour);
        ((TextView)tv).setText("14:40");
        v.findViewById(R.id.overlayer);
       
        //tv.setBackgroundDrawable(getResources().getDrawable(android.R.drawable.gallery_thumb));
       
        String[] locations = new String[] {"Charles de Gaulle"};
        Spinner sp = (Spinner) v.findViewById(R.id.location_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, locations);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(adapter);
        //price_tv = (TextView) v.findViewById(R.id.offer_price_tv);
        np = (NumberPicker) v.findViewById(R.id.npOfferTotalPrice);
        log.e(TAG, "update price number picker on np " + np);
        np.setInline_style(true);
		np.setGroup_inline_split(1);
		
		
       
        return v;
        
        
    	//return null;
    }
    
   
    
    private void setOfferPrice(Offer offer) {
		// TODO Auto-generated method stub
    	 //((TextView)price_tv).setText((value.getPrice()).toString());
    	//BigDecimal newValue = offer.getPrice();
    	BigDecimal newValue = BigDecimal.valueOf(offer.getPrice().getValue(), offer.getPrice().getScale()*(-1));
    	np.setValue(newValue, false);
    	log.e(TAG, "update price number picker on np " + np);
	}
    
    public void updateNumberPicker(){
    	
    }
   
}

