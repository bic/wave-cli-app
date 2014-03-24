package com.maivic.restaurant;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.maivic.protocol.Model.Client;
import net.maivic.protocol.Model.ClientOrBuilder;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.restaurantapp.R;

public class ArrayAdapterLogin extends ArrayAdapter<ClientOrBuilder> {
	private static final int CAMERA_REQUEST = 1888;
	Context mContext;
	int layoutResourceId;
	ImageView adminP;
	UUID uuid;
	private String fm;
	private static List<ClientOrBuilder> clientList = new ArrayList<ClientOrBuilder>();

	public static void initialiseData() {

		Client.Builder client = Client.newBuilder();
		client.setUser("John");
		client.setPassmd5(convertMd5("pass"));
		client.hasPassmd5();
		clientList.add(client);

		Client.Builder client2 = Client.newBuilder();
		client2.setUser("George");
		client2.setPassmd5(convertMd5("pass"));
		client2.hasPassmd5();
		clientList.add(client2);

		Client.Builder client3 = Client.newBuilder();
		client3.setUser("Mark");
		
		
		clientList.add(client3);
		/* employees.put("TO", client3); */

		Client.Builder client4 = Client.newBuilder();
		client4.setUser("Tom");
		
		clientList.add(client4);

	}

	public ArrayAdapterLogin(Context mContext, int layoutResourceId) {

		super(mContext, layoutResourceId);

		this.layoutResourceId = layoutResourceId;
		this.mContext = mContext;
		initialiseData();
		for (ClientOrBuilder client : clientList) {
			add(client);
		}
		/*DeviceUUIDFactory factory = new DeviceUUIDFactory(this.mContext);
	    uuid = factory.getDeviceUuid();
	    fm = factory.getUuidFactoryMethod();*/

	}

	@Override
	public View getView(int position, View convertViewParam, ViewGroup parent) {

		LayoutInflater mInflater = (LayoutInflater) mContext
				.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		final View convertView = convertViewParam != null ? convertViewParam
				: mInflater.inflate(R.layout.login_list_row, null);
		if (this.getCount() == 0)
			return convertView;

		ClientOrBuilder client = getItem(position);

		TextView textViewItem = (TextView) convertView
				.findViewById(R.id.login_list_row_tV);
		textViewItem.setText(client.getUser());
		
		/*TextView uuidTv = (TextView) convertView.findViewById(R.id.login_edtxt_password);
		uuidTv.setText(uuid.toString() + " " + fm);*/
		//uuidTv.setText(uuid.toString());

		adminP = (ImageView) convertView.findViewById(R.id.login_admin_picture);

		adminP.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent cameraIntent = new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
				((Activity) mContext).startActivityForResult(cameraIntent,
						CAMERA_REQUEST);
			}
		});

		return convertView;

	}

	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
			Bitmap photo = (Bitmap) data.getExtras().get("data");
			adminP.setImageBitmap(photo);
			notifyDataSetChanged();
		}
	}
	/*
	 * public void setAdminPicture(View v){ Intent cameraIntent = new
	 * Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE); ((Activity)
	 * mContext).startActivityForResult(cameraIntent, CAMERA_REQUEST); }
	 */
	
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
