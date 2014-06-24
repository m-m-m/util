/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLocalTimeField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetLocalTimeField;
import net.sf.mmm.client.ui.impl.test.widget.field.adapter.UiWidgetAdapterTestLocalTimeField;

/**
 * This is the implementation of {@link UiWidgetLocalTimeField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLocalTimeFieldTestImpl extends AbstractUiWidgetLocalTimeField<UiWidgetAdapterTestLocalTimeField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetLocalTimeFieldTestImpl(UiContext context) {

    super(context, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestLocalTimeField createWidgetAdapter() {

    return new UiWidgetAdapterTestLocalTimeField();
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

      return new UiWidgetLocalTimeFieldTestImpl(context);
    }

  }

}
