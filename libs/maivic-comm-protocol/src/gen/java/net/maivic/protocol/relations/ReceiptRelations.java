//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'receipts'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.comm.Table;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Receipt;
import net.maivic.protocol.Model.Payment;
import net.maivic.protocol.Model.Invoice;
@Table("receipts")
public interface ReceiptRelations {
  @Relation(name="invoices",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Invoice>>getInvoices(Receipt  o , @SubscriptionCallback Callback<List<Invoice>> cb);
  @Relation(name="payments",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Payment>>getPayments(Receipt  o,@SubscriptionCallback Callback<List<Payment>> cb);
};

