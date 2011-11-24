/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.contact;

import java.util.Date;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.util.datatype.api.Gender;

/**
 * This is the interface for a {@link DataEntity} that represents a natural
 * person.
 * 
 * @see DataContact
 * 
 * @author hohwille
 * @since 1.0.0
 */
public interface DataPerson extends DataEntity {

  /**
   * This method gets the {@link Gender} of the person.
   * 
   * @return the {@link Gender} or <code>null</code> if undefined (unknown).
   */
  Gender getGender();

  /**
   * This method gets the first name of the person. This can also be a double
   * name like "Jean-Pascal".
   * 
   * @return the first name or <code>null</code> if undefined (unknown).
   */
  String getFirstName();

  /**
   * This method gets the middle name(s) of the person. This can also be a list
   * of additional names.
   * 
   * @return the middle name(s) or <code>null</code> if undefined (unknown).
   */
  String getMiddleName();

  /**
   * This method gets the last name (also called surname or family name) of the
   * person. This is typically the most important and official part of the name
   * in order to identify or speak to the person.
   * 
   * @return the last name or <code>null</code> if undefined (unknown).
   */
  String getLastName();

  /**
   * This method gets the optional name prefix. This is typically an academic,
   * professional, social, or different title.
   * 
   * @return the name prefix or <code>null</code> if undefined (unknown).
   */
  String getNamePrefix();

  /**
   * This method gets the optional name suffix. This is typically some official
   * title. E.g. various emperors had Roman numbers (e.g. "XIV" for 14) as
   * suffix to their name. Another example is the suffix "SJ" for the catholic
   * order "societas jesu".
   * 
   * @return the name suffix or <code>null</code> if undefined (unknown).
   */
  String getNameSuffix();

  /**
   * <b>ATTENTION:</b><br/>
   * This method is inherited from {@link net.sf.mmm.data.api.DataObject}. In
   * the context of a person, you might expect something like a "doctor" as
   * title what is addressed by {@link #getNamePrefix()}. Instead this method
   * will return a formatted string combining various fields of the person.<br/>
   * 
   * {@inheritDoc}
   */
  String getTitle();

  /**
   * This method gets the birthday of the person.
   * 
   * @return the {@link Date} of the birth or <code>null</code> if undefined
   *         (unknown).
   */
  Date getDateOfBirth();

}
