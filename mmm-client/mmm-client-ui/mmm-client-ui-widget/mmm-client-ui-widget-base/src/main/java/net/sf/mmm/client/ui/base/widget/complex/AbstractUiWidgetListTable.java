/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetListTable;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterListTable;

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
   */
  public AbstractUiWidgetListTable(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRow(ROW row) {

    getValueInternal().add(row);
    if (hasWidgetAdapter()) {
      // getWidgetAdapter().addRow(row);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addRow(ROW row, int index) {

    getValueInternal().add(index, row);
    if (hasWidgetAdapter()) {
      // getWidgetAdapter().addRow(row, index);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeRow(ROW row) {

    boolean removed = getValueInternal().remove(row);
    if (hasWidgetAdapter()) {
      // getWidgetAdapter().removeRow(row);
    }
    return removed;
  }

}
