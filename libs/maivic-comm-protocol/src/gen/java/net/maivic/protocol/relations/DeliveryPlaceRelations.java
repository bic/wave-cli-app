//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'delivery_places'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.DeliveryPlaceSpecification;
import net.maivic.protocol.Model.DeliveryPlace;
import net.maivic.comm.Callback;
import net.maivic.protocol.Model.Order;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Client;
import net.maivic.protocol.Model.Location;
import net.maivic.protocol.Model.Building;
@Table("delivery_places")
public interface DeliveryPlaceRelations extends RPCUpdateSubscriptionService {
  @Relation(name="orders",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Order>>getOrders(DeliveryPlace  o,@SubscriptionCallback Callback<List<Order>> cb);
  @Relation(name="clients",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Client>>getClients(DeliveryPlace  o , @SubscriptionCallback Callback<List<Client>> cb);
  @Relation(name="building",type=RelationType.MANY_TO_ONE)
  LazyResponse<Building>getBuilding(DeliveryPlace object , @SubscriptionCallback Callback<Building> cb);
  @Relation(name="location",type=RelationType.MANY_TO_ONE)
  LazyResponse<Location>getLocation(DeliveryPlace object , @SubscriptionCallback Callback<Location> cb);
  @Relation(name="delivery_place_specifications",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<DeliveryPlaceSpecification>>getDeliveryPlaceSpecifications(DeliveryPlace  o,@SubscriptionCallback Callback<List<DeliveryPlaceSpecification>> cb);
};

