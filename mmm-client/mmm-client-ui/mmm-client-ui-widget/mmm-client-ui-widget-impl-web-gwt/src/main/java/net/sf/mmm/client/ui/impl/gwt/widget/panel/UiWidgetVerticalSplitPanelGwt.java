/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalSplitPanel;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetVerticalSplitPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtVerticalSplitPanel;

/**
 * This is the implementation of {@link UiWidgetVerticalSplitPanel} using GWT.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetVerticalSplitPanelGwt extends
    AbstractUiWidgetVerticalSplitPanel<UiWidgetAdapterGwtVerticalSplitPanel> {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetVerticalSplitPanelGwt(UiContext context, UiWidgetAdapterGwtVerticalSplitPanel widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtVerticalSplitPanel createWidgetAdapter() {

    return new UiWidgetAdapterGwtVerticalSplitPanel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetVerticalSplitPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetVerticalSplitPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetVerticalSplitPanel create(UiContext context) {

      return new UiWidgetVerticalSplitPanelGwt(context, null);
    }
  }

}
