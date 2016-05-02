/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base.example.common;

import net.sf.mmm.util.entity.api.GenericEntity;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

/**
 * This is the interface for a contact person.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface Person extends GenericEntity<Long> {

  /** {@link TypedProperty} for {@link #getFirstName()}. */
  TypedProperty<String> PROPERTY_FIRST_NAME = new TypedProperty<>("First Name", String.class, "firstName");

  /** {@link TypedProperty} for {@link #getLastName()}. */
  TypedProperty<String> PROPERTY_LAST_NAME = new TypedProperty<>("lastName");

  /**
   * @return the firstName
   */
  String getFirstName();

  /**
   * @param firstName is the firstName to set
   */
  void setFirstName(String firstName);

  /**
   * @return the lastName
   */
  String getLastName();

  /**
   * @param lastName is the lastName to set
   */
  void setLastName(String lastName);

  /**
   * @return the {@link #getId() ID} of the {@link Address} of this {@link Person} or {@code null} if not present.
   */
  Long getAddressId();

  /**
   * @param addressId is the new value of {@link #getAddressId()}.
   */
  void setAddressId(Long addressId);

}
