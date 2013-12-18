//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'restaurants_to_contacts'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.RestaurantsToContact;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
@Table("restaurants_to_contacts")
public interface RestaurantsToContactRelations {
  @Relation(name="contact",type=RelationType.MANY_TO_ONE)
  LazyResponse<Contact>getContact(RestaurantsToContact object , @SubscriptionCallback Callback<Contact> cb);
  @Relation(name="restaurant",type=RelationType.MANY_TO_ONE)
  LazyResponse<Restaurant>getRestaurant(RestaurantsToContact object , @SubscriptionCallback Callback<Restaurant> cb);
};

