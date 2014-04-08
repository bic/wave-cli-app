//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'location_bits'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Address;
import net.maivic.protocol.Model.LocationBit;
import net.maivic.protocol.Model.Offer;
import net.maivic.comm.Table;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.DatasetLevel;
@Table("location_bits")
public interface LocationBitRelations extends RPCUpdateSubscriptionService {
  @Relation(name="offers",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Offer>>getOffers(LocationBit object , @SubscriptionCallback Callback<List<Offer>> cb);
  @Relation(name="address",type=RelationType.MANY_TO_ONE)
  LazyResponse<Address>getAddress(LocationBit object , @SubscriptionCallback Callback<Address> cb);
  @Relation(name="dataset_level",type=RelationType.MANY_TO_ONE)
  LazyResponse<DatasetLevel>getDatasetLevel(LocationBit object , @SubscriptionCallback Callback<DatasetLevel> cb);
  @Relation(name="parent",type=RelationType.MANY_TO_ONE)
  LazyResponse<LocationBit>getParent(LocationBit object , @SubscriptionCallback Callback<LocationBit> cb);
  @Relation(name="location_bits",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<LocationBit>>getLocationBits(LocationBit  o,@SubscriptionCallback Callback<List<LocationBit>> cb);
};

