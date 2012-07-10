/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.ValueBoxBase;

/**
 * This is the implementation of {@link net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetEditor} using GWT
 * based on {@link ValueBoxBase}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE>
 * @param <WIDGET>
 */
public abstract class UiWidgetEditorGwtValueBoxBase<VALUE, WIDGET extends ValueBoxBase<VALUE>> extends
    UiWidgetEditorGwt<VALUE, WIDGET> {

  /**
   * The constructor.
   * 
   * @param widget is the active widget.
   */
  public UiWidgetEditorGwtValueBoxBase(WIDGET widget) {

    super(widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void registerChangeHandler(ChangeHandler handler) {

    getActiveWidget().addChangeHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetValue(VALUE value) {

    getActiveWidget().setValue(value, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public VALUE getValue() {

    return getActiveWidget().getValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean enabled) {

    getActiveWidget().setEnabled(enabled);
  }

}
