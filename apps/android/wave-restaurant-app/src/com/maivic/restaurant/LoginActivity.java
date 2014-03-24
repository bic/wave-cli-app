package com.maivic.restaurant;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.maivic.protocol.Model.Client;
import net.maivic.protocol.Model.ClientOrBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restaurantapp.R;

public class LoginActivity extends BaseActivity {
	private static final int CAMERA_REQUEST = 1888; 
    private ImageView imageView;
    private List<String> adapterData = new ArrayList<String>();
    public static HashMap<String, ClientOrBuilder> employees = 
            new HashMap<String, ClientOrBuilder>();
    
    
    private static List<ClientOrBuilder> clientList = new ArrayList<ClientOrBuilder>();
    
    
    public static void initialiseData(){
    	
    	Client.Builder client = Client.newBuilder();
        client.setUser("John");
        client.setPassmd5("pass");
        clientList.add(client);
        
        
    	Client.Builder client2 = Client.newBuilder();
        client2.setUser("George");
        client2.setPassmd5("pass2");
        clientList.add(client2);
       	
        Client.Builder client3 = Client.newBuilder();
        client3.setUser("Mark");
        client3.setPassmd5("");
        clientList.add(client3);
        /*employees.put("TO", client3);*/
        
        Client.Builder client4 = Client.newBuilder();
        client4.setUser("Tom");
        client4.setPassmd5("");
        clientList.add(client4);
        
        
    }
    
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        initialiseData();
        final TextView editPass = (TextView) this.findViewById(R.id.login_edtxt_password);
        
        
        //String adapterData = employees.keySet().iterator().next().value;
        /*for (HashMap<Key, Value> e : employees.entrySet()){
        	
        }*/
        
       /* for (String key : employees.keySet()){
        	
        	adapterData.add(employees.get(key).getUser());
        	//System.out.println(adapterData);
        }*/
        
      /*  for (HashMap.Entry<String, List<String>> e : employees.entrySet())
            adapterData.addAll(e.getValue());*/
         for (ClientOrBuilder client:clientList){
        	 adapterData.add(client.getUser());
         }

        
        
         
         
         
         
        /*Spinner sp = (Spinner) findViewById(R.id.employees_spinner);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, adapterData);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sp.setAdapter(adapter);
       
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                String client = (String) parent.getItemAtPosition(pos);
                ClientOrBuilder cli = clientList.get(pos);
                
                	if  (cli.getPassmd5().equals("")){
                		
                		editPass.setVisibility(View.VISIBLE);
                		pass.setVisibility(View.VISIBLE);

                	}else{
                		
                		editPass.setVisibility(View.GONE);
                		pass.setVisibility(View.GONE);
                	}
                	
                
            }
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });*/
       
        
        
       /* this.imageView = (ImageView)this.findViewById(R.id.login_poza);
        Button photoButton = (Button) this.findViewById(R.id.login_btn_take_picture);
        photoButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); 
                startActivityForResult(cameraIntent, CAMERA_REQUEST); 
            }
        });*/
    }

   /* protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {  
            Bitmap photo = (Bitmap) data.getExtras().get("data"); 
            imageView.setImageBitmap(photo);
        }  
    } */
    
    
   /* @Override
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
        	goOfferActivity();
            return true;
        case R.id.action_refresh:
        	goLoginActivity();
        default:
            return super.onOptionsItemSelected(item);
        }
    }
 */   
    
    
	
	 /*private void goLoginActivity() {
		 Intent intent = new Intent(this, LoginActivity.class);
			startActivity(intent);
		
	}


	public void goOfferActivity(){
			Intent offerIntent = new Intent(LoginActivity.this, OfferActivity.class);
			startActivity(offerIntent);
	 }*/
	 
    public void goMainMenuActivity(View view ){
		Intent mainMenuIntent = new Intent(this, MainMenu.class);
		getApplicationContext().startActivity(mainMenuIntent);
	}
    
    
}

