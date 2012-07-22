/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetField;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.ChangeEventSenderGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.FocusEventSenderGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.UiWidgetRegularAtomicGwt;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is the implementation of {@link UiWidgetField} using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetFieldGwt<VALUE, WIDGET extends Widget> extends UiWidgetRegularAtomicGwt<WIDGET> implements
    UiWidgetField<VALUE> {

  /** @see #addChangeHandler(UiHandlerEventValueChange) */
  private ChangeEventSenderGwt<VALUE> changeEventSender;

  /** @see #addFocusHandler(UiHandlerEventFocus) */
  private FocusEventSenderGwt focusEventSender;

  /**
   * The constructor.
   */
  public UiWidgetFieldGwt() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

    if (this.changeEventSender == null) {
      this.changeEventSender = new ChangeEventSenderGwt<VALUE>(this);
      registerChangeHandler(this.changeEventSender);
    }
    this.changeEventSender.addHandler(handler);
  }

  /**
   * @return the {@link ChangeEventSenderGwt} or <code>null</code> if NOT yet created.
   */
  protected final ChangeEventSenderGwt<VALUE> getChangeEventSender() {

    return this.changeEventSender;
  }

  /**
   * This method has to be implemented to register the given {@link ChangeHandler} in the {@link #getWidget()
   * active widget}.
   * 
   * @param handler is the {@link ChangeHandler}.
   */
  protected abstract void registerChangeHandler(ChangeHandler handler);

  /**
   * {@inheritDoc}
   */
  @Override
  public final boolean removeChangeHandler(UiHandlerEventValueChange<VALUE> handler) {

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
      this.changeEventSender.onValueChange(this, true);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public final VALUE getValue() {

    try {
      return getValueOrException();
    } catch (RuntimeException e) {
      // ATTENTION: This is one of the very rare cases where we intentionally ignore an exception.
      return null;
    }
  }

  /**
   * Called from {@link #setValue(Object)} to set the value in the {@link #getWidget() active widget}.
   * 
   * @param value is the new value to set.
   */
  protected abstract void doSetValue(VALUE value);

  /**
   * {@inheritDoc}
   */
  @Override
  public final void addFocusHandler(UiHandlerEventFocus handler) {

    if (this.focusEventSender == null) {
      this.focusEventSender = new FocusEventSenderGwt(this);
      registerFocusHandler(this.focusEventSender, this.focusEventSender);
    }
    this.focusEventSender.addHandler(handler);
  }

  /**
   * @return the {@link FocusEventSenderGwt} or <code>null</code> if not yet created.
   */
  protected final FocusEventSenderGwt getFocusEventSender() {

    return this.focusEventSender;
  }

  /**
   * This method has to be implemented to register the given {@link FocusHandler} and {@link BlurHandler} in
   * the {@link #getWidget() active widget}.
   * 
   * @param focusHandler is the {@link FocusHandler}.
   * @param blurHandler is the {@link BlurHandler}.
   */
  protected abstract void registerFocusHandler(FocusHandler focusHandler, BlurHandler blurHandler);

  /**
   * {@inheritDoc}
   */
  public final boolean removeFocusHandler(UiHandlerEventFocus handler) {

    if (this.focusEventSender != null) {
      return this.focusEventSender.removeHandler(handler);
    }
    return false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFocused() {

    if (this.focusEventSender != null) {
      return this.focusEventSender.isFocused();
    }
    return false;
  }

}
