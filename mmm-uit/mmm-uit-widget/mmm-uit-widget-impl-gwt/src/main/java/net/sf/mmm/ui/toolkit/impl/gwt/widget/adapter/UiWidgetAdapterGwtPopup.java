/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterPopup;

import com.google.gwt.user.client.ui.DialogBox;

/**
 * This is the implementation of {@link UiWidgetAdapterPopup} using GWT based on {@link DialogBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtPopup extends UiWidgetAdapterGwtBaseWindow<DialogBox> implements
    UiWidgetAdapterPopup<DialogBox> {

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
  protected DialogBox createWidget() {

    DialogBox dialogBox = new DialogBox(false, true);
    dialogBox.setGlassEnabled(true);
    return dialogBox;
  }

}
