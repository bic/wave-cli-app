package com.maivic.android.webviewbridge.jsinterface;

import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.webkit.WebView;

import com.maivic.android.webviewbridge.JavaScriptCallbackRegistrar;

public class JavaScriptRegistrarBase implements JavaScriptCallbackRegistrar{
	// Confirmation codes
	/** No confirmation requested */
	public static final int CONFIRMATION_NO = 0;
	 
	/** Blocking wait */
	public static final int CONFIRMATION_BLOCKING_WAIT = 1;
	
	/** Delivery confirmation requested */
	public static final int CONFIRMATION_DELIVERY_REQUESTED = 2;
	// ~Confirmation

	protected Activity mActivity;
	
	@SuppressLint("JavascriptInterface")
	public void register(WebView webView, String javascriptHandlerName){
		webView.addJavascriptInterface(this, javascriptHandlerName);
	}
	
	/**
	 * This method validate json arguments
	 * 
	 * @param args
	 * @param expectedArgs
	 * @throws IllegalStateException
	 */
	public void validateArguments(JSONObject args, String [] expectedArgs) throws IllegalArgumentException{
		
		if(expectedArgs != null && expectedArgs.length > 0){
			for(String expArg: expectedArgs){
				if(!args.has(expArg)){
					throw new IllegalArgumentException("Expect argument "+expArg +", but it not in "+args.toString());
				}
			}
		}
	}
	
	/**
	 * Call this method, to release javascripthandler.
	 */
	public void release(){}
	
	private String generateStackTrace(Exception e){
		return "";
	}
	
	public String generateErrorResponse(Exception e){
		JSONArray result = new JSONArray();
		result.put(JSONObject.NULL);
		JSONObject errorObject = new JSONObject();
		try {
			errorObject.put("message", e.getMessage() == null ? JSONObject.NULL : e.getMessage());
			errorObject.put("stacktrace", generateStackTrace(e));
		} catch (JSONException e1) {
			e1.printStackTrace();
		}
		result.put(errorObject);
		return result.toString();
	}
	
	public String generateSuccessResponse(Object result){
		JSONArray array = new JSONArray();
		array.put(result);
		return array.toString();
	}
	
	public String generateReferer(){
		String uuid = UUID.randomUUID().toString();
		return uuid;
	}
	
	/**
	 * Call this method, to set activity
	 * 
	 * @param activity
	 *            activity
	 */
	public void setActivityContext(Activity activity){
		this.mActivity = activity;
	}
}
