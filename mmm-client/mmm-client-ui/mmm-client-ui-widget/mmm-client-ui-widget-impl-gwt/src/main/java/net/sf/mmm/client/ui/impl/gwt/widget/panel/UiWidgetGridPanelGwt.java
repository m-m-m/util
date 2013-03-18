/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridPanel;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetGridPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtGridPanel;

/**
 * This is the implementation of {@link UiWidgetGridPanel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetGridPanelGwt extends AbstractUiWidgetGridPanel<UiWidgetAdapterGwtGridPanel> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetGridPanelGwt(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtGridPanel createWidgetAdapter() {

    return new UiWidgetAdapterGwtGridPanel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetGridPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetGridPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetGridPanel create(AbstractUiContext context) {

      return new UiWidgetGridPanelGwt(context);
    }
  }

}
