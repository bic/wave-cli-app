package com.maivic.android.webviewbridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;

import android.webkit.WebView;

import com.maivic.android.webviewbridge.jsinterface.JavaSctiptBinderFactoryImpl;

public class WebviewBridge {
	private WebView webView;
	
	private JavaScriptBinderFactory factory;
	
	public WebviewBridge(WebView webView, JavaScriptBinderFactory binderFactory){
		this.webView = webView;
		this.factory = binderFactory;
	}
	
//	public WebviewBridge(WebView webView){
//		this(webView, new JavaSctiptBinderFactoryImpl());
//	}

	public void registerSmartPhoneFunctionRequests(String functionsInJSON){
		// First decode json
		List<String[]> functions = decodeJSONFromSmartPhoneFunctionRequests(functionsInJSON);
		
		if(functions == null){
			// can't decode jsonFunctions!
			return;
		}
		
		// Remember already mapped functions as to not register repeatedly the same function using the same name
		Map<Class<? extends JavaScriptCallbackRegistrar>, Object[]> instantiatedJavaScriptInterfaceClasses = new HashMap<Class<? extends JavaScriptCallbackRegistrar>, Object[]>();
		
		//See JavaScriptBinderFactory definition below
		for(String[] func : functions){
			// Iterate trough functions containing tuples ['Javascript object name','JSfunction mapped to Java method']
			
			// objects[0] = factory name, objects[1] is the Class offering the public callback
			Object[] objects = new Object[]{ func[1], instantiatedJavaScriptInterfaceClasses.get(factory.getRegistrarClass(func[1]))}; 
			if(objects[1] == null || (!((String)objects[0]).equals(func[0]))){
				JavaScriptCallbackRegistrar reg;
				
				if(objects[1] == null) {
					
					// Callback not registered
					try {
						reg = factory.create(func[1]);
					} catch (IllegalStateException e) {
						// The application not supported method func[1], just continue
						e.printStackTrace();
						continue;
					}
				}else if(!((String)objects[0]).equals(func[0])){
					// Callback Registered, but under different name
					reg = (JavaScriptCallbackRegistrar) objects[1]; 
				} else {
					// i think there never will be
					continue; // Callback already registered
				}
				reg.register(webView, func[0]);
				instantiatedJavaScriptInterfaceClasses.put(reg.getClass(), new Object[]{func[0], reg});
			}
	    }
	}

	private List<String[]> decodeJSONFromSmartPhoneFunctionRequests(
			String functionsInJSON) {
		try {
			JSONArray jsonArray = new JSONArray(functionsInJSON);
			List<String[]> res = new ArrayList<String[]>();
			if(jsonArray.length() > 0){
				for(int i = 0; i < jsonArray.length(); i++){
					JSONArray  tuple = jsonArray.getJSONArray(i);
					res.add(new String[]{tuple.getString(0), tuple.getString(1)});
				}
			}
			
			return res;
			
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}
}
