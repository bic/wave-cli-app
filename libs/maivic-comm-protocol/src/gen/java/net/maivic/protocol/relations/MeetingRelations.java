//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'meetings'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import java.util.List;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Address;
import net.maivic.protocol.Model.Meeting;
import net.maivic.protocol.Model.MeetingsToContact;
import net.maivic.comm.Relation;
@Table("meetings")
public interface MeetingRelations {
  @Relation(name="place_1",type=RelationType.ONE_TO_MANY)
  LazyResponse<Address>getPlace1(Meeting object);
  @Relation(name="meetings",type=RelationType.MANY_TO_ONE)
  LazyResponse<List<MeetingsToContact>>getMeetings(Meeting  o);
};
