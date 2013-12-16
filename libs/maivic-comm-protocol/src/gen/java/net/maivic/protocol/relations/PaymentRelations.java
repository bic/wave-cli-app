//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'payments'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.PaymentsToOrder;
import net.maivic.comm.LazyResponse;
import net.maivic.protocol.Model.Receipt;
import net.maivic.comm.Relation.RelationType;
import java.util.List;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Payment;
import net.maivic.protocol.Model.Client;
@Table("payments")
public interface PaymentRelations {
  @Relation(name="payments_to_orders",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<PaymentsToOrder>>getPaymentsToOrders(Payment  o, Callback<PaymentsToOrder> cb);
  @Relation(name="receipt",type=RelationType.MANY_TO_ONE)
  LazyResponse<Receipt>getReceipt(Payment object , Callback<Receipt> cb);
  @Relation(name="client",type=RelationType.MANY_TO_ONE)
  LazyResponse<Client>getClient(Payment object , Callback<Client> cb);
};

