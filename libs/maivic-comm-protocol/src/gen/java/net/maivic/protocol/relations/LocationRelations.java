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
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Location;
import net.maivic.protocol.Model.Building;
@Table("locations")
public interface LocationRelations {
  @Relation(name="deliveries",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Delivery>>getDeliveries(Location  o, Callback<Delivery> cb);
  @Relation(name="delivery_places",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<DeliveryPlace>>getDeliveryPlaces(Location  o, Callback<DeliveryPlace> cb);
  @Relation(name="delivery_place_specifications",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<DeliveryPlaceSpecification>>getDeliveryPlaceSpecifications(Location  o, Callback<DeliveryPlaceSpecification> cb);
  @Relation(name="offers",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Offer>>getOffers(Location  o, Callback<Offer> cb);
  @Relation(name="buildings",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Building>>getBuildings(Location  o, Callback<Building> cb);
};

