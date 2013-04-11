/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetPasswordField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetTextualInputField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtPasswordTextBox;

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
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetPasswordFieldGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtPasswordTextBox<String> createWidgetAdapter() {

    return new UiWidgetAdapterGwtPasswordTextBox<String>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetPasswordField> {

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
    public UiWidgetPasswordField create(UiContext context) {

      return new UiWidgetPasswordFieldGwt(context);
    }

  }

}
