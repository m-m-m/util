/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex.adapter;

import java.util.List;

import net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn;
import net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetTableColumn;
import net.sf.mmm.util.lang.api.SortOrder;

/**
 * This is the interface for a {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} adapting
 * {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractDataTable}.
 * 
 * @param <ROW> is the generic type of a row in the list.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetAdapterAbstractDataTable<ROW> extends UiWidgetAdapterAbstractDataSet<ROW> {

  /**
   * @param column is the {@link UiWidgetTableColumn} for which to create the widget adapter.
   * @return a new {@link UiWidgetAdapterTableColumn} for the given {@link UiWidgetTableColumn}.
   */
  UiWidgetAdapterTableColumn createTableColumnAdapter(UiWidgetTableColumn<?, ?> column);

  /**
   * @see net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractDataTable#setColumns(UiWidgetTableColumn...)
   * 
   * @param columns are the {@link UiWidgetTableColumn columns} to set.
   */
  void setColumns(List<AbstractUiWidgetTableColumn<?, ROW, ?>> columns);

  /**
   * @see AbstractUiWidgetTableColumn#sort(SortOrder)
   * 
   * @param column is the column to sort by.
   * @param sortOrder is the {@link SortOrder}.
   */
  void sort(AbstractUiWidgetTableColumn<?, ROW, ?> column, SortOrder sortOrder);

  void dragColumn(AbstractUiWidgetTableColumn<?, ROW, ?> dragColumn, AbstractUiWidgetTableColumn<?, ROW, ?> dropColumn);

}
