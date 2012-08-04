/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.UiMode;

/**
 * This interface gives access to {@link #setMode(UiMode) write} the {@link #getMode() mode} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteMode extends AttributeReadMode {

  /**
   * This method sets the {@link UiMode mode} of this object. It will change the object to {@link UiMode#VIEW
   * view} or {@link UiMode#EDIT edit} mode. This is a recursive operation.
   * 
   * @param mode is the new {@link UiMode} to set.
   */
  void setMode(UiMode mode);

}
