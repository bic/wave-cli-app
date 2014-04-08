//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'servers'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.DeviceConnection;
import net.maivic.comm.Callback;
import net.maivic.protocol.Model.Server;
import net.maivic.comm.Relation;
@Table("servers")
public interface ServerRelations extends RPCUpdateSubscriptionService {
  @Relation(name="device_connections",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<DeviceConnection>>getDeviceConnections(Server  o,@SubscriptionCallback Callback<List<DeviceConnection>> cb);
};

