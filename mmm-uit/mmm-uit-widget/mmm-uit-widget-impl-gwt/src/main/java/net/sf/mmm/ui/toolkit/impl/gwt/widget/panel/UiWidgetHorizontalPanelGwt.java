/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.panel;

import net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetHorizontalPanel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

import com.google.gwt.user.client.ui.HorizontalPanel;

/**
 * This is the implementation of {@link UiWidgetHorizontalPanel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetHorizontalPanelGwt extends UiWidgetPanelGwt<HorizontalPanel> implements UiWidgetHorizontalPanel {

  /**
   * The constructor.
   */
  public UiWidgetHorizontalPanelGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HorizontalPanel createWidget() {

    return new HorizontalPanel();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetHorizontalPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetHorizontalPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetHorizontalPanel create() {

      return new UiWidgetHorizontalPanelGwt();
    }
  }

}
