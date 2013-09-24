//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'order_entries'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OrderEntry;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
@Table("order_entries")
public interface OrderEntryRelations {
  @Relation(name="offer",type=RelationType.ONE_TO_MANY)
  LazyResponse<Offer>getOffer(OrderEntry object);
  @Relation(name="order",type=RelationType.ONE_TO_MANY)
  LazyResponse<Order>getOrder(OrderEntry object);
};

