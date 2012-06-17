/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.address;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.util.datatype.api.address.PostalCode;

/**
 * This is the interface for a mutable {@link DataAddressView address}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataAddressView.CLASS_ID, title = DataAddressView.CLASS_TITLE)
public interface DataAddress extends DataAddressView, DataEntity {

  /**
   * This method sets the {@link #getCity() city}.
   * 
   * @param city is the new city to set.
   */
  void setCity(String city);

  /**
   * {@inheritDoc}
   */
  DataCountry getCountry();

  /**
   * This method sets the {@link #getCountry() country}.
   * 
   * @param country is the country to set.
   */
  void setCountry(DataCountry country);

  /**
   * This method sets the {@link #getHouseNumber() house number}.
   * 
   * @param houseNumber is the house number to set.
   */
  void setHouseNumber(String houseNumber);

  /**
   * This method sets the {@link #getPostalCode() postal code}.
   * 
   * @param postalCode is the postal code to set.
   */
  void setPostalCode(PostalCode postalCode);

  /**
   * This method sets the {@link #getState() state/province}.
   * 
   * @param state is the state/province to set.
   */
  void setState(String state);

  /**
   * This method sets the {@link #getStreet() street}.
   * 
   * @param street is the street to set.
   */
  void setStreet(String street);

}
