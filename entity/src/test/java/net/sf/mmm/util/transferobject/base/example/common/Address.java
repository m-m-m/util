/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.transferobject.base.example.common;

import net.sf.mmm.util.entity.api.GenericEntity;

/**
 * This is an example entity for testing.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 3.1.0
 */
public interface Address extends GenericEntity<Long> {

  /**
   * This method gets the city (or village) the address is located in. This is a very important information. However, it
   * may be possible to derive the city from the {@link #getZip() zip code}.
   *
   * @return the city or {@code null} if not defined.
   */
  String getCity();

  /**
   * This method sets the {@link #getCity() city}.
   *
   * @param city is the new city to set.
   */
  void setCity(String city);

  /**
   * This method gets the house number of the address in the {@link #getStreet() street}. Please note that the house
   * number can be alphanumeric (e.g. "12 abc"). In most locations the house number starts with a digit. However, in
   * some locations the house number starts with a letter (e.g. "N123") or is a name and no number at all.
   *
   * @return the house number or {@code null} if not defined.
   */
  String getHouseNumber();

  /**
   * This method sets the {@link #getHouseNumber() house number}.
   *
   * @param houseNumber is the house number to set.
   */
  void setHouseNumber(String houseNumber);

  /**
   * This method gets the country or province where the address is located. Whether this field mandatory or for a valid
   * address depends on the {@link #getCountry() country}. E.g. in the USA the state is important to identify an address
   * while e.g. in Germany it is optional.
   *
   * @return the state/province or {@code null} if not defined.
   */
  String getState();

  /**
   * This method sets the {@link #getState() state/province}.
   *
   * @param state is the state/province to set.
   */
  void setState(String state);

  /**
   * This method gets the street the address is located in. This is excluding the {@link #getHouseNumber() house number}
   * .
   *
   * @return the street.
   */
  String getStreet();

  /**
   * This method sets the {@link #getStreet() street}.
   *
   * @param street is the street to set.
   */
  void setStreet(String street);

  /**
   * This method gets the zip or postal code. This is an identifier of a region, city, or even a district within a
   * country. Different countries have different systems for their postal codes. Some are numeric some are alphanumeric.
   * In any case the postal codes aim to optimize the logistics of mail delivery and typically allow sorting mail for
   * different regions. In most cases the (major) city can be determined from country and postal code. <br>
   * <b>ATTENTION:</b><br>
   * Please note that some countries (e.g. Panama) do not have the concept of postal codes.
   *
   * @return the zip.
   */
  String getZip();

  /**
   * This method sets the {@link #getZip() zip code}.
   *
   * @param zip is the zip to set.
   */
  void setZip(String zip);

  /**
   * This method gets the country where the address is located.
   *
   * @return the country or {@code null} if not defined.
   */
  String getCountry();

  /**
   * This method sets the {@link #getCountry() country}.
   *
   * @param country is the country to set.
   */
  void setCountry(String country);
}
