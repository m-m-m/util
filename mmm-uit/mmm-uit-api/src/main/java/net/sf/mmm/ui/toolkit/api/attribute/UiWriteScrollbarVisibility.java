/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.ScrollbarVisibility;

/**
 * This interface gives read and write access to the
 * {@link #setScrollbarVisibility(ScrollbarVisibility, ScrollbarVisibility)
 * scrollbar visibility} of an {@link net.sf.mmm.ui.toolkit.api.UiObject}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWriteScrollbarVisibility extends UiReadScrollbarVisibility {

  /**
   * This method sets the {@link ScrollbarVisibility} for the horizontal and
   * vertical scrollbars.
   * 
   * @see #getHorizontalScrollbarVisibility()
   * @see #getVerticalScrollbarVisibility()
   * 
   * @param horizontalVisibility is the {@link ScrollbarVisibility} for the
   *        horizontal scrollbar.
   * @param verticalVisibility is the {@link ScrollbarVisibility} for the
   *        horizontal scrollbar.
   */
  void setScrollbarVisibility(ScrollbarVisibility horizontalVisibility,
      ScrollbarVisibility verticalVisibility);

}
