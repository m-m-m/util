/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalSplitPanel;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetHorizontalSplitPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtHorizontalSplitPanel;

/**
 * This is the implementation of {@link UiWidgetHorizontalSplitPanel} using GWT.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetHorizontalSplitPanelGwt extends
    AbstractUiWidgetHorizontalSplitPanel<UiWidgetAdapterGwtHorizontalSplitPanel> {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetHorizontalSplitPanelGwt(UiContext context, UiWidgetAdapterGwtHorizontalSplitPanel widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtHorizontalSplitPanel createWidgetAdapter() {

    return new UiWidgetAdapterGwtHorizontalSplitPanel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetHorizontalSplitPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetHorizontalSplitPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetHorizontalSplitPanel create(UiContext context) {

      return new UiWidgetHorizontalSplitPanelGwt(context, null);
    }
  }

}
