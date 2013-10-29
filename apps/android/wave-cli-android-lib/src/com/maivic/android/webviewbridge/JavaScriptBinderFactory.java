package com.maivic.android.webviewbridge;

public interface JavaScriptBinderFactory {
	
	public JavaScriptCallbackRegistrar create(String methodName) throws IllegalStateException;

	public Class<? extends JavaScriptCallbackRegistrar> getRegistrarClass(
			String methodName);
}
