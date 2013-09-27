//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'images'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.protocol.Model.Restaurant;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.protocol.Model.Image;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Ingredient;
import net.maivic.protocol.Model.Menu;
import net.maivic.comm.Relation;
@Table("images")
public interface ImageRelations {
  @Relation(name="menus",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Menu>>getMenus(Image  o);
  @Relation(name="ingredients",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Ingredient>>getIngredients(Image  o);
  @Relation(name="restaurants",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<Restaurant>>getRestaurants(Image  o);
};
