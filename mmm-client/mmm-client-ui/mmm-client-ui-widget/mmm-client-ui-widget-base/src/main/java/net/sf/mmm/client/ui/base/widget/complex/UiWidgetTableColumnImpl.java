/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetTableColumn}.
 * 
 * @param <ROW> is the generic type of the element representing a row of the grid.
 * @param <CELL> is the generic type of the values located in the cells of this column.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTableColumnImpl<ROW, CELL> extends
    AbstractUiWidgetTableColumn<UiWidgetAdapterTableColumn, ROW, CELL> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param listTable is the {@link AbstractUiWidgetAbstractListTable list table} this column is connected to.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetTableColumnImpl(UiContext context, AbstractUiWidgetAbstractDataTable<?, ROW> listTable,
      UiWidgetAdapterTableColumn widgetAdapter) {

    super(context, listTable, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTableColumn createWidgetAdapter() {

    return getListTable().createTableColumnAdapter(this);
  }

}
