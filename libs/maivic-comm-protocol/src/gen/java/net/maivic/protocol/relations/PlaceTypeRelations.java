//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'place_types'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.DeliveryPlaceSpecification;
import net.maivic.comm.Table;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.PlaceType;
@Table("place_types")
public interface PlaceTypeRelations {
  @Relation(name="delivery_place_specifications",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<DeliveryPlaceSpecification>>getDeliveryPlaceSpecifications(PlaceType  o, Callback<DeliveryPlaceSpecification> cb);
};

