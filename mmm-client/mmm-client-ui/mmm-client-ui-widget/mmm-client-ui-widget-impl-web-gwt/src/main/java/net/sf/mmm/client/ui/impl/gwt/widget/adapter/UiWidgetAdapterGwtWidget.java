/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.adapter;

import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} using GWT
 * based on {@link Widget}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 */
public abstract class UiWidgetAdapterGwtWidget<WIDGET extends Widget> extends UiWidgetAdapterGwt<WIDGET> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtWidget() {

    super();
  }

  /**
   * The constructor.
   * 
   * @param toplevelWidget is the {@link #getToplevelWidget() toplevel widget}.
   */
  public UiWidgetAdapterGwtWidget(WIDGET toplevelWidget) {

    super(toplevelWidget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Widget getActiveWidget() {

    return getToplevelWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void removeFromParent() {

    getToplevelWidget().removeFromParent();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void dispose() {

    super.dispose();
    getToplevelWidget().removeFromParent();
  }

}
