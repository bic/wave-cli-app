//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'payments'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.PaymentsToOrder;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Receipt;
import net.maivic.protocol.Model.Payment;
import net.maivic.protocol.Model.Client;
@Table("payments")
public interface PaymentRelations extends RPCUpdateSubscriptionService {
  @Relation(name="payments_to_orders",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<PaymentsToOrder>>getPaymentsToOrders(Payment  o,@SubscriptionCallback Callback<List<PaymentsToOrder>> cb);
  @Relation(name="receipt",type=RelationType.MANY_TO_ONE)
  LazyResponse<Receipt>getReceipt(Payment object , @SubscriptionCallback Callback<Receipt> cb);
  @Relation(name="client",type=RelationType.MANY_TO_ONE)
  LazyResponse<Client>getClient(Payment object , @SubscriptionCallback Callback<Client> cb);
};

