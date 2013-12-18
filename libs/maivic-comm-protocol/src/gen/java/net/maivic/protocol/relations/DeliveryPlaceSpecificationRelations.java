//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'delivery_place_specifications'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.DeliveryPlaceSpecification;
import net.maivic.protocol.Model.DeliveryPlace;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.PlaceType;
import net.maivic.protocol.Model.Location;
import net.maivic.protocol.Model.Building;
@Table("delivery_place_specifications")
public interface DeliveryPlaceSpecificationRelations {
  @Relation(name="delivery_place",type=RelationType.MANY_TO_ONE)
  LazyResponse<DeliveryPlace>getDeliveryPlace(DeliveryPlaceSpecification object , @SubscriptionCallback Callback<DeliveryPlace> cb);
  @Relation(name="building",type=RelationType.MANY_TO_ONE)
  LazyResponse<Building>getBuilding(DeliveryPlaceSpecification object , @SubscriptionCallback Callback<Building> cb);
  @Relation(name="location",type=RelationType.MANY_TO_ONE)
  LazyResponse<Location>getLocation(DeliveryPlaceSpecification object , @SubscriptionCallback Callback<Location> cb);
  @Relation(name="place_type",type=RelationType.MANY_TO_ONE)
  LazyResponse<PlaceType>getPlaceType(DeliveryPlaceSpecification object , @SubscriptionCallback Callback<PlaceType> cb);
};

