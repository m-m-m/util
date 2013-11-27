/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.menu;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuBar;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.menu.AbstractUiWidgetMenuBar;
import net.sf.mmm.client.ui.impl.gwt.widget.menu.adapter.UiWidgetAdapterGwtMenuBar;

/**
 * This is the implementation of {@link UiWidgetMenuBar} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetMenuBarGwt extends AbstractUiWidgetMenuBar<UiWidgetAdapterGwtMenuBar> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetMenuBarGwt(UiContext context, UiWidgetAdapterGwtMenuBar widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtMenuBar createWidgetAdapter() {

    return new UiWidgetAdapterGwtMenuBar();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetMenuBar> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetMenuBar.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetMenuBar create(UiContext context) {

      return new UiWidgetMenuBarGwt(context, null);
    }

  }

}
