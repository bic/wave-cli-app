package com.maivic.android.webviewbridge.jsinterface;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
import android.webkit.JavascriptInterface;


/**
 * Javascript Back End interface. This class implements list of functions
 * declared in specification.
 * 
 * @author Serghei
 * 
 */
public class JavaScriptBEInterface extends JavaScriptRegistrarBase{
	/**
	 * Default constructor
	 */
	public JavaScriptBEInterface(){
		
	}

	@JavascriptInterface
	public String log(String json){
		//TODO add level
		try {
			JSONObject args = new JSONObject(json);
			validateArguments(args, new String[]{"msg", "level", "domain"});
					
			String msg = args.getString("msg");
			String level = args.getString("level"); 
			String domain = args.getString("domain");
			Log.d(domain, msg);
			
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return generateErrorResponse(e);
			
		} catch (JSONException e) {
			e.printStackTrace();
			return generateErrorResponse(e);
		}
		
		return generateSuccessResponse(null);
	}
}

