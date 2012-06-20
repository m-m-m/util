/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.common.GridCellInfo;
import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the interface of a {@link UiComposite} that represents a row of a {@link UiGridPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 * @since 1.0.0
 */
public interface UiGridRow<CHILD extends UiElement> extends UiExtendableComposite<CHILD> {

  /** @see #getType() */
  String TYPE = "GridRow";

  /**
   * This method sets the given <code>child</code> as cell in this row at the specified <code>column</code>. A
   * {@link UiGridPanel} is created
   * 
   * @param child is the {@link UiElement} to add.
   * @param column is the index of the column where to add the given <code>child</code>.
   */
  void setChild(CHILD child, int column);

  /**
   * This method gets the {@link GridCellInfo} for the {@link #getChild(int) cell} identified by the given
   * <code>column</code> in this {@link UiGridRow} .<br/>
   * <b>NOTE:</b><br>
   * The {@link GridCellInfo} are in sync with the {@link #getChild(int) children} of this {@link UiGridRow}.
   * Operations like {@link #removeChild(int)} or {@link #insertChild(UiElement, int)} will update the
   * {@link GridCellInfo}s accordingly.
   * 
   * @param column is the index of the column of the cell in this row. Needs to be in the range from
   *        <code>0</code> to <code>{@link #getChildCount()} - 1</code>.
   * @return the {@link GridCellInfo} for the identified cell.
   */
  GridCellInfo getCellInfo(int column);

}
