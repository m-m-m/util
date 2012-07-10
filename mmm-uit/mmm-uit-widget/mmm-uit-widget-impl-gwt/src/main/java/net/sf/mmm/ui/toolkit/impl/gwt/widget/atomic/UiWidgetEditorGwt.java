/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic;

import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventChange;
import net.sf.mmm.ui.toolkit.api.widget.atomic.UiWidgetEditor;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.ChangeEventSenderGwt;

import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetEditor} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE>
 * @param <WIDGET>
 */
public abstract class UiWidgetEditorGwt<VALUE, WIDGET extends Widget> extends UiWidgetAtomicRegularGwt<WIDGET>
    implements UiWidgetEditor<VALUE> {

  /** @see #addChangeHandler(UiHandlerEventChange) */
  private ChangeEventSenderGwt<VALUE> changeEventSender;

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getToplevelWidget() widget}.
   */
  public UiWidgetEditorGwt(WIDGET widget) {

    super(widget);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getLabel() {

    // TODO Auto-generated method stub
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setLabel(String label) {

    // TODO Auto-generated method stub

  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void addChangeHandler(UiHandlerEventChange<VALUE> handler) {

    if (this.changeEventSender == null) {
      this.changeEventSender = new ChangeEventSenderGwt<VALUE>(this);
      registerChangeHandler(this.changeEventSender);
    }
    this.changeEventSender.addHandler(handler);
  }

  protected abstract void registerChangeHandler(ChangeHandler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean removeChangeHandler(UiHandlerEventChange<VALUE> handler) {

    if (this.changeEventSender != null) {
      return this.changeEventSender.removeHandler(handler);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void setValue(VALUE value) {

    doSetValue(value);
    if (this.changeEventSender != null) {
      this.changeEventSender.onChange(this, true);
    }
  }

  /**
   * Called from {@link #setValue(Object)} to set the value in the {@link #getActiveWidget() active widget}.
   * 
   * @param value
   */
  protected abstract void doSetValue(VALUE value);

}
