//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'addresses'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.LegalEntity;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Address;
import net.maivic.protocol.Model.Meeting;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Relation;
@Table("addresses")
public interface AddressRelations {
  @Relation(name="legal_entities",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<LegalEntity>>getLegalEntities(Address  o);
  @Relation(name="legal_entities_1",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<LegalEntity>>getLegalEntities1(Address  o);
  @Relation(name="meetings",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Meeting>>getMeetings(Address  o);
  @Relation(name="contacts",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Contact>>getContacts(Address  o);
  @Relation(name="restaurants",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Restaurant>>getRestaurants(Address  o);
};

