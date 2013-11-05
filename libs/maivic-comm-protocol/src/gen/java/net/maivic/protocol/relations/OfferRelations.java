//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'offers'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OrderEntry;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Location;
@Table("offers")
public interface OfferRelations {
  @Relation(name="location",type=RelationType.ONE_TO_MANY)
  LazyResponse<Location>getLocation(Offer object);
  @Relation(name="order_entries",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<OrderEntry>>getOrderEntries(Offer  o);
};

