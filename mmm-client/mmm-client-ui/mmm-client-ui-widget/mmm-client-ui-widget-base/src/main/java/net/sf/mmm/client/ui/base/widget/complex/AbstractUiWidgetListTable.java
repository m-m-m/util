/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetListTable;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterListTable;
import net.sf.mmm.util.validation.api.ValidationState;

/**
 * This is the base implementation of {@link UiWidgetListTable}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetListTable<ADAPTER extends UiWidgetAdapterListTable<ROW>, ROW> extends
    AbstractUiWidgetAbstractListTable<ADAPTER, ROW> implements UiWidgetListTable<ROW> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public AbstractUiWidgetListTable(UiContext context, ADAPTER widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(List<ROW> newValue, boolean forUser) {

    super.doSetValue(newValue, forUser);
    boolean hasWidgetAdapter = hasWidgetAdapter();
    List<TableRowContainer<ROW>> rows = getRowsInternal();
    int i = 0;
    int size = rows.size();
    TableRowContainer<ROW> container;
    for (ROW newRow : newValue) {
      if (i < size) {
        container = rows.get(i);
        container.setValue(newRow);
      } else {
        container = createRowContainer(newRow);
        rows.add(container);
        if (hasWidgetAdapter) {
          getWidgetAdapter().addRow(container, i);
        }
      }
      i++;
    }
    for (int j = rows.size() - 1; j >= i; j--) {
      // TODO potentially pool obsolete rows for later reuse...
      TableRowContainer<ROW> removedRow = rows.remove(j);
      if (hasWidgetAdapter) {
        getWidgetAdapter().removeRow(removedRow);
      }
    }
    getSelectedValuesInternal().clear();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected List<ROW> doGetValue(List<ROW> template, ValidationState state) throws RuntimeException {

    List<TableRowContainer<ROW>> rows = getRowsInternal();
    List<ROW> result = new ArrayList<ROW>(rows.size());
    for (TableRowContainer<ROW> container : rows) {
      result.add(container.getValue());
    }
    return result;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    int i = 0;
    for (TableRowContainer<ROW> row : getRowsInternal()) {
      getWidgetAdapter().addRow(row, i++);
    }

  }

}
