/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.UiMode;

/**
 * This interface gives access to {@link #setMode(UiMode) write} the {@link #getMode() mode} of an object.
 * 
 * @author hohwille
 * @since 1.0.0
 */
public interface AttributeWriteMode extends AttributeReadMode {

  /**
   * This method sets the {@link UiMode mode} of this object.
   * 
   * @param mode is the new {@link UiMode} to set.
   */
  void setMode(UiMode mode);

}
