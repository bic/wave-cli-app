package com.maivic.android.app;

import java.util.concurrent.ExecutionException;

import net.maivic.com.netty.NettyClientConnector;
import net.maivic.comm.Transport;
import net.maivic.comm.TransportManager;
import net.maivic.comm.RPC.LocationQuery;
import net.maivic.comm.impl.RPCHandlerManager;
import net.maivic.context.Context;
import net.maivic.protocol.Model.Location;
import android.os.Bundle;
import android.view.View;

import com.maivic.android.app.custom.locationpicker.DatasetModel;
import com.maivic.android.app.custom.locationpicker.LocationPicker;

public class ProfileActivity extends BaseActivity {

	LocationPicker locationPicker1;
	LocationPicker locationPicker2;
	
	DatasetModel datasetModel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		locationPicker1 = (LocationPicker) findViewById(R.id.locationPicker1);
		locationPicker2 = (LocationPicker) findViewById(R.id.locationPicker2);
		
		datasetModel = new DatasetModel();
		
		locationPicker1.setDatasetModel(datasetModel);
		locationPicker2.setDatasetModel(datasetModel);
		
		
        TransportManager  m = Context.get().getTransportManager();
        try {
                Transport transport =m.addTransport("nettytcp://localhost");
                transport.pullUp();
                NettyClientConnector net;
        } catch(Throwable t) {
                t.printStackTrace();
        }
        LocationQuery locationQuery = RPCHandlerManager.getHandler(LocationQuery.class);
        Location location;
        try {
                location = locationQuery.getLocation(1.0, 2.0, 150).get();
//                System.out.println(location);
        } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        } catch (ExecutionException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
        }
	}

	@Override
	protected View getContentView() {
		return getLayoutInflater().inflate(R.layout.activity_profile, null);
	}
}
