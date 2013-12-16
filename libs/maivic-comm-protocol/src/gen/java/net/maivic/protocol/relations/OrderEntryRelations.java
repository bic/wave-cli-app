//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'order_entries'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Offer;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.OrderEntry;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Invoice;
@Table("order_entries")
public interface OrderEntryRelations {
  @Relation(name="invoices",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Invoice>>getInvoices(OrderEntry  o , Callback<Invoice> cb);
  @Relation(name="offer",type=RelationType.MANY_TO_ONE)
  LazyResponse<Offer>getOffer(OrderEntry object , Callback<Offer> cb);
  @Relation(name="order",type=RelationType.MANY_TO_ONE)
  LazyResponse<Order>getOrder(OrderEntry object , Callback<Order> cb);
};

