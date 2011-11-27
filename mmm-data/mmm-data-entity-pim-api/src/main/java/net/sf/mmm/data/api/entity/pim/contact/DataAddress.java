/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.contact;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.entity.pim.DataCountry;
import net.sf.mmm.util.datatype.api.address.PostalCode;

/**
 * This is the interface for a {@link net.sf.mmm.data.api.entity.DataEntity}
 * that represents an address.
 * 
 * @see DataContact
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataAddress extends DataEntity {

  // TODO: Datatype/Entity ?
  /**
   * This method gets the country where the address is located.
   * 
   * @return the country or <code>null</code> if not defined.
   */
  DataCountry getCountry();

  // TODO Datatype?
  /**
   * This method gets the country or province where the address is located.
   * Depending on the {@link #getCountry() country} this property is mandatory
   * for a valid address or not. E.g. in the USA the state is important to
   * identify an address while e.g. in Germany it is optional.
   * 
   * @return the state/province or <code>null</code> if not defined.
   */
  String getState();

  // TODO Datatype?
  /**
   * This method gets the city (or village) the address is located in. This is a
   * very important information. However, it may be possible to derive the city
   * from the {@link #getPostalCode() postal code}.
   * 
   * @return the city or <code>null</code> if not defined.
   */
  String getCity();

  /**
   * This method gets the street the address is located in. This is excluding
   * the {@link #getHouseNumber() house number}.
   * 
   * @return the street.
   */
  String getStreet();

  // TODO Datatype?
  /**
   * This method gets the house number of the address in the
   * {@link #getStreet() street}. Please note that the house number can be
   * alphanumeric (e.g. "12 abc"). In most locations the house number starts
   * with a digit. However, in some locations the house number starts with a
   * letter (e.g. "N123") or is a name and no number at all.
   * 
   * @return the house number or <code>null</code> if not defined.
   */
  String getHouseNumber();

  // TODO Datatype?
  /**
   * This method gets the {@link PostalCode}.
   * 
   * @return the {@link PostalCode} or <code>null</code> if not defined.
   */
  PostalCode getPostalCode();

}
