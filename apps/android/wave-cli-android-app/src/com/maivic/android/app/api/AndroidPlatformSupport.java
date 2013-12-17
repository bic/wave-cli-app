package com.maivic.android.app.api;

import java.util.UUID;

import net.maivic.comm.PlatformSupport;

public class AndroidPlatformSupport implements PlatformSupport{
	private static final UUID mUid = UUID.randomUUID();
	
	@Override
	public byte[] getUUID() {
		
		return mUid.toString().getBytes();
	}

}
