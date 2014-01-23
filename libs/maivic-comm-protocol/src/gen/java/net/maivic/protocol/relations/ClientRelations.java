//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'clients'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Contact;
import net.maivic.protocol.Model.DeliveryPlace;
import net.maivic.comm.Callback;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Payment;
import net.maivic.protocol.Model.Client;
@Table("clients")
public interface ClientRelations extends RPCUpdateSubscriptionService {
  @Relation(name="orders",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Order>>getOrders(Client  o,@SubscriptionCallback Callback<List<Order>> cb);
  @Relation(name="delivery_places",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<DeliveryPlace>>getDeliveryPlaces(Client object , @SubscriptionCallback Callback<List<DeliveryPlace>> cb);
  @Relation(name="contact",type=RelationType.MANY_TO_ONE)
  LazyResponse<Contact>getContact(Client object , @SubscriptionCallback Callback<Contact> cb);
  @Relation(name="payments",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Payment>>getPayments(Client  o,@SubscriptionCallback Callback<List<Payment>> cb);
};

