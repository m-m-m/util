/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.complex;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.complex.UiWidgetListTable;
import net.sf.mmm.client.ui.base.widget.complex.AbstractUiWidgetListTable;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.impl.gwt.widget.complex.adapter.UiWidgetAdapterGwtListTable;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.complex.UiWidgetListTable} using GWT.
 * 
 * @param <ROW> is the generic type of a row in the {@link #getValue() value list}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetListTableGwt<ROW> extends
    AbstractUiWidgetListTable<UiWidgetAdapterGwtListTable<ROW>, ROW, ItemContainerGwt<ROW>> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetListTableGwt(UiContext context, UiWidgetAdapterGwtListTable<ROW> widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ItemContainerGwt<ROW> createRowContainer() {

    return new ItemContainerGwt<ROW>(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtListTable<ROW> createWidgetAdapter() {

    return new UiWidgetAdapterGwtListTable<ROW>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetListTable> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetListTable.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public UiWidgetListTable create(UiContext context) {

      return new UiWidgetListTableGwt(context, null);
    }

  }

}
