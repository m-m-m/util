/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLocalDateField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetLocalDateField;
import net.sf.mmm.client.ui.impl.test.widget.field.adapter.UiWidgetAdapterTestLocalDateField;

/**
 * This is the implementation of {@link UiWidgetLocalDateField} for testing without a native toolkit.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLocalDateFieldTestImpl extends AbstractUiWidgetLocalDateField<UiWidgetAdapterTestLocalDateField> {

  /**
   * The constructor.
   *
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetLocalDateFieldTestImpl(UiContext context) {

    super(context, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestLocalDateField createWidgetAdapter() {

    return new UiWidgetAdapterTestLocalDateField();
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

      return new UiWidgetLocalDateFieldTestImpl(context);
    }

  }

}
