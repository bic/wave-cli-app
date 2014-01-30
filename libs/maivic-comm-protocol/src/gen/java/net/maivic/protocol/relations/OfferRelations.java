//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'offers'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.Table;
import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.protocol.Model.OfferCatalog;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.LocationBit;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.protocol.Model.OrderEntry;
import net.maivic.comm.Relation;
@Table("offers")
public interface OfferRelations extends RPCUpdateSubscriptionService {
  @Relation(name="location_bits",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<LocationBit>>getLocationBits(Offer  o , @SubscriptionCallback Callback<List<LocationBit>> cb);
  @Relation(name="offer_options",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<OfferOption>>getOfferOptions(Offer  o , @SubscriptionCallback Callback<List<OfferOption>> cb);
  @Relation(name="offer_catalogs",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<OfferCatalog>>getOfferCatalogs(Offer object , @SubscriptionCallback Callback<List<OfferCatalog>> cb);
  @Relation(name="order_entries",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<OrderEntry>>getOrderEntries(Offer  o,@SubscriptionCallback Callback<List<OrderEntry>> cb);
};

