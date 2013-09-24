//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'payments_to_orders'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.PaymentsToOrder;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Payment;
@Table("payments_to_orders")
public interface PaymentsToOrderRelations {
  @Relation(name="order",type=RelationType.ONE_TO_MANY)
  LazyResponse<Order>getOrder(PaymentsToOrder object);
  @Relation(name="payment",type=RelationType.ONE_TO_MANY)
  LazyResponse<Payment>getPayment(PaymentsToOrder object);
};

