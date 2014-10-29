/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

/**
 * This enum contains the available choices for a selection of items from a container widget (list, tree,
 * etc.).
 * 
 * @see net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue#setSelection(SelectionChoice,
 *      SelectionOperation)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum SelectionChoice {

  /**
   * This choice will affect all available items. <br>
   * <b>ATTENTION:</b><br>
   * This choice only makes sense for {@link SelectionMode#MULTIPLE_SELECTION}. Further some widgets allow
   * lazy loading of their data. This choice will only affect the items that are already loaded and available.
   * Otherwise a very large or infinite tree would be loaded completely into the client causing undesired
   * performance issues.
   */
  ALL,

  /**
   * This choice will affect the first available item. It will affect nothing if there is no item at all. In
   * case there is only one single item available {@link #FIRST} will mean the same as {@link #LAST}.
   */
  FIRST,

  /**
   * This choice will affect the last available item.
   * 
   * @see #FIRST
   */
  LAST,

}
