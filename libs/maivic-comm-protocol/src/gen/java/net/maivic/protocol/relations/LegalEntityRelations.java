//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'legal_entities'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.LegalEntity;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Address;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.ContactsToLegalEntity;
import net.maivic.protocol.Model.Invoice;
@Table("legal_entities")
public interface LegalEntityRelations {
  @Relation(name="invoices",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Invoice>>getInvoices(LegalEntity  o, Callback<Invoice> cb);
  @Relation(name="address",type=RelationType.MANY_TO_ONE)
  LazyResponse<Address>getAddress(LegalEntity object , Callback<Address> cb);
  @Relation(name="address_1",type=RelationType.MANY_TO_ONE)
  LazyResponse<Address>getAddress1(LegalEntity object , Callback<Address> cb);
  @Relation(name="contacts_to_legal_entities",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<ContactsToLegalEntity>>getContactsToLegalEntities(LegalEntity  o, Callback<ContactsToLegalEntity> cb);
  @Relation(name="restaurants",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Restaurant>>getRestaurants(LegalEntity  o, Callback<Restaurant> cb);
};

