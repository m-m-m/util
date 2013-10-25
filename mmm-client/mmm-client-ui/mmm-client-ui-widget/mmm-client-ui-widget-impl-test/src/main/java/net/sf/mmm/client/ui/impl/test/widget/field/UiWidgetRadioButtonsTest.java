/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtonsField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetOptionsField;
import net.sf.mmm.client.ui.impl.test.widget.field.adapter.UiWidgetAdapterTestOptionsField;

/**
 * This is the implementation of {@link UiWidgetRadioButtonsField} for testing without a native toolkit.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetRadioButtonsTest<VALUE> extends
    AbstractUiWidgetOptionsField<UiWidgetAdapterTestOptionsField<VALUE>, VALUE> implements
    UiWidgetRadioButtonsField<VALUE> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetRadioButtonsTest(UiContext context) {

    super(context);
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
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetRadioButtonsField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetRadioButtonsField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetRadioButtonsField create(UiContext context) {

      return new UiWidgetRadioButtonsTest(context);
    }

  }

}
