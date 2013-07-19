package net.maivic.comm;

import java.util.List;

import com.google.protobuf.GeneratedMessage;

import net.maivic.protobuf.Maivic.Address;
import net.maivic.protobuf.Maivic.Offer;
import net.maivic.protobuf.Maivic.Interval;
import net.maivic.protobuf.Maivic.OpenViewRequest;
import net.maivic.protobuf.Maivic.OpenViewResponse;
import net.maivic.protobuf.Maivic.UserNotification;
import net.maivic.protobuf.Maivic.UserNotification.NotificationType;
import net.maivic.protobuf.Maivic.VersionedID;
/**
 * communication interface for the Maivic Server
 * The communication can be:
 * <li> blocking: use the get...(params) Versions of the Methods
 * <li> non-blocking: use the get...(params,callback) versions of the Methods
 * 
 * The nonblocking form of the methods accepts a {@link Callback} object
 * which will be called with the result as an argument.
 * The {@link Callback} Argument can be ommitted if,
 * and only if the resulting topic has already been subscribed.  
 * * @author paul
 *
 */
public interface Comm {
	 /**
	  * Callback for All notifications received from the server
	  * The callback receives all Notifications, decodes them to
	  * the Types defined in package {@link net.maivic.comm} and forwards
	  * them to the {@link Callback} argument
	  * 
	  * Thus it is possible that the same incoming message is used in 
	  * two or more {@link Callback.call()} messages are called on the
	  * same incoming message.  
	  * @param cb
	  */
	 void subscribeAll(Callback<GeneratedMessage> cb);
	 
	 /**
	  * Returns the Address given a known ID
	  * @param ver  = VersionId
	  */
	 
	 LazyResponse<Address> getAddress(VersionedID ver);

	 
	 /**
	  * get object updates of Offers with
	  * known ids
	  * @param ids of the offers
	  * @param cb callback used with the result when it arrives
	  */
	 LazyResponse<List<Offer>> getOffers(List<VersionedID> ids);
	 /**
	  * get {@link Offer} object updates and
	  * new {@link Offer} objects in the specified interval
	  * @param ids ids of the Offers
	  * @param cb callback used with the result when it arrives
	  */
	 LazyResponse<List<Offer>> getOffers(Interval interval);

	 /**
	  * Return the List of delivery intervals available
	  * in the interval given.
	  * 
	  * <b>Example:</b>
	  * <code> 
	  * 	SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd"); 
	  * 	List<Intervals> intercals = getDeliveryIntervals(
	  * 		Interval t = Interval.newBuilder()
			 .setFrom(ft.parse("2013-09-01").getTime())
			 .setTo(ft.parse("2013-09-02").getTime())
			 .build())
	  * </code>
	  * @param interval Interval which has overlapping time with all the Intervals in the result set
	  * @return the List of intervals which is included in the given interval
	  */	 
	 LazyResponse<List<Interval>>  getDeliveryIntervals(Interval interval);
	 
	 /**
	  * subscribe using this callback handler to all User subscription Notifications
	  * 
	  * @param cb {@link Callback} handler
	  * @param type Notification type. if the value is null all notifications are being sent to the callback Handler
	  */
	 LazyResponse<Subscription<UserNotification>> subscribeUserNotifications(Callback<UserNotification> cb, Enum<NotificationType> type);
	 
	 /**
	  * Called by the smartphone implementation when a Vieww is opened
	  * @param openViewRequest
	  * @return the openViewResponse directing the client whether to open
	  * or not to open a Webview as overlay
	  */
	 LazyResponse<OpenViewResponse> openView(OpenViewRequest openViewRequest);
	 
	 /**
	  * sends a  
	  * 
	  * @param URL
	  * @return
	  */
	 LazyResponse<Integer> sendResource(String URL);
	 
	 /** 
	  * Log Notification
	  * 
	  * Send Log Notifications to the Server
	  */

}
