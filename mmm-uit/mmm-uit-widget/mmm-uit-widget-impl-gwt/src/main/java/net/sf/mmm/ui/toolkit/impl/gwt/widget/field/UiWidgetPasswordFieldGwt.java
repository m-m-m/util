/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetPasswordField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;

import com.google.gwt.user.client.ui.PasswordTextBox;

/**
 * This is the implementation of {@link UiWidgetPasswordField} using GWT based on {@link PasswordTextBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetPasswordFieldGwt extends UiWidgetInputFieldGwtTextBox<PasswordTextBox> implements UiWidgetPasswordField {

  /**
   * The constructor.
   */
  public UiWidgetPasswordFieldGwt() {

    super(new PasswordTextBox());
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetPasswordField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetPasswordField.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetPasswordField create() {

      return new UiWidgetPasswordFieldGwt();
    }

  }

}
