/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.common;


/**
 * This enum contains the available values for the enabled state of a
 * {@link net.sf.mmm.ui.toolkit.api.view.UiNode}. The enabled state is not
 * realized as boolean to distinguish between {@link #DISABLED} and
 * {@link #BLOCKED}.
 * 
 * @see net.sf.mmm.ui.toolkit.api.attribute.UiReadEnabled
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum EnabledState {

  /**
   * The object is {@link #isEnabled() enabled} allowing user interaction.
   */
  ENABLED,

  /**
   * The object is
   * {@link net.sf.mmm.ui.toolkit.api.attribute.UiWriteEnabled#setEnabled(boolean)
   * programatically disabled}. It will not become {@link #isEnabled() enabled}
   * until the program sets it {@link #ENABLED} again. In this case it may still
   * switch to {@link #BLOCKED} instead.
   */
  DISABLED,

  /**
   * The object itself is
   * {@link net.sf.mmm.ui.toolkit.api.attribute.UiWriteEnabled#setEnabled(boolean)
   * set enabled} but its
   * {@link net.sf.mmm.ui.toolkit.api.view.UiNode#getParent() parent} is not
   * <code>null</code> and {@link #DISABLED}.
   */
  BLOCKED;

  /**
   * This method determines if an object with this {@link EnabledState} is
   * actually enabled in the UI.
   * 
   * @return <code>true</code> if enabled for user interaction,
   *         <code>false</code> otherwise.
   */
  public boolean isEnabled() {

    return (this == ENABLED);
  }

}
