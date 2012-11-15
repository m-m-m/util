/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaSortOrder;

/**
 * This interface gives read and write access to the {@link #getSort() sort} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaSort extends AttributeReadAriaSort {

  /**
   * This method sets the {@link #getSort() sort} attribute of this object.
   * 
   * @param sort is the new value of {@link #getSort()}. May be <code>null</code> to unset.
   */
  void setSort(AriaSortOrder sort);

}
