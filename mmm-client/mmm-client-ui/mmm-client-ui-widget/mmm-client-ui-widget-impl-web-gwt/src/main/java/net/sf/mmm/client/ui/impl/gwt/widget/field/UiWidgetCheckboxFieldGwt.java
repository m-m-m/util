/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetCheckboxField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetCheckboxField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtCheckboxField;

/**
 * This is the implementation of {@link UiWidgetCheckboxField} using GWT based on
 * {@link UiWidgetAdapterGwtCheckboxField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCheckboxFieldGwt extends AbstractUiWidgetCheckboxField<UiWidgetAdapterGwtCheckboxField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetCheckboxFieldGwt(UiContext context, UiWidgetAdapterGwtCheckboxField widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtCheckboxField createWidgetAdapter() {

    return new UiWidgetAdapterGwtCheckboxField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetCheckboxField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetCheckboxField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetCheckboxField create(UiContext context) {

      return new UiWidgetCheckboxFieldGwt(context, null);
    }

  }

}
