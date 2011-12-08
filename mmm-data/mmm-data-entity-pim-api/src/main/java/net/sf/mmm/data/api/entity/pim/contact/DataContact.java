/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.contact;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.link.MutableLinkList;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;

/**
 * This is the interface for a mutable {@link DataContactView contact}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataContactView.CLASS_ID, title = DataContactView.CLASS_TITLE)
public interface DataContact extends DataContactView, DataEntity {

  /**
   * This method sets the {@link #getPerson() person}.
   * 
   * @param person is the {@link DataPerson} to set.
   */
  void setPerson(DataPerson person);

  /**
   * {@inheritDoc}
   */
  MutableLinkList<DataContact> getRelatedContacts();

  /**
   * {@inheritDoc}
   */
  MutableLinkList<DataContactInfo> getContactInfos();

}
