/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.complex;

import java.util.List;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a normal {@link UiWidgetAbstractListTable list table widget}. Its
 * {@link #getValue() value} is the actual list that is displayed and may be modified by the end-user. For a
 * general feature list see {@link UiWidgetAbstractDataTable}.<br/>
 * <b>ATTENTION:</b><br/>
 * For small modifications of the list content please use the dedicated methods ({@link #addRow(Object, int)},
 * {@link #removeRow(Object)}, etc.) as they are a lot more efficient than {@link #setValue(Object, boolean)}.
 * 
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetListTable<ROW> extends UiWidgetAbstractListTable<ROW>, UiWidgetNative {

  /**
   * Here the value is the actual list that is displayed and may be modified ({@link #isEditable() edited},
   * rows added or {@link #removeRow(Object) removed}, etc.) by the end-user.
   * 
   * {@inheritDoc}
   */
  @Override
  List<ROW> getValue();

  /**
   * Like {@link #addRow(Object, int)} but adding the new <code>row</code> at the end of the list.
   * 
   * @param row is the {@literal <ROW>} to add.
   */
  void addRow(ROW row);

  /**
   * This method adds the given <code>row</code> at the given <code>index</code> in this list. Will mark the
   * widget as {@link #isModified() modified}.
   * 
   * @param row is the {@literal <ROW>} to add.
   * @param index is the index where to add the given <code>row</code> in {@link #getValue() the list}.
   */
  void addRow(ROW row, int index);

  /**
   * This method removes the given <code>row</code> from this list. Will mark the widget as
   * {@link #isModified() modified}.
   * 
   * @param row is the {@literal <ROW>} to remove.
   * @return <code>true</code> if the given <code>row</code> was removed successfully, <code>false</code>
   *         otherwise (no such {@literal <ROW>} exists).
   */
  boolean removeRow(ROW row);

}
