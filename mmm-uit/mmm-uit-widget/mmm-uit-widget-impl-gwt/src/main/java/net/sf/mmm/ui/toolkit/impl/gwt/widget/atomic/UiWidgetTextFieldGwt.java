/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic;

import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetTextField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

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

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetTextField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTextField.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetTextField create() {

      return new UiWidgetTextFieldGwt();
    }

  }

}
