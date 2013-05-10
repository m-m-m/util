/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core.adapter;

import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterSlot;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidget;

import com.google.gwt.user.client.ui.FlowPanel;

/**
 * This is the implementation of {@link UiWidgetAdapterSlot} using GWT based on {@link FlowPanel}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtSlot extends UiWidgetAdapterGwtWidget<FlowPanel> implements UiWidgetAdapterSlot {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtSlot() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected FlowPanel createToplevelWidget() {

    return new FlowPanel();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setChild(UiWidgetRegular child) {

    getToplevelWidget().clear();
    getToplevelWidget().add(getToplevelWidget(child));
  }
}
