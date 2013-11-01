/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetColorField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetColorField;
import net.sf.mmm.client.ui.impl.test.widget.field.adapter.UiWidgetAdapterTestColorField;

/**
 * This is the implementation of {@link UiWidgetColorField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetColorFieldTestImpl extends AbstractUiWidgetColorField<UiWidgetAdapterTestColorField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetColorFieldTestImpl(UiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestColorField createWidgetAdapter() {

    return new UiWidgetAdapterTestColorField();
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

      return new UiWidgetColorFieldTestImpl(context);
    }

  }

}
