/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.complex;

import net.sf.mmm.client.ui.api.widget.UiWidgetListBase;

/**
 * This is the abstract interface for a {@link UiWidgetAbstractDataTable data table widget} that represents a
 * <em>list table</em>.<br/>
 * <b>Note:</b><br/>
 * There are the following variants of this abstract list table widget:
 * <ul>
 * <li>{@link UiWidgetListTable}</li>
 * <li>{@link UiWidgetOptionListTable}</li>
 * </ul>
 * 
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAbstractListTable<ROW> extends UiWidgetAbstractDataTable<ROW>, UiWidgetListBase<ROW> {

  /**
   * Gets the index of the given <code>row</code>. <br/>
   * <b>ATTENTION:</b><br/>
   * The index of a row is not stable and may change at any time due to sorting or other features. You should
   * only retrieve an index to immediately use it e.g. for {@link #addRow(Object, int)} to insert a row before
   * or after a given row such as the {@link #getSelectedValue() selected row}. Please avoid storing such
   * index and use it later as this can cause fatal bugs.
   * 
   * @see java.util.List#indexOf(Object)
   * 
   * @param row is the {@literal <ROW>} to locate.
   * @return the index of the given row or <code>-1</code> if not found.
   */
  int getRowIndex(ROW row);

  /**
   * Adds the given row at the end of this list table.
   * 
   * @see #addRow(Object, int)
   * 
   * @param row is the {@literal <ROW>} to add.
   */
  void addRow(ROW row);

  /**
   * Adds the given row to this list table. <br/>
   * <b>ATTENTION:</b><br/>
   * Be careful when dealing with indices. The index of a row can change at any time due to sorting or other
   * features. You may use this method in combination of recent calls to {@link #getRowIndex(Object)}.
   * 
   * @param row is the {@literal <ROW>} to add.
   * @param index is the index where to add the given <code>row</code>.
   */
  void addRow(ROW row, int index);

  /**
   * This method replaces a {@literal <ROW>} in this list table.
   * 
   * @param oldRow is the old {@literal <ROW>} to replace.
   * @param newRow is the new {@literal <ROW>} to insert in place of the old one.
   * @return <code>true</code> if <code>oldRow</code> was found and replaced, <code>false</code> otherwise (if
   *         this list table does not contain <code>oldRow</code> and nothing was changed).
   */
  boolean replaceRow(ROW oldRow, ROW newRow);

  /**
   * This method removes the given <code>row</code>.
   * 
   * @param row is the {@literal <ROW>} to remove.
   * @return <code>true</code> if the <code>row</code> was found and removed, <code>false</code> otherwise (if
   *         this list table does not contain <code>oldRow</code> and nothing was changed).
   */
  boolean removeRow(ROW row);

}
