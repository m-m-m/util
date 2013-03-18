/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtPanel;

import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterDynamicPanel} using GWT based on
 * {@link VerticalPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtVerticalPanel extends UiWidgetAdapterGwtPanel<VerticalPanel> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtVerticalPanel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected VerticalPanel createToplevelWidget() {

    VerticalPanel verticalPanel = new VerticalPanel();
    verticalPanel.setWidth("100%");
    return verticalPanel;
  }

}
