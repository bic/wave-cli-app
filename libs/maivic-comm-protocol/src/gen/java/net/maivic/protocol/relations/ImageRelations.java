//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'images'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.protocol.Model.Image;
import net.maivic.comm.Relation.RelationType;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
@Table("images")
public interface ImageRelations {
  @Relation(name="restaurants",type=RelationType.ONE_TO_MANY)
  LazyResponse<List<Restaurant>>getRestaurants(Image  o,@SubscriptionCallback Callback<List<Restaurant>> cb);
};

