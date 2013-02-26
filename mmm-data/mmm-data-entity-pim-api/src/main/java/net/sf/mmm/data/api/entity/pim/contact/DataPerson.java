/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.contact;

import java.util.Date;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.datatype.api.person.Gender;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a {@link net.sf.mmm.data.api.entity.DataEntity} that represents a natural person.
 * 
 * @see DataContact
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataPerson.CLASS_ID, title = DataPerson.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataPerson extends DataEntity {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_PERSON;

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataPerson";

  /**
   * This method gets the {@link Gender} of the person.
   * 
   * @return the {@link Gender} or <code>null</code> if undefined (unknown).
   */
  Gender getGender();

  /**
   * This method sets the {@link #getGender() gender}.
   * 
   * @param gender is the {@link Gender} to set.
   */
  void setGender(Gender gender);

  /**
   * This method gets the first name of the person. This can also be a double name like "Jean-Pascal".
   * 
   * @return the first name or <code>null</code> if undefined (unknown).
   */
  String getFirstName();

  /**
   * This method sets the {@link #getFirstName() first name}.
   * 
   * @param firstName is the first name to set.
   */
  void setFirstName(String firstName);

  /**
   * This method gets the middle name(s) of the person. This can also be a list of additional names.
   * 
   * @return the middle name(s) or <code>null</code> if undefined (unknown).
   */
  String getMiddleName();

  /**
   * This method sets the {@link #getMiddleName() middle name(s)}.
   * 
   * @param middleName is the middle name(s) to set.
   */
  void setMiddleName(String middleName);

  /**
   * This method gets the last name (also called surname or family name) of the person. This is typically the
   * most important and official part of the name in order to identify or speak to the person.
   * 
   * @return the last name or <code>null</code> if undefined (unknown).
   */
  String getLastName();

  /**
   * This method sets the {@link #getLastName() last name}.
   * 
   * @param lastName is the last name to set.
   */
  void setLastName(String lastName);

  /**
   * This method gets the optional name prefix. This is typically an academic, professional, social, or
   * different title.
   * 
   * @return the name prefix or <code>null</code> if undefined (unknown).
   */
  String getNamePrefix();

  /**
   * This method sets the {@link #getNamePrefix() name prefix}.
   * 
   * @param namePrefix is the name prefix to set.
   */
  void setNamePrefix(String namePrefix);

  /**
   * This method gets the optional name suffix. This is typically some official title. E.g. various emperors
   * had Roman numbers (e.g. "XIV" for 14) as suffix to their name. Another example is the suffix "SJ" for the
   * catholic order "societas jesu".
   * 
   * @return the name suffix or <code>null</code> if undefined (unknown).
   */
  String getNameSuffix();

  /**
   * This method gets the {@link #getNameSuffix() name suffix}.
   * 
   * @param nameSuffix is the name suffix to set.
   */
  void setNameSuffix(String nameSuffix);

  /**
   * <b>ATTENTION:</b><br/>
   * This method is inherited from {@link net.sf.mmm.data.api.DataObject}. In the context of a person, you
   * might expect something like "Mr." or a "doctor" as title what is addressed by {@link #getNamePrefix()}.
   * Instead this method will return a formatted string combining various fields of the person.<br/>
   * 
   * {@inheritDoc}
   */
  @Override
  String getTitle();

  /**
   * This method gets the birthday of the person.
   * 
   * @return the {@link Date} of the birth or <code>null</code> if undefined (unknown).
   */
  Date getDateOfBirth();

  /**
   * This method sets the {@link #getDateOfBirth() birthday}.
   * 
   * @param dateOfBirth is the {@link Date} of the birth to set.
   */
  void setDateOfBirth(Date dateOfBirth);

}
