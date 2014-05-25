/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.editor;

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

  /** @see #getEmail() */
  private String email;

  /** @see #getPhone() */
  private String phone;

  /**
   * The constructor.
   */
  public ContactBean() {

    super();
  }

  /**
   * The constructor.
   *
   * @param firstName - see {@link #getFirstName()}.
   * @param lastName - see {@link #getLastName()}.
   * @param email - see {@link #getEmail()}.
   * @param phone - see {@link #getPhone()}.
   */
  public ContactBean(String firstName, String lastName, String email, String phone) {

    super();
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.phone = phone;
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
  public String getEmail() {

    return this.email;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEmail(String email) {

    this.email = email;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getPhone() {

    return this.phone;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setPhone(String phone) {

    this.phone = phone;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {

    StringBuilder result = new StringBuilder();
    result.append(this.lastName);
    result.append(", ");
    result.append(this.firstName);
    return result.toString();
  }

}
