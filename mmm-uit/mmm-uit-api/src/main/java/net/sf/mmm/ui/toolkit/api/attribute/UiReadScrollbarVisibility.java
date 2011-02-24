/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.ScrollbarVisibility;

/**
 * This interface gives read access to the {@link ScrollbarVisibility} of an
 * {@link net.sf.mmm.ui.toolkit.api.UiObject}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiReadScrollbarVisibility {

  /**
   * This method gets the {@link ScrollbarVisibility} for the horizontal
   * scrollbar.
   * 
   * @return the horizontal {@link ScrollbarVisibility}.
   */
  ScrollbarVisibility getHorizontalScrollbarVisibility();

  /**
   * This method gets the {@link ScrollbarVisibility} for the vertical
   * scrollbar.
   * 
   * @return the vertical {@link ScrollbarVisibility}.
   */
  ScrollbarVisibility getVerticalScrollbarVisibility();

}
