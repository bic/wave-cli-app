package com.maivic.android.webviewbridge;

import android.webkit.WebView;

public interface  JavaScriptCallbackRegistrar {
	public void register(WebView webView, String javascriptHandlerName);
}
