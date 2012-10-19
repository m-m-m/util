/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetPasswordField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetTextualInputField;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtPasswordTextBox;

/**
 * This is the implementation of {@link UiWidgetPasswordField} using GWT based on
 * {@link UiWidgetAdapterGwtPasswordTextBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetPasswordFieldGwt extends
    AbstractUiWidgetTextualInputField<UiWidgetAdapterGwtPasswordTextBox<String>, String, String> implements
    UiWidgetPasswordField {

  /**
   * The constructor.
   * 
   * @param factory is the {@link #getFactory() factory}.
   */
  public UiWidgetPasswordFieldGwt(AbstractUiWidgetFactory<?> factory) {

    super(factory);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtPasswordTextBox<String> createWidgetAdapter() {

    return new UiWidgetAdapterGwtPasswordTextBox<String>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetPasswordField> {

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
    public UiWidgetPasswordField create(AbstractUiWidgetFactory<?> factory) {

      return new UiWidgetPasswordFieldGwt(factory);
    }

  }

}
