/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import java.util.Date;

import net.sf.mmm.util.transferobject.api.TransferObject;

/**
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class ContactBean implements TransferObject {

  private String firstName;

  private String lastName;

  private Date birthday;

  /**
   * The constructor.
   */
  public ContactBean() {

    super();
  }

  /**
   * @return the firstName
   */
  public String getFirstName() {

    return this.firstName;
  }

  /**
   * @param firstName is the firstName to set
   */
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  /**
   * @return the lastName
   */
  public String getLastName() {

    return this.lastName;
  }

  /**
   * @param lastName is the lastName to set
   */
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  /**
   * @return the birthday
   */
  public Date getBirthday() {

    return this.birthday;
  }

  /**
   * @param birthday is the birthday to set
   */
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
