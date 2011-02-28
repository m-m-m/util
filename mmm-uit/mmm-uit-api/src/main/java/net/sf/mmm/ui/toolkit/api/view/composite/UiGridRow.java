/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.composite;

import net.sf.mmm.ui.toolkit.api.view.UiElement;

/**
 * This is the interface of a {@link UiComposite} that represents a row of a
 * {@link UiGrid}.
 * 
 * @param <E> is the generic type of the {@link #getChild(int) child-elements}.
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiGridRow<E extends UiElement> extends UiExtendableComposite<E> {

  /**
   * This method sets the given <code>child</code> as cell in this row at the
   * specified <code>column</code>. A {@link UiGrid} is created
   * 
   * @param child is the {@link UiElement} to add.
   * @param column is the index of the column where to add the given
   *        <code>child</code>.
   */
  void setChild(UiElement child, int column);

  /**
   * This method sets the column-span for the cell identified by the given
   * <code>column</code> number in this row. Like in HTML a column-span defines
   * the number of cells to join horizontal along the columns including the
   * starting cell itself.<br/>
   * The default value is 1. E.g. a value of 2 combines the specified cell with
   * the next cell to the right.
   * 
   * @param column is the number of the column where to start within this
   *        {@link UiGridRow}.
   * @param spanCount is the number of cells to combine horizontally. It has to
   *        be greater than <code>0</code>.
   */
  void setColumnSpan(int column, int spanCount);

  /**
   * This method sets the row-span for the cell identified by the given
   * <code>column</code> number in this row. Like in HTML a row-span defines the
   * number of cells to join vertically along the rows including the starting
   * cell itself.<br/>
   * The default value is 1. E.g. a value of 2 combines the specified cell with
   * the next cell at the bottom.
   * 
   * @param column is the number of the column where to start within this
   *        {@link UiGridRow}.
   * @param rowCount is the number of cells to combine vertically. It has to be
   *        greater than <code>0</code>.
   */
  void setRowSpan(int column, int rowCount);

}
