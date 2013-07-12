/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetLongField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetLongField;
import net.sf.mmm.client.ui.impl.test.widget.field.adapter.UiWidgetAdapterTestLongField;

/**
 * This is the implementation of {@link UiWidgetLongField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetLongFieldTest extends AbstractUiWidgetLongField<UiWidgetAdapterTestLongField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetLongFieldTest(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestLongField createWidgetAdapter() {

    return new UiWidgetAdapterTestLongField();
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

      return new UiWidgetLongFieldTest(context);
    }

  }

}
