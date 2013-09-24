//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'restaurants'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.LegalEntity;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.protocol.Model.Image;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Address;
import net.maivic.protocol.Model.Delivery;
import net.maivic.protocol.Model.RestaurantsToContact;
import net.maivic.comm.Relation;
@Table("restaurants")
public interface RestaurantRelations {
  @Relation(name="deliveries",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Delivery>>getDeliveries(Restaurant  o);
  @Relation(name="restaurants_to_contacts",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<RestaurantsToContact>>getRestaurantsToContacts(Restaurant  o);
  @Relation(name="address",type=RelationType.ONE_TO_MANY)
  LazyResponse<Address>getAddress(Restaurant object);
  @Relation(name="image",type=RelationType.ONE_TO_MANY)
  LazyResponse<Image>getImage(Restaurant object);
  @Relation(name="legal_entity",type=RelationType.ONE_TO_MANY)
  LazyResponse<LegalEntity>getLegalEntity(Restaurant object);
};

