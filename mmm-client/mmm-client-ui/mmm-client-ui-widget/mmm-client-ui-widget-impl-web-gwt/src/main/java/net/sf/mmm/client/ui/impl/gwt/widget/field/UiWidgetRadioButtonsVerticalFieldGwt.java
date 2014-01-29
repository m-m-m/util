/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtonsVerticalField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetRadioButtonsVerticalField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterRadioButtonsField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtRadioButtonsVertical;

/**
 * This is the implementation of {@link UiWidgetRadioButtonsVerticalField} using GWT.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetRadioButtonsVerticalFieldGwt<VALUE> extends
    AbstractUiWidgetRadioButtonsVerticalField<UiWidgetAdapterRadioButtonsField<VALUE>, VALUE> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetRadioButtonsVerticalFieldGwt(UiContext context,
      UiWidgetAdapterGwtRadioButtonsVertical<VALUE> widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtRadioButtonsVertical<VALUE> createWidgetAdapter() {

    return new UiWidgetAdapterGwtRadioButtonsVertical<VALUE>();
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
    @SuppressWarnings("unchecked")
    @Override
    public UiWidgetRadioButtonsVerticalField create(UiContext context) {

      return new UiWidgetRadioButtonsVerticalFieldGwt(context, null);
    }

  }

}
