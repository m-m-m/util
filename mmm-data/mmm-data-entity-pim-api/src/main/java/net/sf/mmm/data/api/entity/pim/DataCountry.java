/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim;

import net.sf.mmm.data.api.DataSelectionList;
import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.util.datatype.api.phone.CountryCode;
import net.sf.mmm.util.datatype.api.phone.InternationalCallPrefix;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataCountry extends DataEntity, DataSelectionList {

  /**
   * This method gets the official name of the country in English language. For
   * internationalization see {@link net.sf.mmm.util.nls.api.NlsMessage}.
   * 
   * {@inheritDoc}
   */
  String getTitle();

  /**
   * This method gets the {@link InternationalCallPrefix} (the exit code) to
   * dial out of the country.<br/>
   * <b>ATTENTION:</b><br/>
   * Some countries have multiple {@link InternationalCallPrefix call prefixes}.
   * In this case the method returns one of them. If there is no default
   * (carrier select) then an arbitrary one is selected (e.g. the lowest
   * number).
   * 
   * @return the {@link InternationalCallPrefix}.
   */
  InternationalCallPrefix getCallPrefix();

  /**
   * This method gets the phone code of this country (e.g. "49" for Germany, "1"
   * for USA, etc.).
   * 
   * @return the {@link CountryCode}.
   */
  CountryCode getCountryCode();

  /**
   * This method gets the 2-letter code of the country according to ISO 639.
   * 
   * @see java.util.Locale#getCountry()
   * @see java.util.Locale#getISO3Country()
   * 
   * @return the 2-letter ISO code.
   */
  String getIsoCode();

}
