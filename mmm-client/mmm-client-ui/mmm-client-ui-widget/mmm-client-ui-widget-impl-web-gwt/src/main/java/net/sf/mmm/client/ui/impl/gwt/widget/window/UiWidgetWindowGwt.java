/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.window;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetWindow;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.window.AbstractUiWidgetWindow;
import net.sf.mmm.client.ui.impl.gwt.widget.window.adapter.UiWidgetAdapterGwtWindow;

/**
 * This is the implementation of {@link UiWidgetWindow} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetWindowGwt extends AbstractUiWidgetWindow<UiWidgetAdapterGwtWindow> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetWindowGwt(UiContext context, UiWidgetAdapterGwtWindow widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtWindow createWidgetAdapter() {

    return new UiWidgetAdapterGwtWindow();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetWindow> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetWindow.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetWindow create(UiContext context) {

      return new UiWidgetWindowGwt(context, null);
    }
  }

}
