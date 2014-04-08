//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'order_entries'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.protocol.Model.OrderEntryOption;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Offer;
import net.maivic.comm.Callback;
import net.maivic.protocol.Model.OrderEntry;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Invoice;
@Table("order_entries")
public interface OrderEntryRelations extends RPCUpdateSubscriptionService {
  @Relation(name="order_entry_options",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<OrderEntryOption>>getOrderEntryOptions(OrderEntry  o,@SubscriptionCallback Callback<List<OrderEntryOption>> cb);
  @Relation(name="invoices",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Invoice>>getInvoices(OrderEntry  o , @SubscriptionCallback Callback<List<Invoice>> cb);
  @Relation(name="offer",type=RelationType.MANY_TO_ONE)
  LazyResponse<Offer>getOffer(OrderEntry object , @SubscriptionCallback Callback<Offer> cb);
  @Relation(name="order",type=RelationType.MANY_TO_ONE)
  LazyResponse<Order>getOrder(OrderEntry object , @SubscriptionCallback Callback<Order> cb);
};

