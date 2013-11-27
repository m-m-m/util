/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtonsVerticalField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetOptionsField;
import net.sf.mmm.client.ui.impl.test.widget.field.adapter.UiWidgetAdapterTestOptionsField;

/**
 * This is the implementation of {@link UiWidgetRadioButtonsVerticalField} for testing without a native
 * toolkit.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetRadioButtonsVerticalFieldTestImpl<VALUE> extends
    AbstractUiWidgetOptionsField<UiWidgetAdapterTestOptionsField<VALUE>, VALUE> implements
    UiWidgetRadioButtonsVerticalField<VALUE> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetRadioButtonsVerticalFieldTestImpl(UiContext context) {

    super(context, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestOptionsField<VALUE> createWidgetAdapter() {

    return new UiWidgetAdapterTestOptionsField<VALUE>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetRadioButtonsVerticalField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetRadioButtonsVerticalField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetRadioButtonsVerticalField create(UiContext context) {

      return new UiWidgetRadioButtonsVerticalFieldTestImpl(context);
    }

  }

}
