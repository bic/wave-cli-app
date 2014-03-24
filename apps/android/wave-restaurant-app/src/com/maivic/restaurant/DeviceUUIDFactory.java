package com.maivic.restaurant;

import java.io.UnsupportedEncodingException;
import java.util.UUID;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;

public class DeviceUUIDFactory {

	protected static final String PREFS_FILE = "device_id.xml";
	protected static final String PREFS_DEVICE_ID = "device_id";
	protected static final String PREFS_UUID_FACTORY_METHOD = "";
	protected volatile static UUID uuid;
	public static final byte UUID_ANDROID_ID = 'I';
	public static final byte UUID_TELEPHONY_SERVICE = 'T';
	public static final byte UUID_RANDOM = 'R';
	
	private byte factoryMethodByte;

	public DeviceUUIDFactory(Context context) {
		
		if (uuid == null) {
			synchronized (DeviceUUIDFactory.class) {
				if (uuid == null) {
					final SharedPreferences prefs = context
							.getSharedPreferences(PREFS_FILE, 0);
					final String id = prefs.getString(PREFS_DEVICE_ID, null);
					final Byte fm = (byte) prefs.getInt(PREFS_UUID_FACTORY_METHOD, Byte.MIN_VALUE);
					if (id != null) {
						// Use the ids previously computed and stored in the
						// prefs file
						
						factoryMethodByte = fm;
						uuid = UUID.fromString(id);
					} else {
						final String androidId = Secure
								.getString(context.getContentResolver(),
										Secure.ANDROID_ID);
						// Use the Android ID unless it's broken, in which case
						// fallback on deviceId,
						// unless it's not available, then fallback on a random
						// number which we store to a prefs file
						try {
							if (!"9774d56d682e549c".equals(androidId)) {

								uuid = UUID.nameUUIDFromBytes((androidId)
										.getBytes("utf8"));
								
								factoryMethodByte = UUID_ANDROID_ID;
							} else {
								final String deviceId = ((TelephonyManager) context
										.getSystemService(Context.TELEPHONY_SERVICE))
										.getDeviceId();

								if (deviceId != null) {
									uuid = UUID.nameUUIDFromBytes((deviceId)
											.getBytes("utf8"));
									
									factoryMethodByte = UUID_TELEPHONY_SERVICE;
								} else {

									uuid = UUID.randomUUID();
									
									factoryMethodByte = UUID_RANDOM;
								}
							}
						} catch (UnsupportedEncodingException e) {
							throw new RuntimeException(e);
						}
						// Write the value out to the prefs file
						prefs.edit()
								.putString(PREFS_DEVICE_ID, uuid.toString())
								.putInt(PREFS_UUID_FACTORY_METHOD,
										factoryMethodByte).commit();
					}
				}
			}
		}
	}

	public UUID getDeviceUuid() {

		return uuid;
	}


	public byte getUuidFactoryMethodByte() {
		return this.factoryMethodByte;
	}

}