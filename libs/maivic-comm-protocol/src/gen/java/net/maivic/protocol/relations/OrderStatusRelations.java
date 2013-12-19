//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'order_statuses'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Relation.RelationType;
import net.maivic.comm.Callback;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.OrderStatus;
@Table("order_statuses")
public interface OrderStatusRelations extends RPCUpdateSubscriptionService {
  @Relation(name="order",type=RelationType.MANY_TO_ONE)
  LazyResponse<Order>getOrder(OrderStatus object , @SubscriptionCallback Callback<Order> cb);
};

