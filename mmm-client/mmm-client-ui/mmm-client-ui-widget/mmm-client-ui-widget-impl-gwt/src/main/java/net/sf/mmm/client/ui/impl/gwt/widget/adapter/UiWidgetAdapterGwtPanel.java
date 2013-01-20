/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterDynamicPanel;

import com.google.gwt.user.client.ui.InsertPanel;
import com.google.gwt.user.client.ui.Panel;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.panel.adapter.UiWidgetAdapterDynamicPanel} using
 * GWT based on {@link Panel} and {@link InsertPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetAdapterGwtPanel<WIDGET extends Panel & InsertPanel> extends
    UiWidgetAdapterGwtDynamicComposite<WIDGET, UiWidgetRegular> implements UiWidgetAdapterDynamicPanel<WIDGET> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtPanel() {

    super();
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
    Widget childWidget = getWidget(child);
    if (index == -1) {
      getWidget().add(childWidget);
    } else {
      getWidget().insert(childWidget, index);
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
