/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex.adapter;

import java.util.List;

import net.sf.mmm.client.ui.base.widget.complex.TableRowContainer;
import net.sf.mmm.util.lang.api.attribute.AttributeWriteValue;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractListTable}.
 * 
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterAbstractListTable<ROW> extends UiWidgetAdapterAbstractDataTable<ROW>,
    AttributeWriteValue<List<ROW>> {

  /**
   * @see net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractListTable#addRow(Object, int)
   * 
   * @param row is the {@literal <ROW>} to add.
   * @param index is the index where to add the new row.
   */
  void addRow(TableRowContainer<ROW> row, int index);

  /**
   * @see net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractListTable#removeRow(Object)
   * 
   * @param row is the {@literal <ROW>} to remove.
   */
  void removeRow(TableRowContainer<ROW> row);

}
