/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.impl.entity.pim.contact;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

import net.sf.mmm.data.api.entity.pim.address.DataAddress;
import net.sf.mmm.data.api.entity.pim.address.DataAddressView;
import net.sf.mmm.data.api.entity.pim.address.DataCountry;
import net.sf.mmm.data.impl.entity.AbstractDataEntity;
import net.sf.mmm.data.impl.entity.pim.DataCountryImpl;
import net.sf.mmm.util.datatype.api.address.PostalCode;

import org.hibernate.annotations.Type;

/**
 * This is the implementation of {@link DataAddress} using JPA.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Entity(name = DataAddressView.CLASS_TITLE)
public class DataAddressImpl extends AbstractDataEntity implements DataAddress {

  /** UID for serialization. */
  private static final long serialVersionUID = -4971835633052193613L;

  /** @see #getCountry() */
  private DataCountryImpl country;

  /** @see #getCity() */
  private String city;

  /** @see #getStreet() */
  private String street;

  /** @see #getHouseNumber() */
  private String houseNumber;

  /** @see #getPostalCode() */
  private PostalCode postalCode;

  /** @see #getState() */
  private String state;

  /**
   * {@inheritDoc}
   */
  @Override
  @Transient
  protected long getStaticDataClassId() {

    return DataAddressView.CLASS_ID;
  }

  /**
   * @return the country
   */
  @Override
  @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.PERSIST, CascadeType.MERGE })
  public DataCountryImpl getCountry() {

    return this.country;
  }

  /**
   * @param country is the country to set
   */
  @Override
  public void setCountry(DataCountry country) {

    this.country = (DataCountryImpl) country;
  }

  /**
   * @return the city
   */
  @Override
  public String getCity() {

    return this.city;
  }

  /**
   * @param city is the city to set
   */
  @Override
  public void setCity(String city) {

    this.city = city;
  }

  /**
   * @return the street
   */
  @Override
  public String getStreet() {

    return this.street;
  }

  /**
   * @param street is the street to set
   */
  @Override
  public void setStreet(String street) {

    this.street = street;
  }

  /**
   * @return the houseNumber
   */
  @Override
  public String getHouseNumber() {

    return this.houseNumber;
  }

  /**
   * @param houseNumber is the houseNumber to set
   */
  @Override
  public void setHouseNumber(String houseNumber) {

    this.houseNumber = houseNumber;
  }

  /**
   * @return the postalCode
   */
  @Override
  public PostalCode getPostalCode() {

    return this.postalCode;
  }

  /**
   * @param postalCode is the postalCode to set
   */
  @Override
  @Type(type = "net.sf.mmm.data.impl.datatype.usertype.PostalCodeUserType")
  public void setPostalCode(PostalCode postalCode) {

    this.postalCode = postalCode;
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

}
