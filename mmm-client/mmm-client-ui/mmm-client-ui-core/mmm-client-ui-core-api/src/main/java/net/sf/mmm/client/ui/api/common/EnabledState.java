/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This enum contains the available values for the enabled state of an object. The enabled state is not
 * realized as boolean to distinguish between {@link #DISABLED} and {@link #BLOCKED}.
 * 
 * @see net.sf.mmm.client.ui.api.attribute.AttributeReadEnabledState
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
   * The object is {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteEnabled#setEnabled(boolean)
   * programatically disabled}. It will not become {@link #isEnabled() enabled} until the program sets it
   * {@link #ENABLED} again. In this case it may still switch to {@link #BLOCKED} instead.
   */
  DISABLED,

  /**
   * The object itself is {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteEnabled#setEnabled(boolean)
   * set enabled} but its parent is not <code>null</code> and {@link #DISABLED}.
   */
  BLOCKED,

  /**
   * The object itself is {@link #BLOCKED} and {@link #DISABLED}.
   */
  BLOCKED_AND_DISABLED;

  /**
   * This method determines if an object with this {@link EnabledState} is actually enabled in the UI.
   * 
   * @return <code>true</code> if enabled for user interaction, <code>false</code> otherwise.
   */
  public boolean isEnabled() {

    return (this == ENABLED);
  }

  /**
   * This method returns the resulting {@link EnabledState} if the activity is set to <code>enabled</code>.
   * 
   * @param enabled <code>true</code> if the element shall be enabled, <code>false</code> if it should be
   *        disabled.
   * @return the resulting {@link EnabledState}.
   */
  public EnabledState setEnabled(boolean enabled) {

    switch (this) {
      case ENABLED:
        if (!enabled) {
          return DISABLED;
        }
        break;
      case DISABLED:
        if (enabled) {
          return ENABLED;
        }
        break;
      case BLOCKED:
        if (!enabled) {
          return BLOCKED_AND_DISABLED;
        }
        break;
      case BLOCKED_AND_DISABLED:
        if (enabled) {
          return BLOCKED;
        }
        break;
      default :
        throw new IllegalCaseException(EnabledState.class, this);
    }
    return this;
  }

  /**
   * This method returns the resulting {@link EnabledState} if the element is {@link #BLOCKED blocked} or
   * unblocked.
   * 
   * @param blocked <code>true</code> if the element shall be {@link #BLOCKED blocked}, <code>false</code>
   *        otherwise (if it shall be unblocked).
   * @return the resulting {@link EnabledState}.
   */
  public EnabledState setBlocked(boolean blocked) {

    switch (this) {
      case ENABLED:
        if (blocked) {
          return BLOCKED;
        }
        break;
      case DISABLED:
        if (blocked) {
          return BLOCKED_AND_DISABLED;
        }
        break;
      case BLOCKED:
        if (!blocked) {
          return ENABLED;
        }
        break;
      case BLOCKED_AND_DISABLED:
        if (!blocked) {
          return DISABLED;
        }
        break;
      default :
        throw new IllegalCaseException(EnabledState.class, this);
    }
    return this;
  }

}
