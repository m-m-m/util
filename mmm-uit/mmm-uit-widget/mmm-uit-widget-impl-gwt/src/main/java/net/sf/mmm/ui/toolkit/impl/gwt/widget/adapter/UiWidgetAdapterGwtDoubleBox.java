/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import com.google.gwt.user.client.ui.DoubleBox;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterField} using
 * GWT based on {@link DoubleBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value - typically {@link Double}.
 */
public class UiWidgetAdapterGwtDoubleBox<VALUE> extends UiWidgetAdapterGwtValueBox<DoubleBox, VALUE, Double> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtDoubleBox() {

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
