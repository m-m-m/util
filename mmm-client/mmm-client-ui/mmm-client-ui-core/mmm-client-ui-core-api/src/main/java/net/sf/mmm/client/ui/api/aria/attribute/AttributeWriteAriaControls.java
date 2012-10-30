/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaIdList;

/**
 * This interface gives read and write access to the {@link #getControls() controls} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaControls extends AttributeReadAriaControls {

  /**
   * This method sets the {@link #getControls() controls} attribute of this object.
   * 
   * @param controls is value of {@link #getControls()}. May be <code>null</code> to unset.
   */
  void setControls(AriaIdList controls);

}
