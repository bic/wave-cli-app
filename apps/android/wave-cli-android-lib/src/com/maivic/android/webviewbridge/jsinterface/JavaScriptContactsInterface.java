package com.maivic.android.webviewbridge.jsinterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds.Email;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.Contacts;
import android.webkit.JavascriptInterface;

import com.maivic.android.utils.JSONUtils;

/**
 * This class need to register javasript interface to pick contacts list, and
 * return selected contact/contacts back to javascript method.
 * <p/>
 * Note. To use this javascript interface, need that activity initialized via
 * {@link JavaScriptRegistrarBase#setActivityContext(Activity activity)}
 * implements interface {@link JavaScriptContactsInterface.ActivityResultBinder}.
 * 
 * @author Serghei
 * 
 */
public class JavaScriptContactsInterface extends JavaScriptRegistrarBase{

	public static final int CONTACTS_PICK_REQUEST = 1001;
	
	public static interface ActivityResultDelegate{
		public boolean onActivityResult(int requestCode, int resultCode, Intent data);
	}
	
	/**
	 * This interface provide a mechanism to register
	 * {@link ActivityResultDelegate} for target activity
	 * 
	 * @author Serghei
	 * 
	 */
	public static interface ActivityResultBinder{
		/**
		 * Register onActivityResultDelegate
		 * @param delegate
		 */
		public void registerActivityResultDeligate(ActivityResultDelegate delegate);
	}
	
	// monitor, need to block javascript thread until user select contact from picker
	private Object monitor = new Object();
	
	@JavascriptInterface
	public String getContacts(String jsonArgs){
		
		try {
			final MethodResult result = new MethodResult();
			
			JSONObject args = new JSONObject(jsonArgs);
			validateArguments(args, new String[]{"pick_type", "types", "select"});

			// extract args
			String pickType = args.getString("pick_type");
			String [] types = JSONUtils.getStringArrayValue(args, "types");
			// select, can be multiple or single
			// ignore in this release
			String select = args.getString("select");
			
			Intent intent;
			if("Email".equalsIgnoreCase(pickType)){
				// intent for all contacts
				intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);	
				
			} else if("Phone".equalsIgnoreCase(pickType)){
				// create intent only for phone
				intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
				intent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE);
				
			} else{
				throw new IllegalStateException(
						"The pick_type value is not supported value "
								+ pickType + ", expected Email or Phone.");
			}
			
			if(mActivity instanceof ActivityResultBinder){
				((ActivityResultBinder) mActivity).registerActivityResultDeligate(new ActivityResultDelegate() {
					
					@Override
					public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
						if(requestCode == CONTACTS_PICK_REQUEST){

							if(Activity.RESULT_OK == resultCode){
								try {
									JSONObject jsonResult = new JSONObject();
									readContactsFromIntentData(data, jsonResult);
									result.resultObject = jsonResult;
									
								} catch (JSONException e) {
									result.exception = e;
								}
								
							} else{
								// user not selected contact (clicked back in picker activity)
								result.resultObject = JSONObject.NULL;
							}
							
							// notify javascript thread to stop wait
							synchronized (monitor){
								monitor.notify();
							}							
							
							// event consumed
							return true;
						}
						
						// event not consumed
						return false;
					}
				});
				
				try {
					mActivity.startActivityForResult(intent, CONTACTS_PICK_REQUEST);
					
				} catch (Exception e1) {
					// activity not found exception
					return generateErrorResponse(new IllegalStateException(
							"Can't start activity for activity " + intent, e1));
				}
				
				// wait until contact list picker closed
				synchronized (monitor) {
					try {
						monitor.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				
				if(result.hasError()){
					return generateErrorResponse(result.exception);
				} else{
					return generateSuccessResponse(result.resultObject);
				}
				
			} else{
				return generateErrorResponse(new IllegalStateException(
						"The activity "
								+ mActivity.getClass().getCanonicalName()
								+ " must implement interface "
								+ ActivityResultBinder.class.getName()));
			}
			
		} catch (JSONException e) {
			return generateErrorResponse(e);
			
		} catch(IllegalArgumentException e){
			return generateErrorResponse(e);
			
		} catch(Exception e){
			return generateErrorResponse(e);
		}
	}
	
	private void readContactsFromIntentData(Intent data, JSONObject result) throws JSONException{
		if(data != null){
			Uri contactUri = data.getData();
			String contactId = contactUri.getLastPathSegment();
					
			String primaryPhone = null;
			String selectedPhone = null;
			String email = null;
			
			ContentResolver contentResolver = mActivity.getContentResolver();
			if(data.hasExtra(Intent.EXTRA_PHONE_NUMBER)){
				// Intent store selected phone number
				// case, when picker_type is phone 
				selectedPhone = data.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
				
				// contentUri store path to selected phone number from table Phone
				Cursor c = contentResolver.query(contactUri, null, null, null, null);
				if(c.moveToFirst()){
					contactId = c.getString(c.getColumnIndex(Phone.CONTACT_ID));
				}
				c.close();
			}
			
			Cursor contactsCursor = contentResolver.query(Contacts.CONTENT_URI, null, Contacts._ID + "=?", new String[]{contactId}, null);
			if(contactsCursor.moveToFirst()){
				String display_name = contactsCursor.getString(contactsCursor.getColumnIndexOrThrow(Contacts.DISPLAY_NAME));

				// extract phone numbers
				JSONArray phones = new JSONArray();
				Cursor phoneCursor = contentResolver.query(Phone.CONTENT_URI, null, Phone.CONTACT_ID + "=?", new String[]{contactId}, null);
				if(phoneCursor.moveToFirst()){
					int indexPhone = phoneCursor
							.getColumnIndexOrThrow(Phone.DATA);
					int indexIsPrimary = phoneCursor.getColumnIndexOrThrow(Phone.IS_PRIMARY);
					do {
						String phoneNumber = phoneCursor
								.getString(indexPhone);
						phones.put(phoneNumber);
						
						if(phoneCursor.getInt(indexIsPrimary) != 0){
							primaryPhone = phoneNumber;
						}
					} while (phoneCursor.moveToNext());
				}				
				phoneCursor.close();
				
				// get email, for selected contact
				Cursor emailCursor = contentResolver.query(Email.CONTENT_URI, null, Email.CONTACT_ID + "=?", new String[]{contactId}, null);
				if(emailCursor.moveToFirst()){
					email = emailCursor.getString(emailCursor.getColumnIndex(Email.DATA));
				}
				emailCursor.close();
				
				result.put("name", display_name != null ? display_name : JSONObject.NULL);
				result.put("phones", phones != null ? phones : JSONObject.NULL);
				result.put("selected_phone", selectedPhone != null ? selectedPhone : JSONObject.NULL);
				result.put("primary_phone", primaryPhone != null ? primaryPhone : JSONObject.NULL);
				result.put("email", email != null ? email : JSONObject.NULL);
			}
			contactsCursor.close();
		}
	}
	
	/**
	 * Used to share result between gui and javascript threads
	 */
	static class MethodResult {
		public Exception exception;
		
		public Object resultObject;

		public boolean hasError() {
			return exception != null;
		}
	}
}
