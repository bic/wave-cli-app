package com.maivic.android.webviewbridge.jsinterface;

import android.app.Activity;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;

public class JavaScriptGUIInterface extends JavaScriptRegistrarBase{

	private WebView mWebView;
	
//	@Override
//	public void register(WebView webView, String javascriptHandlerName) {
//		this.mWebView = webView;
//		webView.addJavascriptInterface(this, javascriptHandlerName);
//	}
	
	public void setActivity(Activity activity){
		this.mActivity = activity;
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
