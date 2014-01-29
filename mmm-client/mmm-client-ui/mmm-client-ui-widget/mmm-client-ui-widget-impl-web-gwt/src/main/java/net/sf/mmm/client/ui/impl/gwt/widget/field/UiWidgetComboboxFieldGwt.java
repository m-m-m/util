/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboboxField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetComboboxField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterComboboxField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtComboBoxField;

/**
 * This is a simple implementation of {@link UiWidgetComboboxField} using GWT based on
 * {@link UiWidgetAdapterGwtComboBoxField}.
 * 
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetComboboxFieldGwt<VALUE> extends
    AbstractUiWidgetComboboxField<UiWidgetAdapterComboboxField<VALUE>, VALUE> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetComboboxFieldGwt(UiContext context, UiWidgetAdapterGwtComboBoxField<VALUE> widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtComboBoxField<VALUE> createWidgetAdapter() {

    return new UiWidgetAdapterGwtComboBoxField<VALUE>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetComboboxField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetComboboxField.class);
    }

    /**
     * {@inheritDoc}
     */
    @SuppressWarnings("unchecked")
    @Override
    public UiWidgetComboboxField create(UiContext context) {

      return new UiWidgetComboboxFieldGwt(context, null);
    }

  }

}
