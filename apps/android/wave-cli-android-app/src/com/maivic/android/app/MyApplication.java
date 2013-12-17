package com.maivic.android.app;

import net.maivic.context.Context;
import net.maivic.context.UnsupportedType;
import net.maivic.context.WrongType;
import android.app.Application;

import com.maivic.android.app.api.AndroidPlatformSupport;

public class MyApplication extends Application {

	@Override
	public void onCreate() {
		super.onCreate();
		
		registerPlatformSupport();
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
