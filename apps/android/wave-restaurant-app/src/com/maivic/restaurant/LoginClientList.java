package com.maivic.restaurant;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.maivic.protocol.Model.Client;
import net.maivic.protocol.Model.ClientOrBuilder;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Telephony.Sms.Conversations;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.restaurantapp.R;




public class LoginClientList extends ListFragment {
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
	    initialiseData();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState); 
		
		 View v = inflater.inflate(R.layout.login_list_view, container, false);
		 
		 ListView listView = (ListView) v.findViewById(android.R.id.list);
		 ArrayAdapterLogin adapter = new ArrayAdapterLogin(inflater.getContext(), android.R.id.list);
         
         listView.setAdapter(adapter);
		 
         
         
		 return v;
	}
	
	@Override
	public void onListItemClick(ListView listView, View v, int position, long id) {

		final ClientOrBuilder selectedUser = (ClientOrBuilder) listView.getAdapter().getItem(position);
		/*Intent offerIntent = new Intent(this, OfferActivity.class);
		startActivity(offerIntent);*/
		if (!selectedUser.hasPassmd5()){
			Intent offerActIntent = new Intent(getActivity(), OfferActivity.class);
			startActivity(offerActIntent);
		}else if (selectedUser.hasPassmd5()){
			AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
			LayoutInflater inflater = getActivity().getLayoutInflater();
			View alertDialogView = inflater.inflate(R.layout.login_password_dialog, null);
			builder.setView(alertDialogView);
			final EditText userInput = (EditText) alertDialogView.findViewById(R.id.login_user_input);
			
			builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
					
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						String inputPass = userInput.getText().toString();
						if (selectedUser.getPassmd5().equals(convertMd5(inputPass))){
							String eroare = new String("E ok, te loghezi");
							Toast.makeText(getActivity(), eroare, Toast.LENGTH_SHORT).show();
							Intent offerActIntent = new Intent(getActivity(), OfferActivity.class);
							startActivity(offerActIntent);
						}else{
							String eroare = new String("parola gresita");
							Toast.makeText(getActivity(), eroare, Toast.LENGTH_SHORT).show();
						}
							
						 
						
					}
				})
					.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
						
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.cancel();

						}
					});
			AlertDialog alertDialog = builder.create();
			alertDialog.show();
			

		}
			
		String io = "blabla";
		
		//get selected items
		
 
	}
	
	
	public static String convertMd5(String pass){
		String md5Pass=null;
		 MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
			
			byte[] hash = md.digest(pass.getBytes("UTF-8"));
		        
	     //converting byte array to Hexadecimal String
	        StringBuilder sb = new StringBuilder(2*hash.length);
	        for(byte b : hash){
		        sb.append(String.format("%02x", b&0xff));
	        }
	        md5Pass = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return md5Pass;
	}
	
    
    
    
}

/*EditText passTv = (EditText) v.findViewById(R.id.login_edtxt_password);
passTv.setVisibility(View.VISIBLE);
Editable pass = passTv.getText();
if (selectedUser.getPassmd5().equals(pass)){
	String eroare = new String("E ok, te loghezi");
	Toast.makeText(this.getActivity(), eroare, Toast.LENGTH_SHORT).show();
	Intent offerIntent = new Intent(getActivity(), OfferActivity.class);
	startActivity(offerIntent);
}else{
	String eroare = new String("parola gresita");
	Toast.makeText(this.getActivity(), eroare, Toast.LENGTH_SHORT).show();
}*/
