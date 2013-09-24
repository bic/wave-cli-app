//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'deliveries'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Delivery;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Location;
@Table("deliveries")
public interface DeliveryRelations {
  @Relation(name="delivering_guy",type=RelationType.ONE_TO_MANY)
  LazyResponse<Contact>getDeliveringGuy(Delivery object);
  @Relation(name="location",type=RelationType.ONE_TO_MANY)
  LazyResponse<Location>getLocation(Delivery object);
  @Relation(name="order",type=RelationType.ONE_TO_MANY)
  LazyResponse<Order>getOrder(Delivery object);
  @Relation(name="restaurant",type=RelationType.ONE_TO_MANY)
  LazyResponse<Restaurant>getRestaurant(Delivery object);
};

