//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'order_entry_options'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.protocol.Model.OrderEntryOption;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.protocol.Model.OrderEntry;
import net.maivic.comm.Relation;
import net.maivic.comm.Callback;
@Table("order_entry_options")
public interface OrderEntryOptionRelations {
  @Relation(name="offer_option",type=RelationType.MANY_TO_ONE)
  LazyResponse<OfferOption>getOfferOption(OrderEntryOption object , @SubscriptionCallback Callback<OfferOption> cb);
  @Relation(name="order_entry",type=RelationType.MANY_TO_ONE)
  LazyResponse<OrderEntry>getOrderEntry(OrderEntryOption object , @SubscriptionCallback Callback<OrderEntry> cb);
};

