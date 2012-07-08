/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.attribute;

import net.sf.mmm.ui.toolkit.api.common.ScrollbarPolicy;

/**
 * This interface gives read and write access to the
 * {@link #setScrollbarPolicy(ScrollbarPolicy, ScrollbarPolicy) scrollbar policy} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeWriteScrollbarPolicy extends AttributeReadScrollbarPolicy {

  /**
   * This method sets the {@link ScrollbarPolicy} for the horizontal and vertical scrollbars.
   * 
   * @see #getHorizontalScrollbarPolicy()
   * @see #getVerticalScrollbarPolicy()
   * 
   * @param horizontalPolicy is the {@link ScrollbarPolicy} for the horizontal scrollbar.
   * @param verticalPolicy is the {@link ScrollbarPolicy} for the horizontal scrollbar.
   */
  void setScrollbarPolicy(ScrollbarPolicy horizontalPolicy, ScrollbarPolicy verticalPolicy);

}
