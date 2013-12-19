//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'menu_entries'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.RPCUpdateSubscriptionService;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Menu;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.MenuEntry;
@Table("menu_entries")
public interface MenuEntryRelations extends RPCUpdateSubscriptionService {
  @Relation(name="menu",type=RelationType.MANY_TO_ONE)
  LazyResponse<Menu>getMenu(MenuEntry object , @SubscriptionCallback Callback<Menu> cb);
};

