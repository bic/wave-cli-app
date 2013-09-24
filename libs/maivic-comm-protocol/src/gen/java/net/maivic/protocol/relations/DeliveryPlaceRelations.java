//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'delivery_places'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.DeliveryPlaceSpecification;
import net.maivic.protocol.Model.DeliveryPlace;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Location;
import net.maivic.protocol.Model.Building;
@Table("delivery_places")
public interface DeliveryPlaceRelations {
  @Relation(name="orders",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Order>>getOrders(DeliveryPlace  o);
  @Relation(name="building",type=RelationType.ONE_TO_MANY)
  LazyResponse<Building>getBuilding(DeliveryPlace object);
  @Relation(name="location",type=RelationType.ONE_TO_MANY)
  LazyResponse<Location>getLocation(DeliveryPlace object);
  @Relation(name="delivery_place_specifications",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<DeliveryPlaceSpecification>>getDeliveryPlaceSpecifications(DeliveryPlace  o);
};

