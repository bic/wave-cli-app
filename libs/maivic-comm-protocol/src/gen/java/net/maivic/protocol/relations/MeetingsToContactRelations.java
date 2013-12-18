//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'meetings_to_contacts'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.SubscriptionCallback;
import net.maivic.comm.Table;
import net.maivic.comm.LazyResponse;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.MeetingsToContact;
import net.maivic.protocol.Model.Meeting;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
@Table("meetings_to_contacts")
public interface MeetingsToContactRelations {
  @Relation(name="contact",type=RelationType.MANY_TO_ONE)
  LazyResponse<Contact>getContact(MeetingsToContact object , @SubscriptionCallback Callback<Contact> cb);
  @Relation(name="meeting",type=RelationType.MANY_TO_ONE)
  LazyResponse<Meeting>getMeeting(MeetingsToContact object , @SubscriptionCallback Callback<Meeting> cb);
};

