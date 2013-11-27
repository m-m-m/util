/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test.widget.field;

import net.sf.mmm.client.ui.api.UiContext;
import net.sf.mmm.client.ui.api.widget.field.UiWidgetCheckboxField;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryNative;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetCheckboxField;
import net.sf.mmm.client.ui.impl.test.widget.field.adapter.UiWidgetAdapterTestCheckboxField;

/**
 * This is the implementation of {@link UiWidgetCheckboxField} for testing without a native toolkit.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetCheckboxFieldTestImpl extends AbstractUiWidgetCheckboxField<UiWidgetAdapterTestCheckboxField> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetCheckboxFieldTestImpl(UiContext context) {

    super(context, null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterTestCheckboxField createWidgetAdapter() {

    return new UiWidgetAdapterTestCheckboxField();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryNative factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryNative<UiWidgetCheckboxField> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetCheckboxField.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetCheckboxField create(UiContext context) {

      return new UiWidgetCheckboxFieldTestImpl(context);
    }

  }

}
