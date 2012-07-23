/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetPasswordField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetInputField;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtPasswordTextBox;

/**
 * This is the implementation of {@link UiWidgetPasswordField} using GWT based on
 * {@link UiWidgetAdapterGwtPasswordTextBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetPasswordFieldGwt extends
    AbstractUiWidgetInputField<UiWidgetAdapterGwtPasswordTextBox<String>, String, String> implements
    UiWidgetPasswordField {

  /**
   * The constructor.
   */
  public UiWidgetPasswordFieldGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtPasswordTextBox<String> createWidgetAdapter() {

    return new UiWidgetAdapterGwtPasswordTextBox<String>();
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
    @Override
    public UiWidgetPasswordField create() {

      return new UiWidgetPasswordFieldGwt();
    }

  }

}
