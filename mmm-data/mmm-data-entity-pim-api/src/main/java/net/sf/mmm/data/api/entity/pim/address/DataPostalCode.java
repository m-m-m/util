/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.data.api.entity.pim.address;

import net.sf.mmm.data.api.entity.DataEntity;
import net.sf.mmm.data.api.link.MutableLinkList;
import net.sf.mmm.util.datatype.api.address.PostalCode;

/**
 * This is the interface for a mutable {@link DataPostalCodeView}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface DataPostalCode extends DataPostalCodeView, DataEntity {

  /**
   * {@inheritDoc}
   */
  MutableLinkList<DataCity> getCities();

  /**
   * This method sets the {@link #getPostalCode() postal code}.
   * 
   * @param postalCode is the {@link PostalCode} to set.
   */
  void setPostalCode(PostalCode postalCode);

}
