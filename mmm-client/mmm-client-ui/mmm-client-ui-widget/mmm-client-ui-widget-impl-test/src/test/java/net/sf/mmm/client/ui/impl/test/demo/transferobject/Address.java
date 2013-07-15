/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.demo.transferobject;

import javax.validation.constraints.NotNull;

import net.sf.mmm.util.datatype.api.address.PostalCode;
import net.sf.mmm.util.pojo.api.Pojo;
import net.sf.mmm.util.pojo.path.api.TypedProperty;
import net.sf.mmm.util.transferobject.api.TransferObject;

/**
 * This is a {@link TransferObject} that represents an address.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class Address implements TransferObject, Pojo {

  /** UID for serialization. */
  private static final long serialVersionUID = 8476194602324166240L;

  /** {@link TypedProperty} for {@link #getCountry()}. */
  public static final TypedProperty<String> PROPERTY_COUNTRY = new TypedProperty<String>(String.class, "country");

  /** {@link TypedProperty} for {@link #getCity()}. */
  public static final TypedProperty<String> PROPERTY_CITY = new TypedProperty<String>(String.class, "city");

  /** {@link TypedProperty} for {@link #getHouseNumber()}. */
  public static final TypedProperty<String> PROPERTY_HOUSE_NUMBER = new TypedProperty<String>(String.class,
      "houseNumber");

  /** {@link TypedProperty} for {@link #getPostalCode()}. */
  public static final TypedProperty<PostalCode> PROPERTY_POSTAL_CODE = new TypedProperty<PostalCode>(PostalCode.class,
      "houseNumber");

  /** {@link TypedProperty} for {@link #getState()}. */
  public static final TypedProperty<String> PROPERTY_STATE = new TypedProperty<String>(String.class, "state");

  /** {@link TypedProperty} for {@link #getStreet()}. */
  public static final TypedProperty<String> PROPERTY_STREET = new TypedProperty<String>(String.class, "street");

  /** @see #getCountry() */
  private String country;

  /** @see #getCity() */
  private String city;

  /** @see #getHouseNumber() */
  private String houseNumber;

  /** @see #getPostalCode() */
  private PostalCode postalCode;

  /** @see #getState() */
  private String state;

  /** @see #getStreet() */
  private String street;

  /**
   * The constructor.
   */
  public Address() {

    super();
  }

  /**
   * This method gets the country where the address is located.
   * 
   * @return the country or <code>null</code> if not defined.
   */
  public String getCountry() {

    return this.country;
  }

  /**
   * This method sets the {@link #getCountry() country}.
   * 
   * @param country is the country to set.
   */
  public void setCountry(String country) {

    this.country = country;
  }

  /**
   * This method gets the city (or village) the address is located in. This is a very important information.
   * However, it may be possible to derive the city from the {@link #getPostalCode() postal code}.
   * 
   * @return the city or <code>null</code> if not defined.
   */
  @NotNull
  public String getCity() {

    return this.city;
  }

  /**
   * This method sets the {@link #getCity() city}.
   * 
   * @param city is the new city to set.
   */
  public void setCity(String city) {

    this.city = city;
  }

  /**
   * This method gets the house number of the address in the {@link #getStreet() street}. Please note that the
   * house number can be alphanumeric (e.g. "12 abc"). In most locations the house number starts with a digit.
   * However, in some locations the house number starts with a letter (e.g. "N123") or is a name and no number
   * at all.
   * 
   * @return the house number or <code>null</code> if not defined.
   */
  public String getHouseNumber() {

    return this.houseNumber;
  }

  /**
   * This method sets the {@link #getHouseNumber() house number}.
   * 
   * @param houseNumber is the house number to set.
   */
  public void setHouseNumber(String houseNumber) {

    this.houseNumber = houseNumber;
  }

  /**
   * This method gets the {@link PostalCode}.
   * 
   * @return the {@link PostalCode} or <code>null</code> if not defined.
   */
  public PostalCode getPostalCode() {

    return this.postalCode;
  }

  /**
   * This method sets the {@link #getPostalCode() postal code}.
   * 
   * @param postalCode is the postal code to set.
   */
  public void setPostalCode(PostalCode postalCode) {

    this.postalCode = postalCode;
  }

  /**
   * This method gets the country or province where the address is located. Whether this field mandatory or
   * for a valid address depends on the {@link #getCountry() country}. E.g. in the USA the state is important
   * to identify an address while e.g. in Germany it is optional.
   * 
   * @return the state/province or <code>null</code> if not defined.
   */
  public String getState() {

    return this.state;
  }

  /**
   * This method sets the {@link #getState() state/province}.
   * 
   * @param state is the state/province to set.
   */
  public void setState(String state) {

    this.state = state;
  }

  /**
   * This method gets the street the address is located in. This is excluding the {@link #getHouseNumber()
   * house number}.
   * 
   * @return the street.
   */
  public String getStreet() {

    return this.street;
  }

  /**
   * This method sets the {@link #getStreet() street}.
   * 
   * @param street is the street to set.
   */
  public void setStreet(String street) {

    this.street = street;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int hashCode() {

    final int prime = 31;
    int result = 1;
    result = prime * result + ((this.city == null) ? 0 : this.city.hashCode());
    result = prime * result + ((this.country == null) ? 0 : this.country.hashCode());
    result = prime * result + ((this.houseNumber == null) ? 0 : this.houseNumber.hashCode());
    result = prime * result + ((this.postalCode == null) ? 0 : this.postalCode.hashCode());
    result = prime * result + ((this.state == null) ? 0 : this.state.hashCode());
    result = prime * result + ((this.street == null) ? 0 : this.street.hashCode());
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
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    Address other = (Address) obj;
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
    if (this.postalCode == null) {
      if (other.postalCode != null) {
        return false;
      }
    } else if (!this.postalCode.equals(other.postalCode)) {
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
    return true;
  }

}
