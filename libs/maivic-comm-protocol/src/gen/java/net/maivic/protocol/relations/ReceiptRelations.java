//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'receipts'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.protocol.Model.Receipt;
import net.maivic.comm.Relation.RelationType;
import java.util.List;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Payment;
@Table("receipts")
public interface ReceiptRelations {
  @Relation(name="payments",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Payment>>getPayments(Receipt  o);
};

