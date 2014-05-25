/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.showcase.client.dialog.editor;

import net.sf.mmm.util.pojo.api.Pojo;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.transferobject.api.TransferObject;
import net.sf.mmm.util.validation.base.Mandatory;

/**
 * This is the interface for a contact person.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Contact extends TransferObject, Pojo {

  /** {@link TypedProperty} for {@link #getFirstName()}. */
  TypedProperty<String> PROPERTY_FIRST_NAME = new TypedProperty<String>("First Name", String.class, "firstName");

  /** {@link TypedProperty} for {@link #getLastName()}. */
  TypedProperty<String> PROPERTY_LAST_NAME = new TypedProperty<String>("lastName");

  /** {@link TypedProperty} for {@link #getEmail()}. */
  TypedProperty<String> PROPERTY_EMAIL = new TypedProperty<String>("email");

  /** {@link TypedProperty} for {@link #getPhone()}. */
  TypedProperty<String> PROPERTY_PHONE = new TypedProperty<String>("phone");

  /**
   * @return the firstName
   */
  @Mandatory
  String getFirstName();

  /**
   * @param firstName is the firstName to set
   */
  void setFirstName(String firstName);

  /**
   * @return the lastName
   */
  @Mandatory
  String getLastName();

  /**
   * @param lastName is the lastName to set
   */
  void setLastName(String lastName);

  /**
   * @return the email address.
   */
  // @Email
  String getEmail();

  /**
   * @param email is the new value of {@link #getEmail()}.
   */
  void setEmail(String email);

  /**
   * @return the phone number.
   */
  // @PhoneNumber
  String getPhone();

  /**
   * @param phone is the new value of {@link #getPhone()}.
   */
  void setPhone(String phone);

}
