/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.complex;

import java.util.List;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetAbstractListTable;
import net.sf.mmm.client.ui.base.widget.complex.adapter.UiWidgetAdapterAbstractListTable;

/**
 * This is the abstract base implementation of {@link UiWidgetAbstractListTable}.
 * 
 * @param <ADAPTER> is the generic type of {@link #getWidgetAdapter()}.
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetAbstractListTable<ADAPTER extends UiWidgetAdapterAbstractListTable<ROW>, ROW>
    extends AbstractUiWidgetAbstractDataTable<ADAPTER, ROW> implements UiWidgetAbstractListTable<ROW> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public AbstractUiWidgetAbstractListTable(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @SuppressWarnings({ "rawtypes", "unchecked" })
  @Override
  protected Class<List<ROW>> getValueClass() {

    return (Class) List.class;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(ADAPTER adapter) {

    super.initializeWidgetAdapter(adapter);
    List<ROW> value = getRecentValue();
    if (value != null) {
      adapter.setValue(value);
    }
  }

}
