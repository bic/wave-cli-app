//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'payments_to_orders'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Callback;
import net.maivic.protocol.Model.PaymentsToOrder;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Relation.RelationType;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Payment;
@Table("payments_to_orders")
public interface PaymentsToOrderRelations {
  @Relation(name="order",type=RelationType.MANY_TO_ONE)
  LazyResponse<Order>getOrder(PaymentsToOrder object , Callback<Order> cb);
  @Relation(name="payment",type=RelationType.MANY_TO_ONE)
  LazyResponse<Payment>getPayment(PaymentsToOrder object , Callback<Payment> cb);
};

