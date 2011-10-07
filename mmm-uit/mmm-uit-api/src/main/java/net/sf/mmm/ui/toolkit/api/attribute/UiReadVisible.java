/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.VisibleState;

/**
 * This interface gives read access to the {@link #getVisibleState() visibility}
 * of an {@link net.sf.mmm.ui.toolkit.api.UiObject object}. Such object can be
 * displayed to or hidden from the UI.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiReadVisible {

  /**
   * This method determines if this object is displayed to the user. It is a
   * shortcut for:<br/>
   * <code>{@link #getVisibleState()}.isVisible()</code>
   * 
   * @see #getVisibleState()
   * 
   * @return the value of {@link VisibleState#isVisible()}.
   */
  boolean isVisible();

}
