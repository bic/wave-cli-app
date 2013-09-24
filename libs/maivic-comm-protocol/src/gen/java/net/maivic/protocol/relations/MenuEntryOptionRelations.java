//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'menu_entry_options'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.MenuEntryOption;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.MenuEntry;
@Table("menu_entry_options")
public interface MenuEntryOptionRelations {
  @Relation(name="menu_entry",type=RelationType.ONE_TO_MANY)
  LazyResponse<MenuEntry>getMenuEntry(MenuEntryOption object);
};

