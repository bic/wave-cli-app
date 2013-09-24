//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'contacts'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Address;
import net.maivic.protocol.Model.Delivery;
import net.maivic.protocol.Model.RestaurantsToContact;
import net.maivic.protocol.Model.Contact;
import net.maivic.protocol.Model.MeetingsToContact;
import net.maivic.protocol.Model.InvoicesToContact;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Client;
import net.maivic.protocol.Model.ContactsToLegalEntity;
import net.maivic.protocol.Model.Invoice;
@Table("contacts")
public interface ContactRelations {
  @Relation(name="clients",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Client>>getClients(Contact  o);
  @Relation(name="invoices",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Invoice>>getInvoices(Contact  o);
  @Relation(name="deliveries",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Delivery>>getDeliveries(Contact  o);
  @Relation(name="address",type=RelationType.ONE_TO_MANY)
  LazyResponse<Address>getAddress(Contact object);
  @Relation(name="meetings",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<MeetingsToContact>>getMeetings(Contact  o);
  @Relation(name="contacts_to_legal_entities",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<ContactsToLegalEntity>>getContactsToLegalEntities(Contact  o);
  @Relation(name="invoices_to_contacts",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<InvoicesToContact>>getInvoicesToContacts(Contact  o);
  @Relation(name="restaurants_to_contacts",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<RestaurantsToContact>>getRestaurantsToContacts(Contact  o);
};

