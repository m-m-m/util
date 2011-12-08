/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim;

import net.sf.mmm.util.datatype.api.address.Iso2CountryCode;
import net.sf.mmm.util.datatype.api.phone.CountryCode;
import net.sf.mmm.util.datatype.api.phone.InternationalCallPrefix;

/**
 * This is the interface for a mutable {@link DataCountryView}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataCountry extends DataCountryView {

  /**
   * This method sets the {@link #getCallPrefix() call prefix}.
   * 
   * @param callPrefix is the {@link InternationalCallPrefix} to set.
   */
  void setCallPrefix(InternationalCallPrefix callPrefix);

  /**
   * This method sets the {@link #getCountryCode() country code}.
   * 
   * @param countryCode is the {@link CountryCode} to set.
   */
  void setCountryCode(CountryCode countryCode);

  /**
   * This method sets the {@link #getIsoCode() ISO code}.
   * 
   * @param isoCode is the ISO code to set.
   */
  void setIsoCode(Iso2CountryCode isoCode);

}
