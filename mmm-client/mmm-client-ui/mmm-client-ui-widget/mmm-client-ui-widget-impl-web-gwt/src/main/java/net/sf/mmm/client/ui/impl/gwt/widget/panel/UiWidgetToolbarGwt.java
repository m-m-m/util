/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetToolbar;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetToolbar;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtToolbar;

/**
 * This is the implementation of {@link UiWidgetToolbar} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetToolbarGwt extends AbstractUiWidgetToolbar<UiWidgetAdapterGwtToolbar> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetToolbarGwt(UiContext context, UiWidgetAdapterGwtToolbar widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtToolbar createWidgetAdapter() {

    return new UiWidgetAdapterGwtToolbar();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetToolbar> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetToolbar.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetToolbar create(UiContext context) {

      return new UiWidgetToolbarGwt(context, null);
    }
  }

}
