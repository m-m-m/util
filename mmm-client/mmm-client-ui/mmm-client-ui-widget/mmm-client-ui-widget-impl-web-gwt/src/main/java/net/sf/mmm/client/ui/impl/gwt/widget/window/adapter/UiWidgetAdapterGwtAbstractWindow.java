/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.window.adapter;

import net.sf.mmm.client.ui.api.common.Length;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterAbstractWindow;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.VerticalFlowPanel;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtDynamicComposite;

import com.google.gwt.user.client.ui.Panel;

/**
 * This is the implementation of {@link UiWidgetAdapterAbstractWindow} using GWT based on {@link Panel}.<br/>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 */
public abstract class UiWidgetAdapterGwtAbstractWindow<WIDGET extends Panel> extends
    UiWidgetAdapterGwtDynamicComposite<WIDGET, UiWidgetRegular> implements UiWidgetAdapterAbstractWindow {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtAbstractWindow() {

    super();
  }

  /**
   * @return the content panel.
   */
  protected abstract VerticalFlowPanel getContentPanel();

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
  public void addChild(UiWidgetRegular child, int index) {

    if (index >= 0) {
      getContentPanel().insert(getToplevelWidget(child), index);
    } else {
      getContentPanel().add(getToplevelWidget(child));
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setWidth(Length width) {

    getContentPanel().setWidth(width.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setHeight(Length height) {

    getContentPanel().setHeight(height.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setSize(Length width, Length height) {

    getContentPanel().setSize(width.toString(), height.toString());
  }

}
