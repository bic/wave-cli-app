//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'menu_options'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Menu;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.MenuOption;
import net.maivic.comm.Callback;
@Table("menu_options")
public interface MenuOptionRelations {
  @Relation(name="menu",type=RelationType.MANY_TO_ONE)
  LazyResponse<Menu>getMenu(MenuOption object , Callback<Menu> cb);
  @Relation(name="offer_options",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<OfferOption>>getOfferOptions(MenuOption object , Callback<OfferOption> cb);
};

