/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.contact;

import net.sf.mmm.data.api.entity.DataEntityView;
import net.sf.mmm.data.api.link.LinkList;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.data.api.reflection.DataFieldAnnotation;
import net.sf.mmm.data.api.reflection.DataFieldIds;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a {@link DataEntityView} that represents a contact.
 * Such object combines all important information to get or stay in contact with
 * a {@link #getPerson() person}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataContactView.CLASS_ID, title = DataContactView.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataContactView extends DataEntityView {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_CONTACT;

  /**
   * The {@link net.sf.mmm.data.api.DataObjectView#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataContact";

  /**
   * The {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier} of
   * {@link #getRelatedContacts() related person} that is a spouse (husband or
   * wife) or girl-/boy-friend.
   */
  String RELATED_PERSON_CLASSIFIER_PARTNER = "partner";

  /**
   * The {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier} of
   * {@link #getRelatedContacts() related person} that is a relative (aunt,
   * uncle, niece, nephew, etc.).
   */
  String RELATED_PERSON_CLASSIFIER_RELATIVE = "relative";

  /**
   * The {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier} of
   * {@link #getRelatedContacts() related person} that is a parent (father or
   * mother).<br/>
   * This is a asymmetrically classifier. See
   * {@link net.sf.mmm.data.api.link.LinkManager#getInverseClassifier(String)}
   * for further details.
   */
  String RELATED_PERSON_CLASSIFIER_PARENT = "parent";

  /**
   * The {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier} of
   * private {@link #getContactInfos() contact infos}.
   */
  String CONTACT_INFO_CLASSIFIER_PRIVATE = "private";

  /**
   * The {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier} of
   * business {@link #getContactInfos() contact infos}.
   */
  String CONTACT_INFO_CLASSIFIER_BUSINESS = "business";

  /**
   * This method gets the {@link DataPersonView person} associated with this
   * contact.
   * 
   * @return the person.
   */
  DataPersonView getPerson();

  /**
   * This method gets the list of related {@link DataContactView contacts}.
   * 
   * @see #RELATED_PERSON_CLASSIFIER_PARTNER
   * @see #RELATED_PERSON_CLASSIFIER_RELATIVE
   * @see #RELATED_PERSON_CLASSIFIER_PARENT
   * 
   * @return the {@link LinkList} with related contacts.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_CONTACT_RELATED_CONTACTS)
  LinkList<? extends DataContactView> getRelatedContacts();

  /**
   * This method gets the default {@link DataContactInfoView} out of the
   * {@link #getContactInfos() contact infos}.
   * 
   * @return the default {@link DataContactInfoView} or <code>null</code> if
   *         undefined (list of {@link #getContactInfos() contact infos} is
   *         empty).
   */
  DataContactInfoView getDefaultContactInfo();

  /**
   * This method gets the list of {@link DataContactInfoView}s associated with
   * this contact. Each {@link DataContactInfoView} is
   * {@link net.sf.mmm.data.api.link.Link#getClassifier() classified}. There
   * should be no contact infos in this {@link LinkList} with the same
   * {@link net.sf.mmm.data.api.link.Link#getClassifier() classifier}.
   * 
   * @see #CONTACT_INFO_CLASSIFIER_BUSINESS
   * @see #CONTACT_INFO_CLASSIFIER_PRIVATE
   * 
   * @return the {@link LinkList} of {@link DataContactInfoView contact infos}.
   *         Initially empty but NOT <code>null</code>.
   */
  @DataFieldAnnotation(id = DataFieldIds.ID_CONTACT_CONTACT_INFOS)
  LinkList<? extends DataContactInfoView> getContactInfos();

}
