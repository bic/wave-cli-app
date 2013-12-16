//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'offer_options'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.Callback;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.MenuOption;
@Table("offer_options")
public interface OfferOptionRelations {
  @Relation(name="menu_options",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<MenuOption>>getMenuOptions(OfferOption  o , Callback<MenuOption> cb);
  @Relation(name="offers",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Offer>>getOffers(OfferOption object , Callback<Offer> cb);
};

