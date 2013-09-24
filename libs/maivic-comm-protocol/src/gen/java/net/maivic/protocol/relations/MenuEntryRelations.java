//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'menu_entries'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.MenuEntryOption;
import net.maivic.protocol.Model.Ingredient;
import net.maivic.protocol.Model.Menu;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.MenuEntry;
@Table("menu_entries")
public interface MenuEntryRelations {
  @Relation(name="menu_entry_options",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<MenuEntryOption>>getMenuEntryOptions(MenuEntry  o);
  @Relation(name="main_ingredient",type=RelationType.ONE_TO_MANY)
  LazyResponse<Ingredient>getMainIngredient(MenuEntry object);
  @Relation(name="menu",type=RelationType.ONE_TO_MANY)
  LazyResponse<Menu>getMenu(MenuEntry object);
};

