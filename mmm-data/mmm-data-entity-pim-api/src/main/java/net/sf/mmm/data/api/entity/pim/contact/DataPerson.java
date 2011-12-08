/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.contact;

import java.util.Date;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.util.datatype.api.person.Gender;

/**
 * This is the interface for a mutable {@link DataPersonView person}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataPersonView.CLASS_ID, title = DataPersonView.CLASS_TITLE)
public interface DataPerson extends DataPersonView, DataEntity {

  /**
   * This method sets the {@link #getGender() gender}.
   * 
   * @param gender is the {@link Gender} to set.
   */
  void setGender(Gender gender);

  /**
   * This method sets the {@link #getFirstName() first name}.
   * 
   * @param firstName is the first name to set.
   */
  void setFirstName(String firstName);

  /**
   * This method sets the {@link #getMiddleName() middle name(s)}.
   * 
   * @param middleName is the middle name(s) to set.
   */
  void setMiddleName(String middleName);

  /**
   * This method sets the {@link #getLastName() last name}.
   * 
   * @param lastName is the last name to set.
   */
  void setLastName(String lastName);

  /**
   * This method sets the {@link #getNamePrefix() name prefix}.
   * 
   * @param namePrefix is the name prefix to set.
   */
  void setNamePrefix(String namePrefix);

  /**
   * This method gets the {@link #getNameSuffix() name suffix}.
   * 
   * @param nameSuffix is the name suffix to set.
   */
  void setNameSuffix(String nameSuffix);

  /**
   * This method sets the {@link #getDateOfBirth() birthday}.
   * 
   * @param dateOfBirth is the {@link Date} of the birth to set.
   */
  void setDateOfBirth(Date dateOfBirth);

}
