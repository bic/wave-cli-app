//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'orders'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.PaymentsToOrder;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Delivery;
import net.maivic.protocol.Model.DeliveryPlace;
import net.maivic.comm.Callback;
import net.maivic.protocol.Model.OrderEntry;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Client;
@Table("orders")
public interface OrderRelations {
  @Relation(name="deliveries",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Delivery>>getDeliveries(Order  o, Callback<Delivery> cb);
  @Relation(name="client",type=RelationType.MANY_TO_ONE)
  LazyResponse<Client>getClient(Order object , Callback<Client> cb);
  @Relation(name="delivery_place",type=RelationType.MANY_TO_ONE)
  LazyResponse<DeliveryPlace>getDeliveryPlace(Order object , Callback<DeliveryPlace> cb);
  @Relation(name="payments_to_orders",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<PaymentsToOrder>>getPaymentsToOrders(Order  o, Callback<PaymentsToOrder> cb);
  @Relation(name="order_entries",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<OrderEntry>>getOrderEntries(Order  o, Callback<OrderEntry> cb);
};

