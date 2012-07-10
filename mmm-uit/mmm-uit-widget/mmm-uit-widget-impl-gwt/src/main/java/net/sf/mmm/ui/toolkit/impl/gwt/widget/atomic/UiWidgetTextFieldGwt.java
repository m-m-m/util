/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic;

import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetTextField;

import com.google.gwt.user.client.ui.TextBox;

/**
 * This is the implementation of {@link UiWidgetTextField} using GWT based on {@link TextBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTextFieldGwt extends UiWidgetEditorGwtValueBoxBase<String, TextBox> implements UiWidgetTextField {

  /**
   * The constructor.
   */
  public UiWidgetTextFieldGwt() {

    super(new TextBox());
  }

}
