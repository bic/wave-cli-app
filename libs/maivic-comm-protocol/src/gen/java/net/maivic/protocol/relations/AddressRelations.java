//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'addresses'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.LegalEntity;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.protocol.Model.LocationBit;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Address;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
@Table("addresses")
public interface AddressRelations extends RPCUpdateSubscriptionService {
  @Relation(name="legal_entities",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<LegalEntity>>getLegalEntities(Address  o,@SubscriptionCallback Callback<List<LegalEntity>> cb);
  @Relation(name="legal_entities_1",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<LegalEntity>>getLegalEntities1(Address  o,@SubscriptionCallback Callback<List<LegalEntity>> cb);
  @Relation(name="contacts",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Contact>>getContacts(Address  o,@SubscriptionCallback Callback<List<Contact>> cb);
  @Relation(name="location_bits",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<LocationBit>>getLocationBits(Address  o,@SubscriptionCallback Callback<List<LocationBit>> cb);
  @Relation(name="restaurants",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Restaurant>>getRestaurants(Address  o,@SubscriptionCallback Callback<List<Restaurant>> cb);
};

