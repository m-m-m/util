/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.panel;

import net.sf.mmm.ui.toolkit.api.widget.panel.UiWidgetVerticalPanel;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is the implementation of {@link UiWidgetVerticalPanel} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetVerticalPanelGwt extends UiWidgetPanelGwt<VerticalPanel> implements UiWidgetVerticalPanel {

  /**
   * The constructor.
   */
  public UiWidgetVerticalPanelGwt() {

    super(new VerticalPanel());
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetVerticalPanel> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetVerticalPanel.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetVerticalPanel create() {

      return new UiWidgetVerticalPanelGwt();
    }
  }

}
