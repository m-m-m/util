/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.common;

import net.sf.mmm.util.nls.api.IllegalCaseException;

/**
 * This enum contains the available values for the visibility of an UI object. The visibility is not realized
 * as boolean to distinguish between {@link #HIDDEN} and {@link #BLOCKED}.
 * 
 * @see net.sf.mmm.ui.toolkit.api.attribute.AttributeReadVisibleState
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum VisibleState {

  /**
   * The object is visible in the UI. The use may still not see it if the window reaches out of the screen.
   */
  VISIBLE,

  /**
   * The object is programatically hidden. It will not become visible until the program sets it
   * {@link #VISIBLE} again.
   * 
   * @see #BLOCKED_AND_HIDDEN
   */
  HIDDEN,

  /**
   * The object is set {@link #VISIBLE} but its parent is <code>null</code> or not {@link #VISIBLE}.
   */
  BLOCKED,

  /**
   * The object is set {@link #BLOCKED} and {@link #HIDDEN}.
   */
  BLOCKED_AND_HIDDEN;

  /**
   * This method determines if an object with this {@link VisibleState} status is actually displayed in the
   * UI.
   * 
   * @return <code>true</code> if visible to the user, <code>false</code> otherwise.
   */
  public boolean isVisible() {

    return (this == VISIBLE);
  }

  /**
   * This method returns the resulting {@link VisibleState} if the visibility is set to <code>visible</code>.
   * 
   * @param visible <code>true</code> if the element shall be visible, <code>false</code> if it should be
   *        invisible.
   * @return the resulting {@link VisibleState}.
   */
  public VisibleState setVisible(boolean visible) {

    switch (this) {
      case VISIBLE:
        if (!visible) {
          return HIDDEN;
        }
        break;
      case HIDDEN:
        if (visible) {
          return VISIBLE;
        }
        break;
      case BLOCKED:
        if (!visible) {
          return BLOCKED_AND_HIDDEN;
        }
        break;
      case BLOCKED_AND_HIDDEN:
        if (visible) {
          return BLOCKED;
        }
        break;
      default :
        throw new IllegalCaseException(VisibleState.class, this);
    }
    return this;
  }

  /**
   * This method returns the resulting {@link VisibleState} if the element is {@link #BLOCKED blocked} or
   * unblocked.
   * 
   * @param blocked <code>true</code> if the element shall be {@link #BLOCKED blocked}, <code>false</code>
   *        otherwise (if it shall be unblocked).
   * @return the resulting {@link VisibleState}.
   */
  public VisibleState setBlocked(boolean blocked) {

    switch (this) {
      case VISIBLE:
        if (blocked) {
          return BLOCKED;
        }
        break;
      case HIDDEN:
        if (blocked) {
          return BLOCKED_AND_HIDDEN;
        }
        break;
      case BLOCKED:
        if (!blocked) {
          return VISIBLE;
        }
        break;
      case BLOCKED_AND_HIDDEN:
        if (!blocked) {
          return HIDDEN;
        }
        break;
      default :
        throw new IllegalCaseException(VisibleState.class, this);
    }
    return this;
  }

}
