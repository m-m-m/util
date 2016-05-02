/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base.example.common;

import net.sf.mmm.util.entity.api.GenericEntity;

/**
 * This is an example entity for testing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public interface ContactInfo extends GenericEntity<Long> {

  /**
   * @return the email address or {@code null} if undefined.
   */
  String getEmail();

  /**
   * @param email is the new value of {@link #getEmail()}.
   */
  void setEmail(String email);

  /**
   * @return the phone number or {@code null} if undefined.
   */
  String getPhone();

  /**
   * @param phone is the new value of {@link #getPhone()}.
   */
  void setPhone(String phone);

  /**
   * @return the fax number or {@code null} if undefined.
   */
  String getFax();

  /**
   * @param fax is the new value of {@link #getFax()}.
   */
  void setFax(String fax);

}
