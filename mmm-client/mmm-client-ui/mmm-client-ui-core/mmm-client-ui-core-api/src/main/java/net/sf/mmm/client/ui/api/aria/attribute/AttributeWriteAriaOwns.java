/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaIdList;

/**
 * This interface gives read and write access to the {@link #getOwns() owns} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaOwns extends AttributeReadAriaOwns {

  /**
   * This method sets the {@link #getOwns() owns} attribute of this object.
   * 
   * @param owns is value of {@link #getOwns()}.
   */
  void setOwns(AriaIdList owns);

}
