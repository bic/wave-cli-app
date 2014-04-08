//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'offer_catalogs'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.LegalEntity;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.protocol.Model.OfferCatalog;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Offer;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
@Table("offer_catalogs")
public interface OfferCatalogRelations extends RPCUpdateSubscriptionService {
  @Relation(name="supplier",type=RelationType.MANY_TO_ONE)
  LazyResponse<LegalEntity>getSupplier(OfferCatalog object , @SubscriptionCallback Callback<LegalEntity> cb);
  @Relation(name="offers",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Offer>>getOffers(OfferCatalog  o , @SubscriptionCallback Callback<List<Offer>> cb);
};

