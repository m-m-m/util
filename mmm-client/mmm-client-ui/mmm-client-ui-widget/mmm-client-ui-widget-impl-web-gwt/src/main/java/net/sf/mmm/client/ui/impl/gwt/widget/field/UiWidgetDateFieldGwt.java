/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import java.util.Date;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDateField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetTextualInputField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtDateField;

/**
 * This is the implementation of {@link UiWidgetDateField} using GWT based on
 * {@link UiWidgetAdapterGwtDateField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetDateFieldGwt extends
    AbstractUiWidgetTextualInputField<UiWidgetAdapterGwtDateField<Date>, Date, Date> implements UiWidgetDateField {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetDateFieldGwt(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtDateField<Date> createWidgetAdapter() {

    return new UiWidgetAdapterGwtDateField<Date>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetDateField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetDateField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetDateField create(UiContext context) {

      return new UiWidgetDateFieldGwt(context);
    }

  }

}
