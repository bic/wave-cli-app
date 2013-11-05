//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'locations'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Delivery;
import net.maivic.protocol.Model.DeliveryPlaceSpecification;
import net.maivic.protocol.Model.DeliveryPlace;
import net.maivic.protocol.Model.Offer;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Location;
import net.maivic.protocol.Model.Building;
@Table("locations")
public interface LocationRelations {
  @Relation(name="deliveries",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Delivery>>getDeliveries(Location  o);
  @Relation(name="delivery_places",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<DeliveryPlace>>getDeliveryPlaces(Location  o);
  @Relation(name="offers",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Offer>>getOffers(Location  o);
  @Relation(name="buildings",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Building>>getBuildings(Location  o);
  @Relation(name="delivery_place_specifications",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<DeliveryPlaceSpecification>>getDeliveryPlaceSpecifications(Location  o);
};

