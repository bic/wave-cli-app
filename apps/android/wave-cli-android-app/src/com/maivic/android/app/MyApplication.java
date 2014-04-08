package com.maivic.android.app;

import net.maivic.context.Context;
import net.maivic.context.UnsupportedType;
import net.maivic.context.WrongType;
import android.app.Application;
import android.os.Build;

import com.maivic.android.app.api.AndroidPlatformSupport;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		if(Build.VERSION.SDK_INT > Build.VERSION_CODES.FROYO){
			// now register only for android > 8
			registerPlatformSupport();			
			
		} else{
			System.err.print("Android SDK <= 8. Update netty libraries!!!");
		}
		
	}

	private void registerPlatformSupport() {
		try {
			Context.get().register("PlatformSupport",
					new AndroidPlatformSupport());
		} catch (WrongType e) {
			e.printStackTrace();
		} catch (UnsupportedType e) {
			e.printStackTrace();
		}

	}

}
