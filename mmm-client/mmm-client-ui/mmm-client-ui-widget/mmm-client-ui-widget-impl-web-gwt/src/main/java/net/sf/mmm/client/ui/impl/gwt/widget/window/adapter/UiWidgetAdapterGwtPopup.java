/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.window.adapter;

import net.sf.mmm.client.ui.base.widget.window.adapter.UiWidgetAdapterPopup;
import net.sf.mmm.client.ui.gwt.widgets.PopupWindow;

/**
 * This is the implementation of {@link UiWidgetAdapterPopup} using GWT based on {@link PopupWindow}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtPopup extends UiWidgetAdapterGwtAbstractDialogWindow implements UiWidgetAdapterPopup {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtPopup() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected PopupWindow createToplevelWidget() {

    PopupWindow popup = new PopupWindow(false, true);
    popup.setGlassEnabled(true);
    return popup;
  }

}
