package com.maivic.android.app.utils;

import android.util.Log;

public class Tracer {
	
	public static void traceMethod(String methodName, Object ...args){
		traceMethod("tracer", methodName, args);
	}
	
	public static final void traceMethod(String tag, String methodName, Object...args){
		StringBuilder signature = new StringBuilder();
		signature.append(methodName + "(");
		if(args != null && args.length > 0){
			for(int i = 0; i < args.length; i++){
				signature.append(args[i]);
				if (i != args.length - 1) {
					signature.append(", ");
				}
			}
		}
		signature.append(")");
		Log.i(tag, "call method " + signature.toString());
	}
	
	public static final void traceMethod(Object target, String methodName, Object ... args){
		traceMethod(target.getClass().getName() + "."+methodName, args);
	}
}
