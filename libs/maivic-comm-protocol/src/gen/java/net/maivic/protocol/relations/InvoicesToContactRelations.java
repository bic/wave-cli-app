//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'invoices_to_contacts'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Relation.RelationType;
import net.maivic.comm.Callback;
import net.maivic.protocol.Model.InvoicesToContact;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Invoice;
@Table("invoices_to_contacts")
public interface InvoicesToContactRelations {
  @Relation(name="contact",type=RelationType.MANY_TO_ONE)
  LazyResponse<Contact>getContact(InvoicesToContact object , @SubscriptionCallback Callback<Contact> cb);
  @Relation(name="invoice",type=RelationType.MANY_TO_ONE)
  LazyResponse<Invoice>getInvoice(InvoicesToContact object , @SubscriptionCallback Callback<Invoice> cb);
};

