//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'clients'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Relation.RelationType;
import java.util.List;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Payment;
import net.maivic.protocol.Model.Client;
@Table("clients")
public interface ClientRelations {
  @Relation(name="contact",type=RelationType.ONE_TO_MANY)
  LazyResponse<Contact>getContact(Client object);
  @Relation(name="orders",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Order>>getOrders(Client  o);
  @Relation(name="payments",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Payment>>getPayments(Client  o);
};

