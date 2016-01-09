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

  /** UID for serialization. */
  private static final long serialVersionUID = 1L;

  /** @see #getAddressId() */
  private Long addressId;

  /** @see #getLastName() */
  private String lastName;

  /** @see #getFirstName() */
  private String firstName;

  /**
   * The constructor.
   */
  public PersonEto() {

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
  public Long getAddressId() {

    return this.addressId;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAddressId(Long addressId) {

    this.addressId = addressId;
  }

}
