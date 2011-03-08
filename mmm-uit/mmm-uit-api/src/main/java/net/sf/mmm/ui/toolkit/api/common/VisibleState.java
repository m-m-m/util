/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.common;

/**
 * This enum contains the available values for the visibility of a
 * {@link net.sf.mmm.ui.toolkit.api.view.UiNode}. The visibility is not realized
 * as boolean to distinguish between {@link #HIDDEN} and {@link #BLOCKED}.
 * 
 * @see net.sf.mmm.ui.toolkit.api.attribute.UiReadVisible
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum VisibleState {

  /**
   * The object is visible in the UI. The use may still not see it if the window
   * reaches out of the screen.
   */
  VISIBLE,

  /**
   * The object is programatically hidden. It will not become visible until the
   * program sets it {@link #VISIBLE} again. In this case it may still switch to
   * {@link #BLOCKED} state instead.
   */
  HIDDEN,

  /**
   * The object is set {@link #VISIBLE} but its
   * {@link net.sf.mmm.ui.toolkit.api.view.UiNode#getParent() parent} is
   * <code>null</code> or not {@link #VISIBLE}.
   */
  BLOCKED;

  /**
   * This method determines if an object with this {@link VisibleState} status
   * is actually displayed in the UI.
   * 
   * @return <code>true</code> if visible to the user, <code>false</code>
   *         otherwise.
   */
  public boolean isVisible() {

    return (this == VISIBLE);
  }

}
