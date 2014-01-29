/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerSliderField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetIntegerSliderField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterIntegerSliderField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtIntegerSliderField;

/**
 * This is the implementation of {@link UiWidgetIntegerSliderField} using GWT based on
 * {@link UiWidgetAdapterGwtIntegerSliderField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetIntegerSliderFieldGwt extends AbstractUiWidgetIntegerSliderField<UiWidgetAdapterIntegerSliderField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetIntegerSliderFieldGwt(UiContext context, UiWidgetAdapterGwtIntegerSliderField widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtIntegerSliderField createWidgetAdapter() {

    return new UiWidgetAdapterGwtIntegerSliderField();
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

      return new UiWidgetIntegerSliderFieldGwt(context, null);
    }

  }

}
