/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.address;

import net.sf.mmm.data.api.entity.DataEntityView;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.datatype.api.address.PostalCode;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the view interface for a
 * {@link net.sf.mmm.data.api.entity.DataEntityView} that represents an address.
 * 
 * @see net.sf.mmm.data.api.entity.pim.contact.DataContactView
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataAddressView.CLASS_ID, title = DataAddressView.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataAddressView extends DataEntityView {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_ADDRESS;

  /**
   * The {@link net.sf.mmm.data.api.DataObjectView#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataAddress";

  /**
   * This method gets the country where the address is located.
   * 
   * @return the country or <code>null</code> if not defined.
   */
  DataCountryView getCountry();

  // TODO Datatype?
  /**
   * This method gets the country or province where the address is located.
   * Whether this field mandatory or for a valid address depends on the
   * {@link #getCountry() country}. E.g. in the USA the state is important to
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
