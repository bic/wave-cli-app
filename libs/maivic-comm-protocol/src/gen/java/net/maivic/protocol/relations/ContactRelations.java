//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'contacts'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Address;
import net.maivic.protocol.Model.Delivery;
import net.maivic.protocol.Model.MeetingsToContact;
import net.maivic.protocol.Model.RestaurantsToContact;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Callback;
import net.maivic.protocol.Model.InvoicesToContact;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Client;
import net.maivic.protocol.Model.ContactsToLegalEntity;
import net.maivic.protocol.Model.Invoice;
@Table("contacts")
public interface ContactRelations extends RPCUpdateSubscriptionService {
  @Relation(name="deliveries",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Delivery>>getDeliveries(Contact  o,@SubscriptionCallback Callback<List<Delivery>> cb);
  @Relation(name="address",type=RelationType.MANY_TO_ONE)
  LazyResponse<Address>getAddress(Contact object , @SubscriptionCallback Callback<Address> cb);
  @Relation(name="invoices",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Invoice>>getInvoices(Contact  o,@SubscriptionCallback Callback<List<Invoice>> cb);
  @Relation(name="invoices_to_contacts",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<InvoicesToContact>>getInvoicesToContacts(Contact  o,@SubscriptionCallback Callback<List<InvoicesToContact>> cb);
  @Relation(name="contacts_to_legal_entities",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<ContactsToLegalEntity>>getContactsToLegalEntities(Contact  o,@SubscriptionCallback Callback<List<ContactsToLegalEntity>> cb);
  @Relation(name="clients",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Client>>getClients(Contact  o,@SubscriptionCallback Callback<List<Client>> cb);
  @Relation(name="restaurants_to_contacts",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<RestaurantsToContact>>getRestaurantsToContacts(Contact  o,@SubscriptionCallback Callback<List<RestaurantsToContact>> cb);
  @Relation(name="meetings",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<MeetingsToContact>>getMeetings(Contact  o,@SubscriptionCallback Callback<List<MeetingsToContact>> cb);
};

