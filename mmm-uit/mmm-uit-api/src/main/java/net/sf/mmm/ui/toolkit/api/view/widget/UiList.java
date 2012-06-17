/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import net.sf.mmm.ui.toolkit.api.attribute.AttributeReadMultiSelection;

/**
 * This is the interface for a list UI. A list is used to display items in a
 * list so the user can select one or multiple of these items.<br>
 * The list shows several items in rows. If the number of items is too large to
 * fit, a scrollbar is displayed.
 * 
 * @param <E> is the templated type of the elements that can be selected with
 *        this widget.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiList<E> extends UiListWidget<E>, AttributeReadMultiSelection {

  /** the type of this object */
  String TYPE = "List";

  /**
   * This method gets the indices of all selected items.
   * 
   * @return the indices of the selected items.
   */
  int[] getSelectedIndices();

}
