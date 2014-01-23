//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'rpc_subscriptions'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.Table;
import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.DeviceConnection;
import net.maivic.protocol.Model.Notification;
import net.maivic.protocol.Model.Device;
import net.maivic.protocol.Model.RpcSubscription;
import net.maivic.comm.Relation;
@Table("rpc_subscriptions")
public interface RpcSubscriptionRelations extends RPCUpdateSubscriptionService {
  @Relation(name="device_connections",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<DeviceConnection>>getDeviceConnections(RpcSubscription  o , @SubscriptionCallback Callback<List<DeviceConnection>> cb);
  @Relation(name="notifications",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Notification>>getNotifications(RpcSubscription  o , @SubscriptionCallback Callback<List<Notification>> cb);
  @Relation(name="device",type=RelationType.MANY_TO_ONE)
  LazyResponse<Device>getDevice(RpcSubscription object , @SubscriptionCallback Callback<Device> cb);
};

