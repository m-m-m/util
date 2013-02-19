/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.widget.panel.UiWidgetTabPanel;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetTabPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtTabLayoutPanel;

/**
 * This is the implementation of {@link UiWidgetTabPanel} using GWT based on
 * {@link UiWidgetAdapterGwtTabLayoutPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTabPanelGwt extends AbstractUiWidgetTabPanel<UiWidgetAdapterGwtTabLayoutPanel> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetTabPanelGwt(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtTabLayoutPanel createWidgetAdapter() {

    return new UiWidgetAdapterGwtTabLayoutPanel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void initializeWidgetAdapter(UiWidgetAdapterGwtTabLayoutPanel adapter) {

    super.initializeWidgetAdapter(adapter);
    if ((getShowChildIndex() == 0) && (getChildCount() > 0)) {
      adapter.showChild(0);
    }
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetTabPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTabPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetTabPanel create(AbstractUiContext context) {

      return new UiWidgetTabPanelGwt(context);
    }

  }

}
