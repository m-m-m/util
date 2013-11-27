/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTimeField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetTimeField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtTimeField;

/**
 * This is the implementation of {@link UiWidgetTimeField} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTimeFieldGwt extends AbstractUiWidgetTimeField<UiWidgetAdapterGwtTimeField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetTimeFieldGwt(UiContext context, UiWidgetAdapterGwtTimeField widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtTimeField createWidgetAdapter() {

    return new UiWidgetAdapterGwtTimeField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetTimeField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTimeField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetTimeField create(UiContext context) {

      return new UiWidgetTimeFieldGwt(context, null);
    }

  }

}
