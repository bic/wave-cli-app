//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'offers'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Callback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Menu;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.protocol.Model.OrderEntry;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.Location;
@Table("offers")
public interface OfferRelations {
  @Relation(name="location",type=RelationType.MANY_TO_ONE)
  LazyResponse<Location>getLocation(Offer object , Callback<Location> cb);
  @Relation(name="offer_options",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<OfferOption>>getOfferOptions(Offer  o , Callback<OfferOption> cb);
  @Relation(name="order_entries",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<OrderEntry>>getOrderEntries(Offer  o, Callback<OrderEntry> cb);
  @Relation(name="menus",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Menu>>getMenus(Offer  o , Callback<Menu> cb);
};

