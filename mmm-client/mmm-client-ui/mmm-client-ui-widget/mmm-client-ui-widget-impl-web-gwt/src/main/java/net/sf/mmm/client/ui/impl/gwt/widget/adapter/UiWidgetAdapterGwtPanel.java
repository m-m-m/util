/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterDynamicPanel;

import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterDynamicPanel} using GWT based on
 * {@link Panel} and {@link InsertPanel}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtPanel<WIDGET extends Panel & InsertPanel> extends
    UiWidgetAdapterGwtDynamicComposite<WIDGET, UiWidgetRegular> implements UiWidgetAdapterDynamicPanel {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtPanel() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param toplevelWidget is the {@link #getToplevelWidget() toplevel widget}.
   */
  public UiWidgetAdapterGwtPanel(WIDGET toplevelWidget) {

    super(toplevelWidget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // nothing to do...
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChild(UiWidgetRegular child, int index) {

    // @SuppressWarnings("unchecked")
    Widget childWidget = getToplevelWidget(child);
    if (index == -1) {
      getToplevelWidget().add(childWidget);
    } else {
      getToplevelWidget().insert(childWidget, index);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeChild(UiWidgetRegular child, int index) {

    // nothing to do (removeFromParent() is already sufficient)
  }
}
