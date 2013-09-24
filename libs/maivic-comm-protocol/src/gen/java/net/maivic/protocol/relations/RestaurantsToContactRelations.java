//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'restaurants_to_contacts'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.RestaurantsToContact;
import net.maivic.comm.Relation;
@Table("restaurants_to_contacts")
public interface RestaurantsToContactRelations {
  @Relation(name="contact",type=RelationType.ONE_TO_MANY)
  LazyResponse<Contact>getContact(RestaurantsToContact object);
  @Relation(name="restaurant",type=RelationType.ONE_TO_MANY)
  LazyResponse<Restaurant>getRestaurant(RestaurantsToContact object);
};

