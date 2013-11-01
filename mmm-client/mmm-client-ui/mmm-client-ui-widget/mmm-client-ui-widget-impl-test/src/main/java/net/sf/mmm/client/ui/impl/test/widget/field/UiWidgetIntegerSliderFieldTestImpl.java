/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerSliderField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetIntegerSliderField;
import net.sf.mmm.client.ui.impl.test.widget.field.adapter.UiWidgetAdapterTestIntegerField;

/**
 * This is the implementation of {@link UiWidgetIntegerSliderField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetIntegerSliderFieldTestImpl extends
    AbstractUiWidgetIntegerSliderField<UiWidgetAdapterTestIntegerField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetIntegerSliderFieldTestImpl(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestIntegerField createWidgetAdapter() {

    return new UiWidgetAdapterTestIntegerField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetIntegerSliderField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetIntegerSliderField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetIntegerSliderField create(UiContext context) {

      return new UiWidgetIntegerSliderFieldTestImpl(context);
    }

  }

}
