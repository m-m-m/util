/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.core.adapter;

import net.sf.mmm.client.ui.base.widget.core.adapter.UiWidgetAdapterButton;
import net.sf.mmm.client.ui.impl.gwt.widget.adapter.UiWidgetAdapterGwtClickable;

import com.google.gwt.user.client.ui.Button;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapter} using GWT
 * based on {@link Button}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtButton extends UiWidgetAdapterGwtClickable<Button> implements UiWidgetAdapterButton {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtButton() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Button createToplevelWidget() {

    return new Button();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    getToplevelWidget().setText(label);
  }

}
