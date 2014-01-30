//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'device_connections'
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
import net.maivic.protocol.Model.Server;
import net.maivic.comm.Relation;
import net.maivic.comm.Callback;
@Table("device_connections")
public interface DeviceConnectionRelations extends RPCUpdateSubscriptionService {
  @Relation(name="device",type=RelationType.MANY_TO_ONE)
  LazyResponse<Device>getDevice(DeviceConnection object , @SubscriptionCallback Callback<Device> cb);
  @Relation(name="server",type=RelationType.MANY_TO_ONE)
  LazyResponse<Server>getServer(DeviceConnection object , @SubscriptionCallback Callback<Server> cb);
  @Relation(name="rpc_subscriptions",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<RpcSubscription>>getRpcSubscriptions(DeviceConnection object , @SubscriptionCallback Callback<List<RpcSubscription>> cb);
};

