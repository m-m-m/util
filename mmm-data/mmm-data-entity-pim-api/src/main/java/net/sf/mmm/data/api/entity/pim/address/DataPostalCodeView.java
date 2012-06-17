/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.address;

import net.sf.mmm.data.api.entity.DataEntityView;
import net.sf.mmm.data.api.link.LinkList;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.datatype.api.address.PostalCode;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the view interface for a
 * {@link net.sf.mmm.data.api.entity.DataEntityView entity} that represents a
 * {@link PostalCode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataPostalCodeView.CLASS_ID, title = DataPostalCodeView.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataPostalCodeView extends DataEntityView {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of
   * the {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_POSTALCODE;

  /**
   * The {@link net.sf.mmm.data.api.DataObjectView#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataPostalCode";

  /**
   * This method gets the {@link DataCityView cities} addressed by this postal
   * code. All cities shall be {@link DataCityView#getCountry() located in the
   * same country}.
   * 
   * @return the list of {@link DataCityView cities}.
   */
  LinkList<? extends DataCityView> getCities();

  /**
   * This method gets the actual {@link PostalCode} represented by this entity.
   * The result will be the same as {@link #getTitle()}.
   * 
   * @return the {@link PostalCode} or <code>null</code>.
   */
  PostalCode getPostalCode();

}
