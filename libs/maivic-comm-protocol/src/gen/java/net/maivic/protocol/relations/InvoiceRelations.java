//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'invoices'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.LegalEntity;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Contact;
import net.maivic.protocol.Model.InvoicesToContact;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Invoice;
@Table("invoices")
public interface InvoiceRelations {
  @Relation(name="contact",type=RelationType.ONE_TO_MANY)
  LazyResponse<Contact>getContact(Invoice object);
  @Relation(name="legal_entity",type=RelationType.ONE_TO_MANY)
  LazyResponse<LegalEntity>getLegalEntity(Invoice object);
  @Relation(name="invoices_to_contacts",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<InvoicesToContact>>getInvoicesToContacts(Invoice  o);
};

