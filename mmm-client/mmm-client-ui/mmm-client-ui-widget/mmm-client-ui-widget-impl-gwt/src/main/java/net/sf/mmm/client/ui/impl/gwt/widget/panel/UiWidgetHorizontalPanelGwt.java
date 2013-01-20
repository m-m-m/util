/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.widget.panel.UiWidgetHorizontalPanel;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetHorizontalPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtHorizontalPanel;

/**
 * This is the implementation of {@link UiWidgetHorizontalPanel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetHorizontalPanelGwt extends AbstractUiWidgetHorizontalPanel<UiWidgetAdapterGwtHorizontalPanel> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetHorizontalPanelGwt(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtHorizontalPanel createWidgetAdapter() {

    return new UiWidgetAdapterGwtHorizontalPanel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetHorizontalPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetHorizontalPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetHorizontalPanel create(AbstractUiContext context) {

      return new UiWidgetHorizontalPanelGwt(context);
    }
  }

}
