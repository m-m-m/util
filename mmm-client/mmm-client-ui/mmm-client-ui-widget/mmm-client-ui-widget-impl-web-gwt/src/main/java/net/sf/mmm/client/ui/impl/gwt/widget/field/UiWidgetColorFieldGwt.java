/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetColorField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetColorField;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterColorField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtColorField;

/**
 * This is the implementation of {@link UiWidgetColorField} using GWT based on
 * {@link UiWidgetAdapterGwtColorField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetColorFieldGwt extends AbstractUiWidgetColorField<UiWidgetAdapterColorField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   * @param widgetAdapter is the {@link #getWidgetAdapter() widget adapter}. Typically <code>null</code> for
   *        lazy initialization.
   */
  public UiWidgetColorFieldGwt(UiContext context, UiWidgetAdapterGwtColorField widgetAdapter) {

    super(context, widgetAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtColorField createWidgetAdapter() {

    return new UiWidgetAdapterGwtColorField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetColorField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetColorField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetColorField create(UiContext context) {

      return new UiWidgetColorFieldGwt(context, null);
    }

  }

}
