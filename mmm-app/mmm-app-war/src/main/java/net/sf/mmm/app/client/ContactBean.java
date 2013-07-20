/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import java.util.Date;

/**
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContactBean implements Contact {

  /** UID for serialization. */
  private static final long serialVersionUID = 985011128511803342L;

  /** @see #getFirstName() */
  private String firstName;

  /** @see #getLastName() */
  private String lastName;

  /** @see #getBirthday() */
  private Date birthday;

  /**
   * The constructor.
   */
  public ContactBean() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFirstName() {

    return this.firstName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLastName() {

    return this.lastName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Date getBirthday() {

    return this.birthday;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setBirthday(Date birthday) {

    this.birthday = birthday;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder result = new StringBuilder(this.lastName);
    result.append(", ");
    result.append(this.firstName);
    if (this.birthday != null) {
      result.append(" (*");
      result.append(this.birthday);
      result.append(")");
    }
    return result.toString();
  }

}
