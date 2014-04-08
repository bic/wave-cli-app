package platform;



import java.util.Arrays;
import java.util.UUID;

import android.content.Context;

import com.maivic.restaurant.DeviceUUIDFactory;
import com.maivic.restaurant.OfferActivity;

import net.maivic.comm.PlatformSupport;

public class RestaurantPlatformSupport implements PlatformSupport {

	private DeviceUUIDFactory uuidFactory;
	private byte[] uuidbytes;

	@Override
	public byte[] getUUID() {
		// TODO Auto-generated method stub
		
		return uuidFactory.getDeviceUuid().toString().getBytes(); 
	}
	
	public RestaurantPlatformSupport(Context context){
		this.uuidFactory = new DeviceUUIDFactory(context);
		byte b1=uuidFactory.getUuidFactoryMethodByte();
		String uuidStr = this.uuidFactory.getDeviceUuid().toString();
		this.uuidbytes=  new byte[uuidStr.length()+1];
		this.uuidbytes[0] = b1;
		byte[] origuuidbytes = uuidStr.getBytes();
		for(int i=1; i<=origuuidbytes.length; i++ ) {
			this.uuidbytes[i]=origuuidbytes[i-1];
		}
		
	}

}
