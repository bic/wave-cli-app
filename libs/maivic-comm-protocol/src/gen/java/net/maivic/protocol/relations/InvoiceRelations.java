//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'invoices'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Callback;
import net.maivic.protocol.Model.LegalEntity;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.OrderEntry;
import net.maivic.protocol.Model.InvoicesToContact;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Receipt;
import net.maivic.protocol.Model.Invoice;
@Table("invoices")
public interface InvoiceRelations {
  @Relation(name="receipts",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Receipt>>getReceipts(Invoice object , Callback<Receipt> cb);
  @Relation(name="contact",type=RelationType.MANY_TO_ONE)
  LazyResponse<Contact>getContact(Invoice object , Callback<Contact> cb);
  @Relation(name="legal_entity",type=RelationType.MANY_TO_ONE)
  LazyResponse<LegalEntity>getLegalEntity(Invoice object , Callback<LegalEntity> cb);
  @Relation(name="order_entries",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<OrderEntry>>getOrderEntries(Invoice object , Callback<OrderEntry> cb);
  @Relation(name="invoices_to_contacts",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<InvoicesToContact>>getInvoicesToContacts(Invoice  o, Callback<InvoicesToContact> cb);
};

