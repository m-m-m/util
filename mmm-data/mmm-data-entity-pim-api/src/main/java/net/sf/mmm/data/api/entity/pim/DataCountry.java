/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim;

import net.sf.mmm.data.api.DataSelectionList;
import net.sf.mmm.data.api.entity.DataEntity;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataCountry extends DataEntity, DataSelectionList {

  /**
   * This method gets the phone code of this country (e.g. "49" for Germany, "1"
   * for USA, etc.).
   * 
   * @return the county code or <code>null</code> if undefined.
   */
  Integer getCountryCode();

  /**
   * This method gets the 2-letter code of the country according to ISO 639.
   * 
   * @see java.util.Locale#getLanguage()
   * 
   * @return the 2-letter ISO code.
   */
  String getIsoCode();

}
