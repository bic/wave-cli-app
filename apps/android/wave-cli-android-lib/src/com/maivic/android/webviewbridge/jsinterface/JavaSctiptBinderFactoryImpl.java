package com.maivic.android.webviewbridge.jsinterface;

import java.util.HashMap;
import java.util.Map;

import android.app.Activity;

import com.maivic.android.webviewbridge.JavaScriptBinderFactory;
import com.maivic.android.webviewbridge.JavaScriptCallbackRegistrar;

public class JavaSctiptBinderFactoryImpl implements JavaScriptBinderFactory{

	private static final Map<String, Class<? extends JavaScriptCallbackRegistrar>> registrarMap = new HashMap<String, Class<? extends JavaScriptCallbackRegistrar>>();
	
	static{
		registrarMap.put("getContacts", JavaScriptContactsInterface.class);
		registrarMap.put("hideMe", JavaScriptGUIInterface.class);
	}
	
	private Activity activity;
	
	public JavaSctiptBinderFactoryImpl(Activity activity){
		this.activity = activity;
	}
	
	@Override
	public JavaScriptCallbackRegistrar create(String methodName) {
		
		Class<? extends JavaScriptCallbackRegistrar> clazzRegistrar = getRegistrarClass(methodName);
		if(clazzRegistrar != null){
			try {
				
				JavaScriptCallbackRegistrar registrar = clazzRegistrar.newInstance();
				if(registrar instanceof JavaScriptRegistrarBase){
					((JavaScriptRegistrarBase)registrar).setActivityContext(activity);
				}

				return registrar;
			} catch (Exception e) {
				throw new IllegalStateException("Can't create instance of registrar "+clazzRegistrar.getName(), e);
			}
		} else{
			throw new IllegalStateException("Can't find JavaScriptCallbackRegistrar for method " + methodName);
		}
	}

	@Override
	public Class<? extends JavaScriptCallbackRegistrar> getRegistrarClass(
			String methodName) {
		return registrarMap.get(methodName);
	}

}
