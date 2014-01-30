//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'offer_options'
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
import net.maivic.protocol.Model.OfferOption;
import net.maivic.comm.Relation;
import net.maivic.comm.Callback;
@Table("offer_options")
public interface OfferOptionRelations extends RPCUpdateSubscriptionService {
  @Relation(name="order_entry_options",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<OrderEntryOption>>getOrderEntryOptions(OfferOption  o,@SubscriptionCallback Callback<List<OrderEntryOption>> cb);
  @Relation(name="offers",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Offer>>getOffers(OfferOption object , @SubscriptionCallback Callback<List<Offer>> cb);
};

