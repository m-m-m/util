/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import java.text.ParseException;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetField} using GWT based
 * on {@link ValueBoxBase}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetFieldGwtValueBoxBase<VALUE, WIDGET extends ValueBoxBase<VALUE>> extends
    UiWidgetFieldGwtFocusWidget<VALUE, WIDGET> {

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public UiWidgetFieldGwtValueBoxBase(WIDGET widget) {

    super(widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void registerChangeHandler(ChangeHandler handler) {

    getWidget().addChangeHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(VALUE value) {

    getWidget().setValue(value, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getValueOrException() {

    try {
      return getWidget().getValueOrThrow();
    } catch (ParseException e) {
      // TODO hohwille use specific runtime exception
      throw new RuntimeException(e.getMessage(), e);
    }
  }

}
