//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'menus'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
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
@Table("menus")
public interface MenuRelations {
  @Relation(name="menu_options",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<MenuOption>>getMenuOptions(Menu  o, Callback<MenuOption> cb);
  @Relation(name="restaurant",type=RelationType.MANY_TO_ONE)
  LazyResponse<Restaurant>getRestaurant(Menu object , Callback<Restaurant> cb);
  @Relation(name="menu_entries",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<MenuEntry>>getMenuEntries(Menu  o, Callback<MenuEntry> cb);
  @Relation(name="offers",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Offer>>getOffers(Menu object , Callback<Offer> cb);
};

