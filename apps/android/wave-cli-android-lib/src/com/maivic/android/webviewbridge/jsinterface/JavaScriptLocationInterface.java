package com.maivic.android.webviewbridge.jsinterface;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.location.LocationManager;
import android.webkit.JavascriptInterface;


/**
 * Javascript Back End interface. This class implements list of functions
 * declared in specification.
 * 
 * @author Serghei
 * 
 */
public class JavaScriptLocationInterface extends JavaScriptRegistrarBase{
	/**
	 * Default constructor
	 */
	public JavaScriptLocationInterface(){
		
	}

	@JavascriptInterface
	public String getCurrentLocation(String json){
		
		try {
			JSONObject args = new JSONObject(json);
			validateArguments(args, new String []{});
		
			LocationManager locationManager = (LocationManager) mActivity.getSystemService(Context.LOCATION_SERVICE);
			locationManager.getAllProviders();
//			locationManager.
			//TODO
			
			JSONObject result = new JSONObject();
			result.put("longitude", "");
			result.put("latitude", "");
			result.put("time", "");
			result.put("precision", "");
			result.put("method", "");
			
			return generateSuccessResponse(result);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return generateErrorResponse(e);
			
		} catch (JSONException e) {
			e.printStackTrace();
			return generateErrorResponse(e);
			
		} catch(Exception e){
			e.printStackTrace();
			return generateErrorResponse(e);
		}
		
	}
}

