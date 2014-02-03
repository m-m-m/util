/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterHorizontalPanel;
import net.sf.mmm.client.ui.gwt.widgets.HorizontalFlowPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtPanel;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterDynamicPanel} using GWT based on
 * {@link HorizontalFlowPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtHorizontalPanel extends UiWidgetAdapterGwtPanel<HorizontalFlowPanel> implements
    UiWidgetAdapterHorizontalPanel {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtHorizontalPanel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HorizontalFlowPanel createToplevelWidget() {

    return new HorizontalFlowPanel();
  }

}
