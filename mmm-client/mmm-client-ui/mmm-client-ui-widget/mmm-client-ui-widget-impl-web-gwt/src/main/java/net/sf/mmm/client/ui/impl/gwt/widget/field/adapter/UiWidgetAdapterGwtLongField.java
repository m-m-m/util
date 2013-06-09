/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterLongField;

import com.google.gwt.user.client.ui.LongBox;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField}
 * using GWT based on {@link LongBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtLongField extends UiWidgetAdapterGwtFieldValueBox<LongBox, Long, Long> implements
    UiWidgetAdapterLongField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtLongField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected LongBox createActiveWidget() {

    return new LongBox();
  }

}
