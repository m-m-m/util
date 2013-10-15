/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetButtonPanel;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetButtonPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtButtonPanel;

/**
 * This is the implementation of {@link UiWidgetButtonPanel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetButtonPanelGwt extends AbstractUiWidgetButtonPanel<UiWidgetAdapterGwtButtonPanel> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetButtonPanelGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtButtonPanel createWidgetAdapter() {

    return new UiWidgetAdapterGwtButtonPanel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetButtonPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetButtonPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetButtonPanel create(UiContext context) {

      return new UiWidgetButtonPanelGwt(context);
    }
  }

}
