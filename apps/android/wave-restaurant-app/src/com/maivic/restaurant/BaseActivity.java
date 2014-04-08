package com.maivic.restaurant;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.restaurantapp.R;

public class BaseActivity extends FragmentActivity {
	
	@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.action_bar_menu, menu);
 
        return super.onCreateOptionsMenu(menu);
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Take appropriate action for each action item click
        switch (item.getItemId()) {
        case R.id.action_goto_proposal_configuration:
            // search action
        	startActivity(new Intent(this, LoginActivity.class));
            return true;
        case R.id.action_refresh:
        	startActivity(new Intent(this, OfferActivity.class));
            
        default:
            return super.onOptionsItemSelected(item);
        }
    }

}
