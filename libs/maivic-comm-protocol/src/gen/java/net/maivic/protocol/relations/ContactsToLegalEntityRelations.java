//////////////////////////////////////////////////
//DO NOT EDIT. These Files are automatically generated
//Outgoing Relations from table 'contacts_to_legal_entities'
//////////////////////////////////////////////////
package net.maivic.protocol.relations;
import net.maivic.comm.Table;
import net.maivic.protocol.Model.LegalEntity;
import net.maivic.comm.LazyResponse;
import net.maivic.protocol.Model.Contact;
import net.maivic.comm.Relation.RelationType;
import net.maivic.protocol.Model.ContactsToLegalEntity;
import net.maivic.comm.Callback;
import net.maivic.comm.Relation;
@Table("contacts_to_legal_entities")
public interface ContactsToLegalEntityRelations {
  @Relation(name="contact",type=RelationType.MANY_TO_ONE)
  LazyResponse<Contact>getContact(ContactsToLegalEntity object , Callback<Contact> cb);
  @Relation(name="legal_entity",type=RelationType.MANY_TO_ONE)
  LazyResponse<LegalEntity>getLegalEntity(ContactsToLegalEntity object , Callback<LegalEntity> cb);
};

