/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.contact;

import java.util.List;

import net.sf.mmm.data.api.LinkList;
import net.sf.mmm.data.api.entity.DataEntity;

/**
 * This is the interface for a {@link DataEntity} that represents a contact.
 * Such object combines all important information to get or stay in contact with
 * a {@link #getPerson() person}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataContact extends DataEntity {

  /**
   * This method gets the {@link DataPerson person} associated with this
   * contact.
   * 
   * @return the person.
   */
  DataPerson getPerson();

  LinkList<DataPerson> getRelatedPersons();

  LinkList<DataEmailAddress> getEmailAddresses();

  /**
   * This method gets the default {@link DataAddress} out of the
   * {@link #getAddresses() addresses}.
   * 
   * @return the default address or <code>null</code> if undefined (list of
   *         {@link #getAddresses() addresses} is empty).
   */
  DataAddress getDefaultAddress();

  /**
   * This method gets the list of {@link DataAddress addresses} associated with
   * this contact. Each {@link DataAddress address} is
   * {@link net.sf.mmm.data.api.entity.DataEntityClassified#getClassifier()
   * classified}. It is best practice NOT to have two addresses with the same
   * {@link net.sf.mmm.data.api.entity.DataEntityClassified#getClassifier()
   * classifier}.
   * 
   * @return the {@link List} of {@link DataAddress addresses}. Initially empty
   *         but NOT <code>null</code>.
   */
  LinkList<DataAddress> getAddresses();

}
