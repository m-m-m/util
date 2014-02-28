/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.common;

/**
 * This enum contains the available operations for a selection of items from a container widget (list, tree,
 * etc.).
 * 
 * @see net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue#setSelection(SelectionChoice,
 *      SelectionOperation)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public enum SelectionOperation {

  /** This operation set the selection so only the indicated items are selected. */
  SET,

  /**
   * This operation will add the indicated items to the current selection. <br/>
   * <b>ATTENTION:</b><br/>
   * This operation only makes sense in {@link SelectionMode#MULTIPLE_SELECTION}. Use {@link #SET} in case of
   * {@link SelectionMode#SINGLE_SELECTION}.
   */
  ADD,

  /** This operation will remove the indicated items from the current selection. */
  REMOVE,

  /** This operation will toggle (invert) the selection of the indicated items. */
  TOGGLE,

}
