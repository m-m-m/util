/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.address;

import net.sf.mmm.data.api.entity.DataEntityView;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.datatype.api.phone.AreaCode;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the view interface for a
 * {@link net.sf.mmm.data.api.entity.DataEntityView entity} that represents a
 * city.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataCityView.CLASS_ID, title = DataCityView.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataCityView extends DataEntityView {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_CITY;

  /**
   * The {@link net.sf.mmm.data.api.DataObjectView#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataCity";

  /**
   * This method gets the {@link DataCountryView country} this city is located
   * in. This field has to be set for a valid city.
   * 
   * @return the {@link DataCountryView country} or <code>null</code> if
   *         undefined.
   */
  DataCountryView getCountry();

  /**
   * This method gets the {@link AreaCode} for this city.
   * 
   * @return the {@link AreaCode} or <code>null</code> if undefined (e.g. some
   *         countries do not have the concept of {@link AreaCode}s).
   */
  AreaCode getAreaCode();

}
