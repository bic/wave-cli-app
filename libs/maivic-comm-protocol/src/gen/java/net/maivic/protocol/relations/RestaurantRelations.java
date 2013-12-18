//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'restaurants'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
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
import net.maivic.protocol.Model.Menu;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
@Table("restaurants")
public interface RestaurantRelations {
  @Relation(name="deliveries",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Delivery>>getDeliveries(Restaurant  o,@SubscriptionCallback Callback<List<Delivery>> cb);
  @Relation(name="menus",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Menu>>getMenus(Restaurant  o,@SubscriptionCallback Callback<List<Menu>> cb);
  @Relation(name="restaurants_to_contacts",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<RestaurantsToContact>>getRestaurantsToContacts(Restaurant  o,@SubscriptionCallback Callback<List<RestaurantsToContact>> cb);
  @Relation(name="address",type=RelationType.MANY_TO_ONE)
  LazyResponse<Address>getAddress(Restaurant object , @SubscriptionCallback Callback<Address> cb);
  @Relation(name="image",type=RelationType.MANY_TO_ONE)
  LazyResponse<Image>getImage(Restaurant object , @SubscriptionCallback Callback<Image> cb);
  @Relation(name="legal_entity",type=RelationType.MANY_TO_ONE)
  LazyResponse<LegalEntity>getLegalEntity(Restaurant object , @SubscriptionCallback Callback<LegalEntity> cb);
};

