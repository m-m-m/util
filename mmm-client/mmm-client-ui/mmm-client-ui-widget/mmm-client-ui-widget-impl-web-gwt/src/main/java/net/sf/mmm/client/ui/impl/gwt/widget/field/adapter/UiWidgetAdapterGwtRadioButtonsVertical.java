/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * This is the implementation of
 * {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterOptionsField} using GWT based on
 * {@link VerticalPanel} and {@link com.google.gwt.user.client.ui.RadioButton}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value.
 */
public class UiWidgetAdapterGwtRadioButtonsVertical<VALUE> extends UiWidgetAdapterGwtRadioButtons<VALUE> {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtRadioButtonsVertical() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ComplexPanel doCreateActiveWidget() {

    return new VerticalPanel();
  }

}
