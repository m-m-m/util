/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.address;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.link.MutableLinkList;
import net.sf.mmm.data.api.reflection.DataClassAnnotation;
import net.sf.mmm.data.api.reflection.DataClassIds;
import net.sf.mmm.util.datatype.api.address.PostalCode;
import net.sf.mmm.util.lang.api.BooleanEnum;

/**
 * This is the interface for a {@link net.sf.mmm.data.api.entity.DataEntity} that represents a
 * {@link PostalCode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@DataClassAnnotation(id = DataPostalCode.CLASS_ID, title = DataPostalCode.CLASS_TITLE, isFinal = BooleanEnum.TRUE)
public interface DataPostalCode extends DataEntity {

  /**
   * The {@link net.sf.mmm.data.api.datatype.DataId#getClassId() class-ID} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  long CLASS_ID = DataClassIds.ID_POSTALCODE;

  /**
   * The {@link net.sf.mmm.data.api.DataObject#getTitle() title} of the
   * {@link net.sf.mmm.data.api.reflection.DataClass} reflecting this type.
   */
  String CLASS_TITLE = "DataPostalCode";

  /**
   * This method gets the {@link DataCity cities} addressed by this postal code. All cities shall be
   * {@link DataCity#getCountry() located in the same country}.
   * 
   * @return the list of {@link DataCity cities}.
   */
  MutableLinkList<DataCity> getCities();

  /**
   * This method gets the actual {@link PostalCode} represented by this entity. The result will be the same as
   * {@link #getTitle()}.
   * 
   * @return the {@link PostalCode} or <code>null</code>.
   */
  PostalCode getPostalCode();

  /**
   * This method sets the {@link #getPostalCode() postal code}.
   * 
   * @param postalCode is the {@link PostalCode} to set.
   */
  void setPostalCode(PostalCode postalCode);

}
