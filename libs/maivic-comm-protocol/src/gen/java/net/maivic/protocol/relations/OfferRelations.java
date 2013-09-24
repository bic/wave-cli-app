//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'offers'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.protocol.Model.OrderEntry;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
@Table("offers")
public interface OfferRelations {
  @Relation(name="orders",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Order>>getOrders(Offer  o);
  @Relation(name="order_entries",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<OrderEntry>>getOrderEntries(Offer  o);
  @Relation(name="offer_options",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<OfferOption>>getOfferOptions(Offer  o);
};

