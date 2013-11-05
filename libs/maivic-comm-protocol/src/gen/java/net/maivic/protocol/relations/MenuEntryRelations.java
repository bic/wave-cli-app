//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'menu_entries'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Menu;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.MenuEntry;
@Table("menu_entries")
public interface MenuEntryRelations {
  @Relation(name="menu",type=RelationType.ONE_TO_MANY)
  LazyResponse<Menu>getMenu(MenuEntry object);
};

