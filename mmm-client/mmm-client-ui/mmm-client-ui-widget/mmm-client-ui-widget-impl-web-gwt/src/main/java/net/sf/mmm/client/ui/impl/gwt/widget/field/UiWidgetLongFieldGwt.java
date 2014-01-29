/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetLongField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterLongField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtLongField;

/**
 * This is the implementation of {@link UiWidgetLongField} using GWT based on
 * {@link UiWidgetAdapterGwtLongField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLongFieldGwt extends AbstractUiWidgetLongField<UiWidgetAdapterLongField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetLongFieldGwt(UiContext context, UiWidgetAdapterGwtLongField widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtLongField createWidgetAdapter() {

    return new UiWidgetAdapterGwtLongField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetLongField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetLongField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetLongField create(UiContext context) {

      return new UiWidgetLongFieldGwt(context, null);
    }

  }

}
