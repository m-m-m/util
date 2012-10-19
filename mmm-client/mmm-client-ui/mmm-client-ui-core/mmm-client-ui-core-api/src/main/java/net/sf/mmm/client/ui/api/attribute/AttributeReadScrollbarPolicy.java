/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.attribute;

import net.sf.mmm.client.ui.api.common.ScrollbarPolicy;

/**
 * This interface gives read access to the {@link ScrollbarPolicy} of an object.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface AttributeReadScrollbarPolicy {

  /**
   * This method gets the {@link ScrollbarPolicy} for the horizontal scrollbar.
   * 
   * @return the horizontal {@link ScrollbarPolicy}.
   */
  ScrollbarPolicy getHorizontalScrollbarPolicy();

  /**
   * This method gets the {@link ScrollbarPolicy} for the vertical scrollbar.
   * 
   * @return the vertical {@link ScrollbarPolicy}.
   */
  ScrollbarPolicy getVerticalScrollbarPolicy();

}
