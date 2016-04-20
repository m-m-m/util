/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.query.base.example;

import net.sf.mmm.util.bean.api.Entity;

/**
 * Interface for a contact as an {@link Entity} for testing.
 *
 * @author hohwille
 * @since 8.0.0
 */
public interface Contact extends Entity<Long> {

  String getFirstName();

  void setFirstName(String firstName);

  String getLastName();

  void setLastName(String lastName);

  Integer getAge();

  void setAge(Integer age);

  boolean isFriend();

  void setFriend(boolean friend);

  ContactType getType();

  void setType(ContactType type);

}
