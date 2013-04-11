/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetVerticalPanel;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetVerticalPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtVerticalPanel;

/**
 * This is the implementation of {@link UiWidgetVerticalPanel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetVerticalPanelGwt extends AbstractUiWidgetVerticalPanel<UiWidgetAdapterGwtVerticalPanel> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetVerticalPanelGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtVerticalPanel createWidgetAdapter() {

    return new UiWidgetAdapterGwtVerticalPanel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetVerticalPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetVerticalPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetVerticalPanel create(UiContext context) {

      return new UiWidgetVerticalPanelGwt(context);
    }
  }

}
