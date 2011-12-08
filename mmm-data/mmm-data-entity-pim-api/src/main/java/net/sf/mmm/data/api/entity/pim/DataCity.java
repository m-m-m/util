/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.util.datatype.api.phone.AreaCode;

/**
 * This is the interface for a mutable {@link DataCityView}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataCityView.CLASS_ID, title = DataCityView.CLASS_TITLE)
public interface DataCity extends DataCityView, DataEntity {

  /**
   * This method sets the {@link #getAreaCode() area code}.
   * 
   * @param areaCode is the {@link AreaCode} to set.
   */
  void setAreaCode(AreaCode areaCode);

  /**
   * {@inheritDoc}
   */
  DataCountry getCountry();

  /**
   * This method sets the {@link #getCountry() country}.
   * 
   * @param country is the {@link DataCountry} to set.
   */
  void setCounty(DataCountry country);

}
