/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.widget.field.UiWidgetComboBox;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.factory.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetComboBox;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtComboBox;

/**
 * This is a simple implementation of {@link UiWidgetComboBox} using GWT based on
 * {@link UiWidgetAdapterGwtComboBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public class UiWidgetComboBoxGwt<VALUE> extends AbstractUiWidgetComboBox<UiWidgetAdapterGwtComboBox<VALUE>, VALUE> {

  /**
   * The constructor.
   * 
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetComboBoxGwt(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtComboBox<VALUE> createWidgetAdapter() {

    return new UiWidgetAdapterGwtComboBox<VALUE>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetComboBox> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetComboBox.class);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UiWidgetComboBox create(AbstractUiContext context) {

      return new UiWidgetComboBoxGwt(context);
    }

  }

}
