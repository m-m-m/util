/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLocalTimeField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetLocalTimeField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtLocalTimeField;

/**
 * This is the implementation of {@link UiWidgetLocalTimeField} using GWT.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLocalTimeFieldGwt extends AbstractUiWidgetLocalTimeField<UiWidgetAdapterGwtLocalTimeField> {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetLocalTimeFieldGwt(UiContext context, UiWidgetAdapterGwtLocalTimeField widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtLocalTimeField createWidgetAdapter() {

    return new UiWidgetAdapterGwtLocalTimeField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetLocalTimeField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetLocalTimeField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetLocalTimeField create(UiContext context) {

      return new UiWidgetLocalTimeFieldGwt(context, null);
    }

  }

}
