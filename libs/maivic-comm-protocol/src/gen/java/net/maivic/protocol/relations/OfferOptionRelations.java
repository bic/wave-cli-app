//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'offer_options'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Offer;
import net.maivic.protocol.Model.OfferOption;
import net.maivic.comm.Relation;
@Table("offer_options")
public interface OfferOptionRelations {
  @Relation(name="offer",type=RelationType.ONE_TO_MANY)
  LazyResponse<Offer>getOffer(OfferOption object);
};

