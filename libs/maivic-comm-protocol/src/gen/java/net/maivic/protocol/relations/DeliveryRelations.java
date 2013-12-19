//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'deliveries'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Delivery;
import net.maivic.comm.Callback;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Location;
@Table("deliveries")
public interface DeliveryRelations extends RPCUpdateSubscriptionService {
  @Relation(name="delivering_guy",type=RelationType.MANY_TO_ONE)
  LazyResponse<Contact>getDeliveringGuy(Delivery object , @SubscriptionCallback Callback<Contact> cb);
  @Relation(name="location",type=RelationType.MANY_TO_ONE)
  LazyResponse<Location>getLocation(Delivery object , @SubscriptionCallback Callback<Location> cb);
  @Relation(name="order",type=RelationType.MANY_TO_ONE)
  LazyResponse<Order>getOrder(Delivery object , @SubscriptionCallback Callback<Order> cb);
  @Relation(name="restaurant",type=RelationType.MANY_TO_ONE)
  LazyResponse<Restaurant>getRestaurant(Delivery object , @SubscriptionCallback Callback<Restaurant> cb);
};

