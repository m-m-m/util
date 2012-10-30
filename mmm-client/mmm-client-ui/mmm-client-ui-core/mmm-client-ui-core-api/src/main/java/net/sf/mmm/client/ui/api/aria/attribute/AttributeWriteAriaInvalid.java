/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaInvalid;

/**
 * This interface gives read and write access to the {@link #getInvalid() invalid} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaInvalid extends AttributeReadAriaInvalid {

  /**
   * This method sets the {@link #getInvalid() invalid} state of this object.
   * 
   * @param invalid is the new value of {@link #getInvalid()}.
   */
  void setInvalid(AriaInvalid invalid);

}
