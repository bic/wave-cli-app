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
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Building;
@Table("addresses")
public interface AddressRelations {
  @Relation(name="legal_entities",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<LegalEntity>>getLegalEntities(Address  o, Callback<LegalEntity> cb);
  @Relation(name="legal_entities_1",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<LegalEntity>>getLegalEntities1(Address  o, Callback<LegalEntity> cb);
  @Relation(name="meetings",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Meeting>>getMeetings(Address  o, Callback<Meeting> cb);
  @Relation(name="contacts",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Contact>>getContacts(Address  o, Callback<Contact> cb);
  @Relation(name="buildings",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Building>>getBuildings(Address object , Callback<Building> cb);
  @Relation(name="restaurants",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Restaurant>>getRestaurants(Address  o, Callback<Restaurant> cb);
};

