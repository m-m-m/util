/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetIntegerField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterIntegerField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtIntegerField;

/**
 * This is the implementation of {@link UiWidgetIntegerField} using GWT based on
 * {@link UiWidgetAdapterGwtIntegerField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetIntegerFieldGwt extends AbstractUiWidgetIntegerField<UiWidgetAdapterIntegerField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetIntegerFieldGwt(UiContext context, UiWidgetAdapterGwtIntegerField widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtIntegerField createWidgetAdapter() {

    return new UiWidgetAdapterGwtIntegerField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetIntegerField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetIntegerField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetIntegerField create(UiContext context) {

      return new UiWidgetIntegerFieldGwt(context, null);
    }

  }

}
