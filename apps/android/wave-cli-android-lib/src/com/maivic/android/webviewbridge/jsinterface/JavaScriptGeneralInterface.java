package com.maivic.android.webviewbridge.jsinterface;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class JavaScriptGeneralInterface extends JavaScriptRegistrarBase{

	private WebView mWebView;
	
	@Override
	public void register(WebView webView, String javascriptHandlerName) {
		super.register(webView, javascriptHandlerName);
		
		this.mWebView = webView;
	}
	
	public void setActivity(Activity activity){
		this.mActivity = activity;
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
	
	
	@JavascriptInterface
	public void hideMe(){
//		if(context instanceof GuiCallbackInterface){
//			((GuiCallbackInterface)context).hideWebView();
//		}
		
		if(mWebView != null){
			mWebView.setVisibility(View.GONE);
		}
	}
}
