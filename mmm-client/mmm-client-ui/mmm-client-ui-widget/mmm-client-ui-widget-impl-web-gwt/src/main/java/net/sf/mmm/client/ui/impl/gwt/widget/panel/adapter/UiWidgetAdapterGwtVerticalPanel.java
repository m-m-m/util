/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterVerticalPanel;
import net.sf.mmm.client.ui.gwt.widgets.VerticalFlowPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtPanel;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterDynamicPanel} using GWT based on
 * {@link VerticalFlowPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtVerticalPanel extends UiWidgetAdapterGwtPanel<VerticalFlowPanel> implements
    UiWidgetAdapterVerticalPanel {

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
  protected VerticalFlowPanel createToplevelWidget() {

    VerticalFlowPanel verticalPanel = new VerticalFlowPanel();
    return verticalPanel;
  }

}
