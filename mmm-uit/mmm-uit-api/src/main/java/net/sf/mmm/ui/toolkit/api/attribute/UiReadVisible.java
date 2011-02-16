/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

/**
 * This interface gives read access to the {@link #isVisible() visible-flag} of
 * an {@link net.sf.mmm.ui.toolkit.api.UiObject object}. Such object can be
 * displayed to or hidden from the UI.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiReadVisible {

  /**
   * This method checks if this object is visible.
   * 
   * @return <code>true</code> if this object is visible, <code>false</code>
   *         otherwise.
   */
  boolean isVisible();

}
