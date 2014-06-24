/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLocalDateField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetLocalDateField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtLocalDateField;

/**
 * This is the implementation of {@link UiWidgetLocalDateField} using GWT based on
 * {@link UiWidgetAdapterGwtLocalDateField}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLocalDateFieldGwt extends AbstractUiWidgetLocalDateField<UiWidgetAdapterGwtLocalDateField> {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetLocalDateFieldGwt(UiContext context, UiWidgetAdapterGwtLocalDateField widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtLocalDateField createWidgetAdapter() {

    return new UiWidgetAdapterGwtLocalDateField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetLocalDateField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetLocalDateField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetLocalDateField create(UiContext context) {

      return new UiWidgetLocalDateFieldGwt(context, null);
    }

  }

}
