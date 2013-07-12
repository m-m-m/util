/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetTextAreaField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetTextAreaField;
import net.sf.mmm.client.ui.impl.test.widget.field.adapter.UiWidgetAdapterTestStringField;

/**
 * This is the implementation of {@link UiWidgetTextAreaField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetTextAreaFieldTest extends AbstractUiWidgetTextAreaField<UiWidgetAdapterTestStringField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetTextAreaFieldTest(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestStringField createWidgetAdapter() {

    return new UiWidgetAdapterTestStringField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetTextAreaField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetTextAreaField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetTextAreaField create(UiContext context) {

      return new UiWidgetTextAreaFieldTest(context);
    }

  }

}
