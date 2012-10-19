/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field.adapter;

import net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterDoubleField;

import com.google.gwt.user.client.ui.DoubleBox;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterField}
 * using GWT based on {@link DoubleBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value - typically {@link Double}.
 */
public class UiWidgetAdapterGwtDoubleField<VALUE> extends UiWidgetAdapterGwtFieldValueBox<DoubleBox, VALUE, Double> implements
    UiWidgetAdapterDoubleField<DoubleBox, VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtDoubleField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected DoubleBox createWidget() {

    return new DoubleBox();
  }

}
