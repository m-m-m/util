/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.contact;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.link.MutableLinkList;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a {@link DataEntity} that represents a contact. Such object combines all
 * important information to get or stay in contact with a {@link #getPerson() person}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataContact.CLASS_ID, title = DataContact.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataContact extends DataEntity {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_CONTACT;

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataContact";

  /**
   * The {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier} of {@link #getRelatedContacts()
   * related person} that is a spouse (husband or wife) or girl-/boy-friend.
   */
  String RELATED_PERSON_CLASSIFIER_PARTNER = "partner";

  /**
   * The {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier} of {@link #getRelatedContacts()
   * related person} that is a relative (aunt, uncle, niece, nephew, etc.).
   */
  String RELATED_PERSON_CLASSIFIER_RELATIVE = "relative";

  /**
   * The {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier} of {@link #getRelatedContacts()
   * related person} that is a parent (father or mother).<br/>
   * This is a asymmetrically classifier. See
   * {@link net.sf.mmm.data.api.link.LinkManager#getInverseClassifier(String)} for further details.
   */
  String RELATED_PERSON_CLASSIFIER_PARENT = "parent";

  /**
   * The {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier} of private
   * {@link #getContactInfos() contact infos}.
   */
  String CONTACT_INFO_CLASSIFIER_PRIVATE = "private";

  /**
   * The {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier} of business
   * {@link #getContactInfos() contact infos}.
   */
  String CONTACT_INFO_CLASSIFIER_BUSINESS = "business";

  /**
   * This method gets the {@link DataPerson person} associated with this contact.
   * 
   * @return the person.
   */
  DataPerson getPerson();

  /**
   * This method sets the {@link #getPerson() person}.
   * 
   * @param person is the {@link DataPerson} to set.
   */
  void setPerson(DataPerson person);

  /**
   * This method gets the default {@link DataContactInfo} out of the {@link #getContactInfos() contact infos}.
   * 
   * @return the default {@link DataContactInfo} or <code>null</code> if undefined (list of
   *         {@link #getContactInfos() contact infos} is empty).
   */
  DataContactInfo getDefaultContactInfo();

  /**
   * This method gets the list of {@link DataContactInfo}s associated with this contact. Each
   * {@link DataContactInfo} is {@link net.sf.mmm.data.api.link.Link#getClassifier() classified}. There should
   * be no contact infos in this {@link net.sf.mmm.data.api.link.LinkList} with the same
   * {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier}.
   * 
   * @see #CONTACT_INFO_CLASSIFIER_BUSINESS
   * @see #CONTACT_INFO_CLASSIFIER_PRIVATE
   * 
   * @return the {@link MutableLinkList} of {@link DataContactInfo contact infos}. Initially empty but NOT
   *         <code>null</code>.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_CONTACT_CONTACT_INFOS)
  MutableLinkList<DataContactInfo> getContactInfos();

  /**
   * This method gets the list of related {@link DataContact contacts}.
   * 
   * @see #RELATED_PERSON_CLASSIFIER_PARTNER
   * @see #RELATED_PERSON_CLASSIFIER_RELATIVE
   * @see #RELATED_PERSON_CLASSIFIER_PARENT
   * 
   * @return the {@link MutableLinkList} with related contacts.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_CONTACT_RELATED_CONTACTS)
  MutableLinkList<DataContact> getRelatedContacts();

}
