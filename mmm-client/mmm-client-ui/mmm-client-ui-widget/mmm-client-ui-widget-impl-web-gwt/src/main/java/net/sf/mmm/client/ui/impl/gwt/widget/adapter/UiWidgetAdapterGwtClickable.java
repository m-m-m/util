/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.adapter;

import net.sf.mmm.client.ui.base.widget.adapter.UiWidgetAdapterClickable;

import com.google.gwt.user.client.ui.FocusWidget;

/**
 * This is the implementation of {@link UiWidgetAdapterClickable} using GWT based on {@link FocusWidget}.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiWidgetAdapterGwtClickable<WIDGET extends FocusWidget> extends
    UiWidgetAdapterGwtFocusWidget<WIDGET> implements UiWidgetAdapterClickable {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtClickable() {

    super();
  }

}
