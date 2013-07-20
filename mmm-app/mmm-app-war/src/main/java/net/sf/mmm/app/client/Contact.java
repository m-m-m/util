/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.app.client;

import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import net.sf.mmm.util.pojo.api.Pojo;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.transferobject.api.TransferObject;

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

  /** {@link TypedProperty} for {@link #getBirthday()}. */
  TypedProperty<String> PROPERTY_BIRTHDAY = new TypedProperty<String>("birthday");

  /**
   * @return the firstName
   */
  @NotNull
  String getFirstName();

  /**
   * @param firstName is the firstName to set
   */
  void setFirstName(String firstName);

  /**
   * @return the lastName
   */
  @NotNull
  String getLastName();

  /**
   * @param lastName is the lastName to set
   */
  void setLastName(String lastName);

  /**
   * @return the birthday
   */
  @Past
  Date getBirthday();

  /**
   * @param birthday is the birthday to set
   */
  void setBirthday(Date birthday);

}
