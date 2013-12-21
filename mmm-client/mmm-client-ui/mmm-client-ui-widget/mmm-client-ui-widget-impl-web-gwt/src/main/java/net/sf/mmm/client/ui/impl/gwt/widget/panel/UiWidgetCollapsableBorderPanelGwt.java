/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetCollapsableBorderPanel;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetCollapsableBorderPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtCollapsableBorderPanel;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetBorderPanel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCollapsableBorderPanelGwt extends
    AbstractUiWidgetCollapsableBorderPanel<UiWidgetAdapterGwtCollapsableBorderPanel> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetCollapsableBorderPanelGwt(UiContext context, UiWidgetAdapterGwtCollapsableBorderPanel widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtCollapsableBorderPanel createWidgetAdapter() {

    return new UiWidgetAdapterGwtCollapsableBorderPanel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetCollapsableBorderPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetCollapsableBorderPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetCollapsableBorderPanel create(UiContext context) {

      return new UiWidgetCollapsableBorderPanelGwt(context, null);
    }
  }

}
