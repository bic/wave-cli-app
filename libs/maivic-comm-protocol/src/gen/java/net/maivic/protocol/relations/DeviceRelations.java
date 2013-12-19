//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'devices'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.Table;
import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Device;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Client;
@Table("devices")
public interface DeviceRelations extends RPCUpdateSubscriptionService {
  @Relation(name="clients",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Client>>getClients(Device  o , @SubscriptionCallback Callback<List<Client>> cb);
};

