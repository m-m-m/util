/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.panel;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridPanel;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.panel.AbstractUiWidgetGridPanel;
import net.sf.mmm.client.ui.impl.test.widget.panel.adapter.UiWidgetAdapterTestGridPanel;

/**
 * This is the implementation of {@link UiWidgetGridPanel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetGridPanelTestImpl extends AbstractUiWidgetGridPanel<UiWidgetAdapterTestGridPanel> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetGridPanelTestImpl(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestGridPanel createWidgetAdapter() {

    return new UiWidgetAdapterTestGridPanel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetGridPanel> {

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
    public UiWidgetGridPanel create(UiContext context) {

      return new UiWidgetGridPanelTestImpl(context);
    }
  }

}
