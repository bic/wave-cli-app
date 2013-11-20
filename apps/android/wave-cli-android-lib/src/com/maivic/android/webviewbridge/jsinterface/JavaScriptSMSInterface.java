package com.maivic.android.webviewbridge.jsinterface;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.telephony.SmsManager;
import android.util.Log;
import android.webkit.JavascriptInterface;

/**
 * Javascript interface for transmission sms
 * 
 * @author Serghei
 * 
 */
public class JavaScriptSMSInterface extends JavaScriptRegistrarBase{
	private static final String T = "JavaScriptSMSInterface";
	
	// SMS error codes
	public static final int RESULT_OK = 0;
	public static final int RESULT_ERROR_GENERIC_FAILURE = 1;
	public static final int RESULT_ERROR_RADIO_OFF = 2;
	public static final int RESULT_ERROR_NULL_PDU = 3;
	public static final int RESULT_DELIVERY_IN_PROCESS = 4;
	// ~SMS error codes

	private static final String ACTION_SMS_SEND = "action_sms_send";
	private static final String EXTRA_REFERER = "extra_referer";
	private static final String EXTRA_MULTIPART_FLAG = "extra_multipart_flag";
	
	private ConcurrentHashMap<String, ThreadBlocker> mThreadBlockerMap = new ConcurrentHashMap<String, JavaScriptSMSInterface.ThreadBlocker>();
	private ConcurrentHashMap<String, DeliveryResultEntry> mSMSDeliveryResultCodes = new ConcurrentHashMap<String, DeliveryResultEntry>();
	
	/**
	 * Function to send a text SMS to a supplied number from the user's phone.
	 * The text might be longer than 165 characters in which case a multipart
	 * text sms should be sent. Blocking wait returns an error/success code
	 * exactly as checkSMSDelivery
	 * 
	 * @param phoneNumber
	 * @param textMessage
	 *            url encoded text message
	 * @param confirmation
	 *            confirmation value 0 - no confirmation, 1 - blocking wait, 2 -
	 *            delivery confirmation request
	 * @return ref: Only returned if confirmation=2. The ref vallue can be used
	 *         with checkSMSDelivery
	 * @see {@linkplain JavaScriptLocationInterface#sendDataSMS(String phoneNumber, int port, String message, int confirmation)}
	 */
	@JavascriptInterface
	public String sendTextSMS(String json){
		try {
			JSONObject args = new JSONObject(json);
			validateArguments(args, new String[]{"phone_number", "text_message", "confirmation"});
			
			String phoneNumber = args.getString("phone_number");
			String textMessage = args.getString("text_message");
			int confirmation = args.getInt("confirmation");
			
			return sendSMSInternal(phoneNumber, textMessage, confirmation, false, (short)0);
		} catch (IllegalArgumentException e) {
			return generateErrorResponse(e);
			
		} catch (JSONException e) {
			return generateErrorResponse(e);
		}
	}
	
	/**
	 * Function to send data SMS to a supplied number using the specified port.
	 * The data itself is URLencoded
	 * 
	 * @param phoneNumber
	 * @param port
	 * @param textMessage
	 *            url encoded message
	 * @param confirmation
	 *            0=no confirmation requested 1=blocking wait 2=delivery
	 *            confirmation requested
	 * @return ref: Only returned if confirmation=2. The ref vallue can be used
	 *         with checkSMSDelivery
	 * @see {@link JavaScriptLocationInterface#sendTextSMS(String phoneNumber, String textMessage, int confirmation)}
	 * 
	 */
	public String sendDataSMS(String json/*String phoneNumber, int port, String textMessage, int confirmation*/){
		
		try {
			JSONObject args = new JSONObject(json);
			
			validateArguments(args, new String[]{"phone_number", "port", "text_message", "confirmation"});
			
			String phoneNumber = args.getString("phone_number"); 
			String textMessage = args.getString("text_message");
			short port = (short) args.getInt("port");
			int confirmation = args.getInt("confirmation");
			
			return sendSMSInternal(phoneNumber, textMessage, confirmation, true, port);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return generateErrorResponse(e);
			
		} catch (JSONException e) {
			e.printStackTrace();
			return generateErrorResponse(e);
		}
	}
	
	/**
	 * Call this method to send text sms or data sms
	 * 
	 * @param phoneNumber
	 *            destination phone number
	 * @param textMessage
	 *            urlecoded message
	 * @param confirmation
	 *            confirmation value. 0=no confirmation requested 1=blocking
	 *            wait 2=delivery confirmation requested
	 * @param dataSMS
	 *            if dataSMS equals true, then send data sms and text sms in
	 *            other case
	 * @param port
	 *            port used for data sms and ingnored for text sms type
	 * @return json encoded result, depended of confirmation
	 */
	private String sendSMSInternal(String phoneNumber, String textMessage, int confirmation, boolean dataSMS, short port){
		
		Log.i(T, "call sendSMSInternal("+phoneNumber +", "+textMessage+", "+confirmation+", "+dataSMS+", "+port+")");
		try {
			SmsManager smsManager = SmsManager.getDefault();
			String referer = generateReferer();
			boolean multipartSMS = false;
			
			ArrayList<String> dividedMessages = null;
			ArrayList<PendingIntent> sentIntents = null;
			if(!dataSMS){
				// divide text message if it is big
				dividedMessages = smsManager.divideMessage(textMessage);	
				if(dividedMessages.size() > 1){
					multipartSMS = true;
					sentIntents = new ArrayList<PendingIntent>();
					for(int i = 0; i < dividedMessages.size(); i++){
						Intent broadcastIntent = new Intent(ACTION_SMS_SEND);
						broadcastIntent.putExtra(EXTRA_MULTIPART_FLAG, true);
						PendingIntent pending = PendingIntent.getBroadcast(mActivity, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
						sentIntents.add(pending);
					}
				}
			}
						
			// pending intent for single sms
			Intent broadcastIntent = new Intent(ACTION_SMS_SEND);
			broadcastIntent.putExtra(EXTRA_REFERER, referer);
			PendingIntent sentIntent = PendingIntent.getBroadcast(mActivity, 0, broadcastIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//			PendingIntent deliveryPendingIntent = sentPendingIntent;

			// register delivery receiver
			new SMSDeliveryReceiver();
			
			int partsCount;
			DeliveryResultEntry resultEntry;
			switch (confirmation) {
			case CONFIRMATION_NO:
				if(dataSMS){
					smsManager.sendDataMessage(phoneNumber, null, port, getDataSMS(textMessage), null, null);
				} else{
					if(multipartSMS){
						smsManager.sendMultipartTextMessage(phoneNumber, null, dividedMessages, null, null);
					} else{
						smsManager.sendTextMessage(phoneNumber, null, textMessage, null, null);					
					}
				}
				break;

			case CONFIRMATION_DELIVERY_REQUESTED:
				partsCount = multipartSMS ? dividedMessages.size() : 1;
				resultEntry = new DeliveryResultEntry( partsCount, RESULT_DELIVERY_IN_PROCESS);
				mSMSDeliveryResultCodes.put(referer, resultEntry);
				
				if(dataSMS){
					smsManager.sendDataMessage(phoneNumber, null, port, getDataSMS(textMessage), sentIntent, null);
					
				} else{
					if(multipartSMS){
						smsManager.sendMultipartTextMessage(phoneNumber, null, dividedMessages, sentIntents, null);
					} else{
						smsManager.sendTextMessage(phoneNumber, null, textMessage, sentIntent, null);											
					}
				}
				
				return generateSuccessResponse(referer);

			case CONFIRMATION_BLOCKING_WAIT:
				partsCount = multipartSMS ? dividedMessages.size() : 1;
				resultEntry = new DeliveryResultEntry( partsCount, RESULT_DELIVERY_IN_PROCESS);
				mSMSDeliveryResultCodes.put(referer, resultEntry);

				ThreadBlocker threadBlocker = new ThreadBlocker();
				mThreadBlockerMap.put(referer, threadBlocker);
				
				if(dataSMS){
					smsManager.sendDataMessage(phoneNumber, null, port, getDataSMS(textMessage), sentIntent, null);
				} else {
					if(multipartSMS){
						smsManager.sendMultipartTextMessage(phoneNumber, null, dividedMessages, sentIntents, null);
					} else{
						smsManager.sendTextMessage(phoneNumber, null, textMessage, sentIntent, null);
					}
				}
				
				// block thread until get response
				threadBlocker.blockThread();
				
				resultEntry = mSMSDeliveryResultCodes.remove(referer);
				return generateSuccessResponse(resultEntry.statusCode);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			return generateErrorResponse(e);
		}			
					
		return generateSuccessResponse(JSONObject.NULL);
	}
	
	private byte [] getDataSMS(String smsMessage){
		return smsMessage.getBytes();
	}
	
	/**
	 * Checks whether a SMS sent using sendDataSMS or sendTextSMS has been sent
	 * 
	 * @param referrer
	 *            A referrer as supplied by the send sms functions
	 * @return One of the error/success codes below:
	 *         <ul>
	 *         <li>0=RESULT_OK</li>
	 *         <li>1=RESULT_ERROR_GENERIC_FAILURE</li>
	 *         <li>2=RESULT_ERROR_RADIO_OFF</li>
	 *         <li>3=RESULT_ERROR_NULL_PDU</li>
	 *         </ul>
	 * @see {@linkplain JavaScriptLocationInterface#sendDataSMS(String, int, String, int)}
	 *      {@link JavaScriptLocationInterface#sendTextSMS(String, String, int)}
	 */
	public String checkSMSDelivery(String json){
		
		try {
			JSONObject args = new JSONObject(json);
			validateArguments(args, new String[]{"referrer"});

			String referrer = args.getString("referrer");
			if(referrer != null && referrer.length() > 0){
				DeliveryResultEntry resultEntry = mSMSDeliveryResultCodes.remove(referrer);
				if(resultEntry != null){
					// remove delivery code, to release resource
					return generateSuccessResponse(resultEntry.statusCode);
				} else{
					return generateErrorResponse(new IllegalStateException("Bad referrer value: "+referrer));
				}
			}

			return generateErrorResponse(new IllegalStateException("Bad referrer value: "+referrer));
		} catch (JSONException e) {
			e.printStackTrace();
			return generateErrorResponse(e);
			
		} catch (IllegalArgumentException e) {
			return generateErrorResponse(e);
		}
	}
	
	/**
	 * This receiver used to sms delivery
	 * 
	 * @author Serghei
	 *
	 */
	class SMSDeliveryReceiver extends BroadcastReceiver{
		
		public SMSDeliveryReceiver(){
			registerReceiverSelf();
		}
		
		@Override
		public void onReceive(Context context, Intent intent) {
			Log.d(T, "SMSDeliveryReceiver.onReceive, intent:"+intent);
			String referrer = intent.getStringExtra(EXTRA_REFERER);
			
			boolean multiPartsSMS = intent.getBooleanExtra(EXTRA_MULTIPART_FLAG, false);
			
			int genericResultCode = getResultCode();
			int resultCode;
			
            switch (genericResultCode){
            case Activity.RESULT_OK:                      
//            	Toast.makeText(mActivity, "SMS sent",Toast.LENGTH_LONG).show();
            	resultCode = RESULT_OK;
            	break;
            	
            case SmsManager.RESULT_ERROR_GENERIC_FAILURE:  
//            	Toast.makeText(mActivity, "Generic failure",Toast.LENGTH_LONG).show();
            	resultCode = RESULT_ERROR_GENERIC_FAILURE;
                break;
                
            case SmsManager.RESULT_ERROR_NO_SERVICE:       
//            	Toast.makeText(mActivity, "No service",Toast.LENGTH_LONG).show();
            	resultCode = RESULT_ERROR_GENERIC_FAILURE;
            	break;
            	
            case SmsManager.RESULT_ERROR_NULL_PDU:         
//            	Toast.makeText(mActivity, "Null PDU",Toast.LENGTH_LONG).show();
            	resultCode = RESULT_ERROR_NULL_PDU;
            	break;
            	
            case SmsManager.RESULT_ERROR_RADIO_OFF:        
//            	Toast.makeText(mActivity, "Radio off",Toast.LENGTH_LONG).show();
            	resultCode = RESULT_ERROR_RADIO_OFF;
            	break;
            	
            default: 
            	// unknown error
//            	Toast.makeText(mActivity, "Unknown error status: " + genericResultCode, Toast.LENGTH_LONG).show();
            	resultCode = RESULT_ERROR_GENERIC_FAILURE;
            }
			
            // update status map
            DeliveryResultEntry entry =  mSMSDeliveryResultCodes.get(referrer);
            if(entry != null){
            	synchronized (entry) {
                    if(multiPartsSMS){
                    	entry.partsCount--;
                    	if(entry.partsCount > 0){
                    		// already send some part of sms
                    		// remember last error
                    		if(entry.lastErrorCode == RESULT_OK){
                    			entry.lastErrorCode = resultCode;
                    		}
                    	} else{
                    		// already send last part of sms
                    		// return in statusCode last error or resultCode
                    		if(resultCode != RESULT_OK){
                    			entry.statusCode = resultCode;
                    		} else{
                    			entry.statusCode = entry.lastErrorCode;
                    		}
                    	}
                    } else{
                		entry.statusCode = resultCode;                    	
                    }
				}
            }
            
			ThreadBlocker threadBlocker = mThreadBlockerMap.get(referrer);
			if(threadBlocker != null){
				threadBlocker.unblockThread();
				mThreadBlockerMap.remove(referrer);
			}
			
			unregisterReceiverSelf();
		}
		
		private void registerReceiverSelf(){
			IntentFilter filter = new IntentFilter(ACTION_SMS_SEND);
			mActivity.registerReceiver(this, filter);
		}
		
		private void unregisterReceiverSelf(){
			mActivity.unregisterReceiver(this);
		}
	}
	
	interface ResultCallback{
		public void onResult(Object result);
	}
	
	static class DeliveryResultEntry{
		int statusCode;
		
		int partsCount;
		
		int lastErrorCode = RESULT_OK;
		
		public DeliveryResultEntry(int partsCount, int statusCode){		
			this.partsCount = partsCount;
			this.statusCode = statusCode;
		}
		
		public DeliveryResultEntry(int partsCount){		
			this(partsCount, 0);
		}
		
		public boolean hasErorr(){
			return lastErrorCode != RESULT_OK;
		}
		
		public boolean isMultipartsSMS(){
			return partsCount > 1; 
		}
	}
	
	class ThreadBlocker{
		private Object semaphore = new Object();
		private ResultCallback resultCalback;
		
		public void blockThread(){
			blockThread(null);
		}
		
		public void blockThread(ResultCallback resultCallback){
			this.resultCalback = resultCallback;
			
			synchronized (semaphore) {
				try {
					semaphore.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		public void unblockThread(){
			synchronized (semaphore) {
				semaphore.notify();
			}
		}
		
		public void unblockThreadWithResult(Object result){
			if(resultCalback != null){
				resultCalback.onResult(result);
			}
			unblockThread();
		}
	}
}
