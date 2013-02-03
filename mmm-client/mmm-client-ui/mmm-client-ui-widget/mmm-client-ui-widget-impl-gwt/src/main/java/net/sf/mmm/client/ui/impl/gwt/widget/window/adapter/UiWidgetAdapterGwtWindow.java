/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.window.adapter;

import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterWindow;

import com.google.gwt.user.client.ui.DialogBox;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterWindow}
 * using GWT based on {@link DialogBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtWindow extends UiWidgetAdapterGwtBaseWindowDialogBox<DialogBox> implements
    UiWidgetAdapterWindow {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtWindow() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DialogBox createToplevelWidget() {

    return new DialogBox(false, false);
  }

}
