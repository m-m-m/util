/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter;

import com.google.gwt.user.client.ui.IntegerBox;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.base.widget.adapter.UiWidgetAdapterField} using
 * GWT based on {@link IntegerBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value - typically {@link Integer}.
 */
public class UiWidgetAdapterGwtIntegerBox<VALUE> extends UiWidgetAdapterGwtValueBox<IntegerBox, VALUE, Integer> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtIntegerBox() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IntegerBox createWidget() {

    return new IntegerBox();
  }

}
