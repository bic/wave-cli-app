//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'meetings_to_contacts'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.Meeting;
import net.maivic.protocol.Model.MeetingsToContact;
import net.maivic.comm.Relation;
@Table("meetings_to_contacts")
public interface MeetingsToContactRelations {
  @Relation(name="contact",type=RelationType.ONE_TO_MANY)
  LazyResponse<Contact>getContact(MeetingsToContact object);
  @Relation(name="meeting",type=RelationType.ONE_TO_MANY)
  LazyResponse<Meeting>getMeeting(MeetingsToContact object);
};

