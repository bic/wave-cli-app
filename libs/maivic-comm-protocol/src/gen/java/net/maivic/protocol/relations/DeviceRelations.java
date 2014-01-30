//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'devices'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.DeviceConnection;
import net.maivic.protocol.Model.Device;
import net.maivic.protocol.Model.RpcSubscription;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Client;
import net.maivic.comm.Callback;
@Table("devices")
public interface DeviceRelations extends RPCUpdateSubscriptionService {
  @Relation(name="device_connections",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<DeviceConnection>>getDeviceConnections(Device  o,@SubscriptionCallback Callback<List<DeviceConnection>> cb);
  @Relation(name="rpc_subscriptions",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<RpcSubscription>>getRpcSubscriptions(Device  o,@SubscriptionCallback Callback<List<RpcSubscription>> cb);
  @Relation(name="clients",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Client>>getClients(Device  o , @SubscriptionCallback Callback<List<Client>> cb);
};

