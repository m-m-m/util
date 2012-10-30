/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaIdList;

/**
 * This interface gives read and write access to the {@link #getLabelledBy() labelledBy} attribute of an
 * object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaLabelledBy extends AttributeReadAriaLabelledBy {

  /**
   * This method sets the {@link #getLabelledBy() labelledBy} attribute of this object.
   * 
   * @param labelledBy is value of {@link #getLabelledBy()}.
   */
  void setLabelledBy(AriaIdList labelledBy);

}
