//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'dataset_levels'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.LocationBit;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.DatasetLevel;
@Table("dataset_levels")
public interface DatasetLevelRelations extends RPCUpdateSubscriptionService {
  @Relation(name="location_bits",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<LocationBit>>getLocationBits(DatasetLevel  o,@SubscriptionCallback Callback<List<LocationBit>> cb);
};

