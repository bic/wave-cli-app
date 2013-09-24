//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'delivery_place_specifications'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.DeliveryPlaceSpecification;
import net.maivic.protocol.Model.DeliveryPlace;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.PlaceType;
import net.maivic.protocol.Model.Location;
import net.maivic.protocol.Model.Building;
@Table("delivery_place_specifications")
public interface DeliveryPlaceSpecificationRelations {
  @Relation(name="delivery_place",type=RelationType.ONE_TO_MANY)
  LazyResponse<DeliveryPlace>getDeliveryPlace(DeliveryPlaceSpecification object);
  @Relation(name="building",type=RelationType.ONE_TO_MANY)
  LazyResponse<Building>getBuilding(DeliveryPlaceSpecification object);
  @Relation(name="location",type=RelationType.ONE_TO_MANY)
  LazyResponse<Location>getLocation(DeliveryPlaceSpecification object);
  @Relation(name="place_type",type=RelationType.ONE_TO_MANY)
  LazyResponse<PlaceType>getPlaceType(DeliveryPlaceSpecification object);
};

