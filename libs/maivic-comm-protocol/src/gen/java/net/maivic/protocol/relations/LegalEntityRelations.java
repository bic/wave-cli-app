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
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.ContactsToLegalEntity;
import net.maivic.protocol.Model.Invoice;
@Table("legal_entities")
public interface LegalEntityRelations {
  @Relation(name="invoices",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Invoice>>getInvoices(LegalEntity  o);
  @Relation(name="address",type=RelationType.ONE_TO_MANY)
  LazyResponse<Address>getAddress(LegalEntity object);
  @Relation(name="address_1",type=RelationType.ONE_TO_MANY)
  LazyResponse<Address>getAddress1(LegalEntity object);
  @Relation(name="contacts_to_legal_entities",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<ContactsToLegalEntity>>getContactsToLegalEntities(LegalEntity  o);
  @Relation(name="restaurants",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Restaurant>>getRestaurants(LegalEntity  o);
};

