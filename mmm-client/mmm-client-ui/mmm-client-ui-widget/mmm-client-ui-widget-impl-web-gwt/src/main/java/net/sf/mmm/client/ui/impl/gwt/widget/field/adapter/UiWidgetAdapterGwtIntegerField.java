/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterIntegerField;

import com.google.gwt.user.client.ui.IntegerBox;

/**
 * This is the implementation of {@link UiWidgetAdapterIntegerField} using GWT based on {@link IntegerBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtIntegerField extends UiWidgetAdapterGwtFieldValueBox<IntegerBox, Integer, Integer>
    implements UiWidgetAdapterIntegerField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtIntegerField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IntegerBox createActiveWidget() {

    return new IntegerBox();
  }

}
