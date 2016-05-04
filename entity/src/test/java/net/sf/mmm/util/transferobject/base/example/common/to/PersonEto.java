/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base.example.common.to;

import net.sf.mmm.util.transferobject.api.EntityTo;
import net.sf.mmm.util.transferobject.base.example.common.Person;

/**
 * This is the {@link EntityTo ETO} implementing {@link Person}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public class PersonEto extends EntityTo<Long> implements Person {

  private static final long serialVersionUID = 1L;

  private Long addressId;

  private String lastName;

  private String firstName;

  /**
   * The constructor.
   */
  public PersonEto() {

    super();
  }

  @Override
  public String getFirstName() {

    return this.firstName;
  }

  @Override
  public void setFirstName(String firstName) {

    this.firstName = firstName;
  }

  @Override
  public String getLastName() {

    return this.lastName;
  }

  @Override
  public void setLastName(String lastName) {

    this.lastName = lastName;
  }

  @Override
  public Long getAddressId() {

    return this.addressId;
  }

  @Override
  public void setAddressId(Long addressId) {

    this.addressId = addressId;
  }

}
