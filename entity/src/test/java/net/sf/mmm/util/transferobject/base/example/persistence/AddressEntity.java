/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base.example.persistence;

import javax.persistence.Entity;

import net.sf.mmm.util.entity.api.PersistenceEntity;
import net.sf.mmm.util.transferobject.base.example.common.Address;

/**
 * This is the {@link PersistenceEntity} implementing {@link Address}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
@Entity
public class AddressEntity extends AbstractEntity<Long> implements Address {

  /** UID for serialization. */
  private static final long serialVersionUID = 1468867969942249329L;

  /** @see #getCity() */
  private String city;

  /** @see #getHouseNumber() */
  private String houseNumber;

  /** @see #getState() */
  private String state;

  /** @see #getStreet() */
  private String street;

  /** @see #getZip() */
  private String zip;

  /** @see #getCountry() */
  private String country;

  /**
   * The constructor.
   */
  public AddressEntity() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCity() {

    return this.city;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCity(String city) {

    this.city = city;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getHouseNumber() {

    return this.houseNumber;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHouseNumber(String houseNumber) {

    this.houseNumber = houseNumber;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getState() {

    return this.state;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setState(String state) {

    this.state = state;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getStreet() {

    return this.street;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setStreet(String street) {

    this.street = street;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getZip() {

    return this.zip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setZip(String zip) {

    this.zip = zip;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getCountry() {

    return this.country;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setCountry(String country) {

    this.country = country;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = super.hashCode();
    result = prime * result + ((this.city == null) ? 0 : this.city.hashCode());
    result = prime * result + ((this.country == null) ? 0 : this.country.hashCode());
    result = prime * result + ((this.houseNumber == null) ? 0 : this.houseNumber.hashCode());
    result = prime * result + ((this.state == null) ? 0 : this.state.hashCode());
    result = prime * result + ((this.street == null) ? 0 : this.street.hashCode());
    result = prime * result + ((this.zip == null) ? 0 : this.zip.hashCode());
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
    AddressEntity other = (AddressEntity) obj;
    if (this.city == null) {
      if (other.city != null) {
        return false;
      }
    } else if (!this.city.equals(other.city)) {
      return false;
    }
    if (this.country == null) {
      if (other.country != null) {
        return false;
      }
    } else if (!this.country.equals(other.country)) {
      return false;
    }
    if (this.houseNumber == null) {
      if (other.houseNumber != null) {
        return false;
      }
    } else if (!this.houseNumber.equals(other.houseNumber)) {
      return false;
    }
    if (this.state == null) {
      if (other.state != null) {
        return false;
      }
    } else if (!this.state.equals(other.state)) {
      return false;
    }
    if (this.street == null) {
      if (other.street != null) {
        return false;
      }
    } else if (!this.street.equals(other.street)) {
      return false;
    }
    if (this.zip == null) {
      if (other.zip != null) {
        return false;
      }
    } else if (!this.zip.equals(other.zip)) {
      return false;
    }
    return true;
  }

}
