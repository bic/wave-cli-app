//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'buildings'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.DeliveryPlaceSpecification;
import net.maivic.protocol.Model.DeliveryPlace;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Location;
import net.maivic.protocol.Model.Building;
@Table("buildings")
public interface BuildingRelations {
  @Relation(name="delivery_places",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<DeliveryPlace>>getDeliveryPlaces(Building  o);
  @Relation(name="location",type=RelationType.ONE_TO_MANY)
  LazyResponse<Location>getLocation(Building object);
  @Relation(name="delivery_place_specifications",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<DeliveryPlaceSpecification>>getDeliveryPlaceSpecifications(Building  o);
};

