/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.demo.transferobject;

import net.sf.mmm.util.transferobject.api.TransferObject;

/**
 * This is a {@link TransferObject} that represents a contact.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Contact extends Address {

  /** UID for serialization. */
  private static final long serialVersionUID = -2467053895251827636L;

  /** @see #getEmail() */
  private String email;

  /** @see #getTelephone() */
  private String telephone;

  /** @see #getTelefax() */
  private String telefax;

  /**
   * The constructor.
   */
  public Contact() {

    super();
  }

  /**
   * @return the email
   */
  public String getEmail() {

    return this.email;
  }

  /**
   * @param email is the email to set
   */
  public void setEmail(String email) {

    this.email = email;
  }

  /**
   * @return the telephone
   */
  public String getTelephone() {

    return this.telephone;
  }

  /**
   * @param telephone is the telephone to set
   */
  public void setTelephone(String telephone) {

    this.telephone = telephone;
  }

  /**
   * @return the telefax
   */
  public String getTelefax() {

    return this.telefax;
  }

  /**
   * @param telefax is the telefax to set
   */
  public void setTelefax(String telefax) {

    this.telefax = telefax;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.email == null) ? 0 : this.email.hashCode());
    result = prime * result + ((this.telefax == null) ? 0 : this.telefax.hashCode());
    result = prime * result + ((this.telephone == null) ? 0 : this.telephone.hashCode());
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
    Contact other = (Contact) obj;
    if (this.email == null) {
      if (other.email != null) {
        return false;
      }
    } else if (!this.email.equals(other.email)) {
      return false;
    }
    if (this.telefax == null) {
      if (other.telefax != null) {
        return false;
      }
    } else if (!this.telefax.equals(other.telefax)) {
      return false;
    }
    if (this.telephone == null) {
      if (other.telephone != null) {
        return false;
      }
    } else if (!this.telephone.equals(other.telephone)) {
      return false;
    }
    return true;
  }

}
