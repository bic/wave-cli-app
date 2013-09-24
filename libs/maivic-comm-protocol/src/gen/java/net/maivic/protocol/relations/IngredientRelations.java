//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'ingredients'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.protocol.Model.Image;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Ingredient;
import net.maivic.comm.Relation;
import net.maivic.protocol.Model.MenuEntry;
@Table("ingredients")
public interface IngredientRelations {
  @Relation(name="image",type=RelationType.ONE_TO_MANY)
  LazyResponse<Image>getImage(Ingredient object);
  @Relation(name="menu_entries",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<MenuEntry>>getMenuEntries(Ingredient  o);
};

