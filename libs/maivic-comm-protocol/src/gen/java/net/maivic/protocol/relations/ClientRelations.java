//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'clients'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.protocol.Model.Client;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Contact;
import net.maivic.protocol.Model.DeliveryPlace;
import net.maivic.protocol.Model.Device;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Payment;
import net.maivic.comm.Callback;
@Table("clients")
public interface ClientRelations {
  @Relation(name="orders",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Order>>getOrders(Client  o, Callback<Order> cb);
  @Relation(name="delivery_places",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<DeliveryPlace>>getDeliveryPlaces(Client object , Callback<DeliveryPlace> cb);
  @Relation(name="devices",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Device>>getDevices(Client object , Callback<Device> cb);
  @Relation(name="contact",type=RelationType.MANY_TO_ONE)
  LazyResponse<Contact>getContact(Client object , Callback<Contact> cb);
  @Relation(name="payments",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Payment>>getPayments(Client  o, Callback<Payment> cb);
};

