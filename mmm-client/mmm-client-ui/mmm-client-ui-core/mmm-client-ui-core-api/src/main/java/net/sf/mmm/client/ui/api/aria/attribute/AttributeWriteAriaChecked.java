/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.aria.attribute;

import net.sf.mmm.client.ui.api.aria.datatype.AriaTristate;

/**
 * This interface gives read and write access to the {@link #getChecked() checked} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteAriaChecked extends AttributeReadAriaChecked {

  /**
   * This method sets the {@link #getChecked() checked} state of this object.
   * 
   * @param checked is the new value of {@link #getChecked()}.
   */
  void setChecked(AriaTristate checked);

}
