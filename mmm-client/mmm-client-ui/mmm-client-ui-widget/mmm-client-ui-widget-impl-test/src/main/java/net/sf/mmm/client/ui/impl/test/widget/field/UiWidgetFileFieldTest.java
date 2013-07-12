/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetFileField;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetIntegerField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetFileField;
import net.sf.mmm.client.ui.impl.test.widget.field.adapter.UiWidgetAdapterTestFileField;

/**
 * This is the implementation of {@link UiWidgetIntegerField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetFileFieldTest extends AbstractUiWidgetFileField<UiWidgetAdapterTestFileField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetFileFieldTest(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestFileField createWidgetAdapter() {

    return new UiWidgetAdapterTestFileField();
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

      return new UiWidgetFileFieldTest(context);
    }

  }

}
