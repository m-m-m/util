/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetDoubleField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetDoubleField;
import net.sf.mmm.client.ui.impl.test.widget.field.adapter.UiWidgetAdapterTestDoubleField;

/**
 * This is the implementation of {@link UiWidgetDoubleField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetDoubleFieldTest extends AbstractUiWidgetDoubleField<UiWidgetAdapterTestDoubleField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetDoubleFieldTest(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestDoubleField createWidgetAdapter() {

    return new UiWidgetAdapterTestDoubleField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetDoubleField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetDoubleField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetDoubleField create(UiContext context) {

      return new UiWidgetDoubleFieldTest(context);
    }

  }

}
