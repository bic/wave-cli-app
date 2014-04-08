package com.maivic.restaurant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import com.example.restaurantapp.R;

public class MainMenu extends FragmentActivity{
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_menu);
        
        ImageButton goToOfferActivity = (ImageButton) findViewById(R.id.main_menu_iB_config_propuneri);
        goToOfferActivity.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent offerIntent = new Intent(MainMenu.this, OfferActivity.class);
				
				v.getContext().startActivity(offerIntent);
				
			}
		});
	

}
	
	  public void goOfferActivity(View view ){
			Intent offerIntent = new Intent(this, OfferActivity.class);
			getApplicationContext().startActivity(offerIntent);
		}
	  
	  public void goWebViewActivity(View view){
	    	Intent webviewIntent = new Intent(this, WebViewActivity.class);
	    	startActivity(webviewIntent);
	    }
}