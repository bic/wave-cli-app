//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'menu_options'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Menu;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.MenuOption;
@Table("menu_options")
public interface MenuOptionRelations {
  @Relation(name="menu",type=RelationType.ONE_TO_MANY)
  LazyResponse<Menu>getMenu(MenuOption object);
};

