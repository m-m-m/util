/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetFileField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetFileField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtFileField;

/**
 * This is the implementation of {@link UiWidgetFileField} using GWT based on
 * {@link UiWidgetAdapterGwtFileField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetFileFieldGwt extends AbstractUiWidgetFileField<UiWidgetAdapterGwtFileField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetFileFieldGwt(UiContext context, UiWidgetAdapterGwtFileField widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtFileField createWidgetAdapter() {

    return new UiWidgetAdapterGwtFileField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetFileField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetFileField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetFileField create(UiContext context) {

      return new UiWidgetFileFieldGwt(context, null);
    }

  }

}
