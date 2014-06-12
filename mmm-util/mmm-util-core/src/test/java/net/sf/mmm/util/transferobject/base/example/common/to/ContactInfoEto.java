/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base.example.common.to;

import javax.persistence.Entity;

import net.sf.mmm.util.transferobject.api.EntityTo;
import net.sf.mmm.util.transferobject.base.example.common.ContactInfo;

/**
 * This is the {@link EntityTo ETO} implementing {@link ContactInfo}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@Entity
public class ContactInfoEto extends EntityTo<Long> implements ContactInfo {

  /** UID for serialization. */
  private static final long serialVersionUID = 1468867969942249329L;

  /** @see #getPhone */
  private String phone;

  /** @see #getEmail() */
  private String email;

  /** @see #getFax() */
  private String fax;

  /**
   * The constructor.
   */
  public ContactInfoEto() {

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
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
    result = prime * result + ((this.fax == null) ? 0 : this.fax.hashCode());
    result = prime * result + ((this.phone == null) ? 0 : this.phone.hashCode());
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean equals(Object obj) {

    if (this == obj) {
      return true;
    }
    if (!super.equals(obj)) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    ContactInfoEto other = (ContactInfoEto) obj;
    if (this.email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!this.email.equals(other.email)) {
      return false;
    }
    if (this.fax == null) {
      if (other.fax != null) {
        return false;
      }
    } else if (!this.fax.equals(other.fax)) {
      return false;
    }
    if (this.phone == null) {
      if (other.phone != null) {
        return false;
      }
    } else if (!this.phone.equals(other.phone)) {
      return false;
    }
    return true;
  }

}
