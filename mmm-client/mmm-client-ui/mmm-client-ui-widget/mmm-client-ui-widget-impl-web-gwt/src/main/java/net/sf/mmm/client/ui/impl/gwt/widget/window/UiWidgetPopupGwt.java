/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.window;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.panel.UiWidgetButtonPanel;
import net.sf.mmm.client.ui.api.widget.window.UiWidgetPopup;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.window.AbstractUiWidgetPopup;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.UiWidgetButtonPanelGwt;
import net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter.UiWidgetAdapterGwtButtonPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.window.adapter.UiWidgetAdapterGwtPopup;

/**
 * This is the implementation of {@link UiWidgetPopup} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetPopupGwt extends AbstractUiWidgetPopup<UiWidgetAdapterGwtPopup> {

  /** @see #getButtonPanel() */
  private UiWidgetButtonPanel buttonPanel;

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetPopupGwt(UiContext context, UiWidgetAdapterGwtPopup widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiWidgetButtonPanel getButtonPanel() {

    if (this.buttonPanel == null) {
      UiWidgetAdapterGwtButtonPanel buttonPanelAdapter = new UiWidgetAdapterGwtButtonPanel(getWidgetAdapter()
          .getToplevelWidget().getButtonPanel());
      this.buttonPanel = new UiWidgetButtonPanelGwt(getContext(), buttonPanelAdapter);
      setParent(this.buttonPanel, this);
    }
    return this.buttonPanel;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtPopup createWidgetAdapter() {

    return new UiWidgetAdapterGwtPopup();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetPopup> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetPopup.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetPopup create(UiContext context) {

      return new UiWidgetPopupGwt(context, null);
    }
  }

}
