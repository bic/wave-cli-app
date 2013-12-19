//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'offers'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Menu;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.protocol.Model.OrderEntry;
import net.maivic.comm.Relation;
import net.maivic.comm.Callback;
@Table("offers")
public interface OfferRelations extends RPCUpdateSubscriptionService {
  @Relation(name="order_entries",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<OrderEntry>>getOrderEntries(Offer  o,@SubscriptionCallback Callback<List<OrderEntry>> cb);
  @Relation(name="offer_options",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<OfferOption>>getOfferOptions(Offer  o , @SubscriptionCallback Callback<List<OfferOption>> cb);
  @Relation(name="menus",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Menu>>getMenus(Offer  o , @SubscriptionCallback Callback<List<Menu>> cb);
};

