/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridRow;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterGridPanel;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.GridPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtDynamicComposite;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterDynamicPanel} using GWT based on
 * {@link GridPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtGridPanel extends UiWidgetAdapterGwtDynamicComposite<GridPanel, UiWidgetGridRow>
    implements UiWidgetAdapterGridPanel {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtGridPanel() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GridPanel createToplevelWidget() {

    return new GridPanel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetGridRow child, int index) {

    Widget widget = getToplevelWidget(child);
    if (index < 0) {
      getToplevelWidget().add(widget);
    } else {
      getToplevelWidget().insert(widget, index);
    }
  }

}
