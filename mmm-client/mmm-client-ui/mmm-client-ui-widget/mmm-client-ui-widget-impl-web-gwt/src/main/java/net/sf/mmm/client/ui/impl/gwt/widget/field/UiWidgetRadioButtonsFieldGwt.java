/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetRadioButtonsField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetRadioButtonsField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterRadioButtonsField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtRadioButtonsHorizontal;

/**
 * This is the implementation of {@link UiWidgetRadioButtonsField} using GWT.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetRadioButtonsFieldGwt<VALUE> extends
    AbstractUiWidgetRadioButtonsField<UiWidgetAdapterRadioButtonsField<VALUE>, VALUE> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetRadioButtonsFieldGwt(UiContext context, UiWidgetAdapterGwtRadioButtonsHorizontal<VALUE> widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtRadioButtonsHorizontal<VALUE> createWidgetAdapter() {

    return new UiWidgetAdapterGwtRadioButtonsHorizontal<VALUE>();
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
    @SuppressWarnings("unchecked")
    @Override
    public UiWidgetRadioButtonsField create(UiContext context) {

      return new UiWidgetRadioButtonsFieldGwt(context, null);
    }

  }

}
