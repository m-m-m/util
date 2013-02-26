/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.address;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.datatype.api.address.PostalCode;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a {@link net.sf.mmm.data.api.entity.DataEntity} that represents an address.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataAddress.CLASS_ID, title = DataAddress.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataAddress extends DataEntity {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_ADDRESS;

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataAddress";

  /**
   * This method gets the country where the address is located.
   * 
   * @return the country or <code>null</code> if not defined.
   */
  DataCountry getCountry();

  /**
   * This method sets the {@link #getCountry() country}.
   * 
   * @param country is the country to set.
   */
  void setCountry(DataCountry country);

  /**
   * This method gets the city (or village) the address is located in. This is a very important information.
   * However, it may be possible to derive the city from the {@link #getPostalCode() postal code}.
   * 
   * @return the city or <code>null</code> if not defined.
   */
  String getCity();

  /**
   * This method sets the {@link #getCity() city}.
   * 
   * @param city is the new city to set.
   */
  void setCity(String city);

  /**
   * This method gets the house number of the address in the {@link #getStreet() street}. Please note that the
   * house number can be alphanumeric (e.g. "12 abc"). In most locations the house number starts with a digit.
   * However, in some locations the house number starts with a letter (e.g. "N123") or is a name and no number
   * at all.
   * 
   * @return the house number or <code>null</code> if not defined.
   */
  String getHouseNumber();

  /**
   * This method sets the {@link #getHouseNumber() house number}.
   * 
   * @param houseNumber is the house number to set.
   */
  void setHouseNumber(String houseNumber);

  /**
   * This method gets the {@link PostalCode}.
   * 
   * @return the {@link PostalCode} or <code>null</code> if not defined.
   */
  PostalCode getPostalCode();

  /**
   * This method sets the {@link #getPostalCode() postal code}.
   * 
   * @param postalCode is the postal code to set.
   */
  void setPostalCode(PostalCode postalCode);

  // TODO Datatype?
  /**
   * This method gets the country or province where the address is located. Whether this field mandatory or
   * for a valid address depends on the {@link #getCountry() country}. E.g. in the USA the state is important
   * to identify an address while e.g. in Germany it is optional.
   * 
   * @return the state/province or <code>null</code> if not defined.
   */
  String getState();

  /**
   * This method sets the {@link #getState() state/province}.
   * 
   * @param state is the state/province to set.
   */
  void setState(String state);

  /**
   * This method gets the street the address is located in. This is excluding the {@link #getHouseNumber()
   * house number}.
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

}
