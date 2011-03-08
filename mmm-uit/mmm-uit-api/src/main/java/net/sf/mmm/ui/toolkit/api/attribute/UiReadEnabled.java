/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.EnabledState;

/**
 * This interface gives read access to the {@link #isEnabled() enabled-flag} of
 * an {@link net.sf.mmm.ui.toolkit.api.UiObject}. Such object allows interactive
 * behavior that can be enabled or disabled. If an object is disabled, the user
 * can NOT interact with the object. This is typically visualized by greying out
 * the object.
 * 
 * @see UiReadEditable
 * @see UiWriteEnabled
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiReadEnabled {

  /**
   * This method gets the {@link EnabledState} of this object.
   * 
   * @see #isEnabled()
   * 
   * @return the {@link EnabledState}.
   */
  EnabledState getEnabledState();

  /**
   * This method determines if this object is enabled. If it is NOT enabled, the
   * user can NOT interact with the object. This method is a shortcut for:<br/>
   * <code>{@link #getEnabledState()}.isEnabled()</code>
   * 
   * @see #getEnabledState()
   * 
   * @return <code>true</code> if this object is enabled, <code>false</code> if
   *         this object is disabled.
   */
  boolean isEnabled();

}
