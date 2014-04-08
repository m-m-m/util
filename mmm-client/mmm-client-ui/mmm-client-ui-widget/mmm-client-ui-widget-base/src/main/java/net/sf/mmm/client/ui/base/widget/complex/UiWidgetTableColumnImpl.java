/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterTableColumn;
import net.sf.mmm.util.pojo.path.api.TypedProperty;

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
   * @param typedProperty is the {@link #getTypedProperty() typed property} of the column to create. May be
   *        <code>null</code>.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetTableColumnImpl(UiContext context, AbstractUiWidgetAbstractDataTable<?, ?, ROW, ?> listTable,
      TypedProperty<CELL> typedProperty, UiWidgetAdapterTableColumn widgetAdapter) {

    super(context, listTable, typedProperty, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTableColumn createWidgetAdapter() {

    return getDataTable().createTableColumnAdapter(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setReorderable(boolean reorderable) {

    super.setReorderable(reorderable);
    if (reorderable) {
      addStyle(CssStyles.DRAGGABLE);
    } else {
      removeStyle(CssStyles.DRAGGABLE);
    }
  }

}
