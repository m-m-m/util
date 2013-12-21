/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.panel.adapter;

import net.sf.mmm.client.ui.api.widget.panel.UiWidgetGridCell;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterGridRow;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.GridRow;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtDynamicComposite;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetAdapterGridRow} using GWT based on {@link GridRow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtGridRow extends UiWidgetAdapterGwtDynamicComposite<GridRow, UiWidgetGridCell> implements
    UiWidgetAdapterGridRow {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtGridRow() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected GridRow createToplevelWidget() {

    return new GridRow();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetGridCell child, int index) {

    Widget widget = getToplevelWidget(child);
    if (index < 0) {
      getToplevelWidget().add(widget);
    } else {
      getToplevelWidget().insert(widget, index);
    }
  }

}
