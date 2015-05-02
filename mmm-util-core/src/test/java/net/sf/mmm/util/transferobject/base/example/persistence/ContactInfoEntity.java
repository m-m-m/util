/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base.example.persistence;

import javax.persistence.Entity;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.transferobject.base.example.common.Address;
import net.sf.mmm.util.transferobject.base.example.common.ContactInfo;

/**
 * This is the {@link PersistenceEntity} implementing {@link Address}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@Entity
public class ContactInfoEntity extends AbstractEntity<Long> implements ContactInfo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1468867969942249329L;

  /** @see #getPhone */
  private String phone;

  /** @see #getEmail() */
  private String email;

  /** @see #getFax() */
  private String fax;

  /** @see #getType() */
  private String type;

  /**
   * The constructor.
   */
  public ContactInfoEntity() {

    super();
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
  public void setPhone(String phone) {

    this.phone = phone;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getFax() {

    return this.fax;
  }

  /**
   * {@inheritDoc}
   */
  public void setFax(String fax) {

    this.fax = fax;
  }

  /**
   * @return the type of this {@link ContactInfo} (e.g. "private", "business", etc.).
   */
  public String getType() {

    return this.type;
  }

  /**
   * @param type is the new value of {@link #getType()}.
   */
  public void setType(String type) {

    this.type = type;
  }

}
