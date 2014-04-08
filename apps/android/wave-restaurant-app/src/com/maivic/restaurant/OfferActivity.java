package com.maivic.restaurant;

import java.util.ArrayList;
import java.util.List;

import net.maivic.comm.Transport;
import net.maivic.protocol.Model.Notification;
import net.maivic.protocol.Model.NotificationOrBuilder;
import net.maivic.comm.RPC.LocationQuery;
import net.maivic.context.EntityAlreadyRegistered;
import net.maivic.context.UnsupportedType;
import net.maivic.context.WrongType;
import platform.RestaurantPlatformSupport;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment.SavedState;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Spinner;

import com.example.restaurantapp.R;
import com.maivic.android.utils.AndroidLogger;
import com.maivic.restaurant.data.ActivityDataHolder;
import com.maivic.restaurant.data.LocalOfferOptionDistributor;
import com.maivic.restaurant.data.NotificationRegistry;
import com.maivic.restaurant.data.OfferContent;
import com.maivic.restaurant.data.OfferOptionsContent;

public class OfferActivity extends BaseActivity implements ActivityDataHolder {
	// Fragment TabHost as mTabHost

	Context context;
	Notification.Builder notif1 = Notification.newBuilder();
	Notification.Builder notif2 = Notification.newBuilder();
	Notification.Builder notif3 = Notification.newBuilder();
	Notification.Builder notif4 = Notification.newBuilder();
	Notification.Builder notif5 = Notification.newBuilder();
	Notification.Builder notif6 = Notification.newBuilder();
	
	private Handler mHandler = new Handler();
	
	static final String EXTRA_DATA = "data_for_intent";
	private Transport transport;
	private LocationQuery locationQuery;
	private List<NotificationOrBuilder> notifList = new ArrayList<NotificationOrBuilder>();
	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		context = getApplicationContext();
		setContentView(R.layout.activity_main);

		setupNetwork();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setupActivityDataHolder();
		
		
		/*mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mHandler.postDelayed(this, 5000);
				notif1.setId(1);
				notif1.setName("Prima notificare");
				messageContent.setreceivedValue(notif2);
				notif2.setId(2);
				notif2.setName("Mesaj de informare");
				messageContent.setreceivedValue(notif1);
				notif3.setId(3);
				notif3.setName("Panic: I am in Crimea");
				
				
				messageContent.setreceivedValue(notif3);
				mHandler.postDelayed(this, 5000);
			}
		}, 5000);*/
		
		
		Runnable r = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				notif1.setId(1);
				notif1.setName("Prima notificare");
				//messageContent.setreceivedValue(notif1);
				notifList.add(notif1);
				
				notif2.setId(2);
				notif2.setName("Mesaj de informare");
				notifList.add(notif2);
				//messageContent.setreceivedValue(notif2);
				notif3.setId(3);
				notif3.setName("Panic: I am in Crimea");
				notifList.add(notif3);
				
				notif4.setId(4);
				notif4.setName("Ceva nu e ok");
				notifList.add(notif4);
				
				notif5.setId(5);
				notif5.setName("Toate bune");
				notifList.add(notif5);
				
				notif6.setId(6);
				notif6.setName("Livratorul este in intarziere");
				notifList.add(notif6);
				
				
				messageContent.setreceivedValues(notifList);
				//mHandler.postDelayed(this, 5000);
				
			}
		};
		mHandler.postDelayed(r, 5000);
		
		
	/*	Runnable r2 = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				
				notif4.setId(1);
				notif4.setName("Ceva nu e ok");
				notifList.add(notif4);
				
				notif5.setId(2);
				notif5.setName("Toate bune");
				notifList.add(notif5);
				
				notif6.setId(3);
				notif6.setName("Livratorul este in intarziere");
				notifList.add(notif6);
				
				
				messageContent.setreceivedValues(notifList);
				
			}
		};
		mHandler.postDelayed(r2, 15000);
*/		
	}

	/*
	 * @Override public List<DataContent> getDataContent() { // TODO
	 * Auto-generated method stub return this.myOffer; }
	 */

	public void controlPanel(View view){
		Intent myIntent = new Intent(this, ControlPanelMainActivity.class);
		Spinner zona = (Spinner) findViewById(R.id.location_spinner);
		String valueToSend = zona.getSelectedItem().toString();
		myIntent.putExtra(EXTRA_DATA, valueToSend);
		startActivity(myIntent);
		
	}
	
	

	public void setupNetwork() {
		// String url = "nettytcp://api.wavetheapp.com";

		String url = "nettytcp://5.9.137.187";
		net.maivic.context.Context c = net.maivic.context.Context.get();
		try {
			try {
				c.register("Log", new AndroidLogger(),true);
			} catch (EntityAlreadyRegistered e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			c.register("PlatformSupport", new RestaurantPlatformSupport(this));
		} catch (WrongType e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedType e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.transport = c.getTransportManager().addTransport(url);
		transport.pullUp();

	}
/*
	public OfferActivity() {
		// try {
	
		setupNetwork();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		setupActivityDataHolder();
	}*/
		/*
		 * Context.get().register("Log", new AndroidLogger(), true); Log log =
		 * Context.get().log(); this.locationQuery =
		 * RPCHandlerManager.getHandler(LocationQuery.class); Location location
		 * = locationQuery.getLocation(1, 1, 1).get();
		 * log.e("LOC",location.toString() ); List<Offer> offers =
		 * locationQuery.getCurrentActiveOffers(location).get(); OfferRelations
		 * offerRelations = RPCHandlerManager.getHandler(OfferRelations.class);
		 * int i =0; for (Offer offer : offers){
		 * log.e("OFFER"+Integer.toString(i),offer.toString() ); i++;
		 * List<OfferOption> options = offerRelations.getOfferOptions(offer,
		 * null).get(); int j =0; for(OfferOption o: options){
		 * log.e("OfferOption"+Integer.toString(i)+"."+Integer.toString(j),
		 * o.toString()); j++; } }
		 */
		/*
		 * } catch (EntityAlreadyRegistered e) { // TODO Auto-generated catch
		 * block e.printStackTrace(); } catch (WrongType e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); } catch
		 * (UnsupportedType e) { // TODO Auto-generated catch block
		 * e.printStackTrace();
		 */
		/*
		 * } catch (InterruptedException e) { 
		 * e.printStackTrace(); }
		 */
		/*
		 * } catch (ExecutionException e) { 
		 * e.printStackTrace();
		 */
		// }

	
	
	public OfferContent offerContent ;
	public OfferOptionsContent offerOptionsContent ;
	public NotificationRegistry messageContent;
	public LocalOfferOptionDistributor[] courseContent ;
		
	public LocalOfferOptionDistributor perOfferOfferOption ;
	public LocalOfferOptionDistributor perOrderOfferOption ;
	public LocalOfferOptionDistributor[] optionsConfigurationContent;
	
	
	public NotificationRegistry getMessageContent(){
		return messageContent;
	}


	public OfferContent getOfferContent() {
		return offerContent;
	}

	public OfferOptionsContent getOfferOptionsContent() {
		return offerOptionsContent;
	}

	public LocalOfferOptionDistributor getCourseContent(int idx) {
		return courseContent[idx];
	}

	public LocalOfferOptionDistributor getPerOfferOfferOption() {
		return perOfferOfferOption;
	}

	public LocalOfferOptionDistributor getPerOrderOfferOption() {
		return perOrderOfferOption;
	}
	
	public LocalOfferOptionDistributor getOptionsConfigurationContent(int idx){
		return optionsConfigurationContent[idx];
	}

	private void setupActivityDataHolder() {
		this.offerContent = new OfferContent(this);
		this.offerOptionsContent =new OfferOptionsContent(this);
		this.messageContent = new NotificationRegistry(this);
		
		this.courseContent = new LocalOfferOptionDistributor[]{
			new LocalOfferOptionDistributor(this),
			new LocalOfferOptionDistributor(this),
			new LocalOfferOptionDistributor(this)
		};
		
		this.optionsConfigurationContent = new LocalOfferOptionDistributor[]{
				new LocalOfferOptionDistributor(this),
				new LocalOfferOptionDistributor(this),
				new LocalOfferOptionDistributor(this)
			};
		
		
		
	}


}