/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterVerticalSplitPanel;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterVerticalSplitPanel} using GWT.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtVerticalSplitPanel extends UiWidgetAdapterGwtSplitPanel implements
    UiWidgetAdapterVerticalSplitPanel {

  /**
   * The constructor.
   *
   */
  public UiWidgetAdapterGwtVerticalSplitPanel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetRegular child, int index) {

    double size = 100;
    // TODO all sucks...
    Widget widget = getToplevelWidget(child);
    getToplevelWidget().addNorth(widget, size);
  }

}
