/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity.pim;

import java.util.Date;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.pim.contact.DataPerson;
import net.sf.mmm.data.api.entity.pim.contact.DataPersonView;
import net.sf.mmm.data.impl.entity.AbstractDataEntity;
import net.sf.mmm.util.datatype.api.person.Gender;

/**
 * This is the implementation of {@link DataPerson} using JPA.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity
@DiscriminatorValue("" + DataPersonView.CLASS_ID)
public class DataPersonImpl extends AbstractDataEntity implements DataPerson {

  /** UID for serialization. */
  private static final long serialVersionUID = -2669388622861452452L;

  /** @see #getFirstName() */
  private String firstName;

  /** @see #getMiddleName() */
  private String middleName;

  /** @see #getLastName() */
  private String lastName;

  /** @see #getDateOfBirth() */
  private Date dateOfBirth;

  /** @see #getGender() */
  private Gender gender;

  /** @see #getNamePrefix() */
  private String namePrefix;

  /** @see #getNameSuffix() */
  private String nameSuffix;

  /**
   * The constructor.
   */
  public DataPersonImpl() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  protected long getStaticDataClassId() {

    return DataPersonView.CLASS_ID;
  }

  /**
   * {@inheritDoc}
   */
  public String getLastName() {

    return this.lastName;
  }

  /**
   * {@inheritDoc}
   */
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  /**
   * {@inheritDoc}
   */
  public Gender getGender() {

    return this.gender;
  }

  /**
   * {@inheritDoc}
   */
  public void setGender(Gender gender) {

    this.gender = gender;
  }

  /**
   * {@inheritDoc}
   */
  public String getFirstName() {

    return this.firstName;
  }

  /**
   * {@inheritDoc}
   */
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  /**
   * {@inheritDoc}
   */
  public String getMiddleName() {

    return this.middleName;
  }

  /**
   * {@inheritDoc}
   */
  public void setMiddleName(String middleName) {

    this.middleName = middleName;
  }

  /**
   * {@inheritDoc}
   */
  public String getNamePrefix() {

    return this.namePrefix;
  }

  /**
   * {@inheritDoc}
   */
  public void setNamePrefix(String namePrefix) {

    this.namePrefix = namePrefix;
  }

  /**
   * {@inheritDoc}
   */
  public String getNameSuffix() {

    return this.nameSuffix;
  }

  /**
   * {@inheritDoc}
   */
  public void setNameSuffix(String nameSuffix) {

    this.nameSuffix = nameSuffix;
  }

  /**
   * {@inheritDoc}
   */
  public Date getDateOfBirth() {

    return this.dateOfBirth;
  }

  /**
   * {@inheritDoc}
   */
  public void setDateOfBirth(Date dateOfBirth) {

    this.dateOfBirth = dateOfBirth;
  }

}
