//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'menus'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.MenuOption;
import net.maivic.protocol.Model.Menu;
import net.maivic.protocol.Model.Offer;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.MenuEntry;
import net.maivic.protocol.Model.Location;
@Table("menus")
public interface MenuRelations extends RPCUpdateSubscriptionService {
  @Relation(name="menu_options",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<MenuOption>>getMenuOptions(Menu  o,@SubscriptionCallback Callback<List<MenuOption>> cb);
  @Relation(name="location",type=RelationType.MANY_TO_ONE)
  LazyResponse<Location>getLocation(Menu object , @SubscriptionCallback Callback<Location> cb);
  @Relation(name="restaurant",type=RelationType.MANY_TO_ONE)
  LazyResponse<Restaurant>getRestaurant(Menu object , @SubscriptionCallback Callback<Restaurant> cb);
  @Relation(name="menu_entries",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<MenuEntry>>getMenuEntries(Menu  o,@SubscriptionCallback Callback<List<MenuEntry>> cb);
  @Relation(name="offers",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Offer>>getOffers(Menu object , @SubscriptionCallback Callback<List<Offer>> cb);
};

