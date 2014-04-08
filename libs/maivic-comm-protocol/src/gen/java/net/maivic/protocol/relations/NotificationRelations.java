//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'notifications'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Notification;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.RpcSubscription;
import net.maivic.comm.Relation;
@Table("notifications")
public interface NotificationRelations extends RPCUpdateSubscriptionService {
  @Relation(name="rpc_subscriptions",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<RpcSubscription>>getRpcSubscriptions(Notification object , @SubscriptionCallback Callback<List<RpcSubscription>> cb);
};

