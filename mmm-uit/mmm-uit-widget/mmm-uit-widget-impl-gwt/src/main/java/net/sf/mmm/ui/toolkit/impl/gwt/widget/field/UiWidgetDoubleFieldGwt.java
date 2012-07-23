/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetDoubleField;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.base.widget.field.AbstractUiWidgetInputField;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtDoubleBox;

/**
 * This is the implementation of {@link UiWidgetDoubleField} using GWT based on
 * {@link UiWidgetAdapterGwtDoubleBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetDoubleFieldGwt extends
    AbstractUiWidgetInputField<UiWidgetAdapterGwtDoubleBox<Double>, Double, Double> implements UiWidgetDoubleField {

  /**
   * The constructor.
   */
  public UiWidgetDoubleFieldGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected UiWidgetAdapterGwtDoubleBox<Double> createWidgetAdapter() {

    return new UiWidgetAdapterGwtDoubleBox<Double>();
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetDoubleField> {

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
    public UiWidgetDoubleField create() {

      return new UiWidgetDoubleFieldGwt();
    }

  }

}
