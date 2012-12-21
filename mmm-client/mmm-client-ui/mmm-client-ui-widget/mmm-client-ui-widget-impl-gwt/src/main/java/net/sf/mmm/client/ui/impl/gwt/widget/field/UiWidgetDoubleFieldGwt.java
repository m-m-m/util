/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field;

import net.sf.mmm.client.ui.api.widget.field.UiWidgetDoubleField;
import net.sf.mmm.client.ui.base.AbstractUiContext;
import net.sf.mmm.client.ui.base.widget.AbstractUiSingleWidgetFactoryReal;
import net.sf.mmm.client.ui.base.widget.field.AbstractUiWidgetTextualInputField;
import net.sf.mmm.client.ui.impl.gwt.widget.field.adapter.UiWidgetAdapterGwtDoubleField;

/**
 * This is the implementation of {@link UiWidgetDoubleField} using GWT based on
 * {@link UiWidgetAdapterGwtDoubleField}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetDoubleFieldGwt extends
    AbstractUiWidgetTextualInputField<UiWidgetAdapterGwtDoubleField<Double>, Double, Double> implements UiWidgetDoubleField {

  /**
   * The constructor.
   * @param context is the {@link #getContext() context}.
   */
  public UiWidgetDoubleFieldGwt(AbstractUiContext context) {

    super(context);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtDoubleField<Double> createWidgetAdapter() {

    return new UiWidgetAdapterGwtDoubleField<Double>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactoryReal factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactoryReal<UiWidgetDoubleField> {

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
    public UiWidgetDoubleField create(AbstractUiContext context) {

      return new UiWidgetDoubleFieldGwt(context);
    }

  }

}
