/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.UiMode;

/**
 * This interface gives read access to the {@link #getModeFixed() mode} attribute of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadModeFixed {

  /**
   * This method gets the fixed {@link UiMode} of this object. While a fixed {@link UiMode} is
   * {@link AttributeWriteModeFixed#setModeFixed(UiMode) set} (NOT null)
   * {@link AttributeWriteMode#setMode(UiMode) changing the mode} will have no effect.
   * 
   * @return the fixed {@link UiMode} or <code>null</code> if the {@link UiMode} may be
   *         {@link AttributeWriteMode#setMode(UiMode) changed}.
   */
  UiMode getModeFixed();

}
