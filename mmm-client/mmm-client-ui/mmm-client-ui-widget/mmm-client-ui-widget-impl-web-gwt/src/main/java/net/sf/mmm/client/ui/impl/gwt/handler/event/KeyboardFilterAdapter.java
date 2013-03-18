/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.handler.event;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteKeyboardFilter;
import net.sf.mmm.util.filter.api.CharFilter;

import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.shared.HandlerRegistration;

/**
 * This is the implementation of a {@link AttributeWriteKeyboardFilter#getKeyboardFilter() keyboard-filter}
 * for GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class KeyboardFilterAdapter implements KeyPressHandler, AttributeWriteKeyboardFilter {

  /** @see #getKeyboardFilter() */
  private CharFilter keyboardFilter;

  /** @see #remove() */
  private HandlerRegistration handlerRegistration;

  /**
   * The constructor.
   */
  public KeyboardFilterAdapter() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onKeyPress(KeyPressEvent event) {

    if (this.keyboardFilter == null) {
      return;
    }
    char key = event.getCharCode();
    if (isControlKey(key)) {
      // never cancel control keys...
      return;
    }
    if (!this.keyboardFilter.accept(key)) {
      event.getNativeEvent().stopPropagation();
      event.getNativeEvent().preventDefault();
    }
  }

  /**
   * This method determines if the given <code>key</code> is a control key (backspace, return, tab, arrow
   * up/down/left/right, escape, etc.).
   * 
   * @param key is the character of the key that has been pressed.
   * @return <code>true</code> if the given <code>key</code> is a control key, <code>false</code> otherwise.
   */
  private boolean isControlKey(char key) {

    return (key < ' ');
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public CharFilter getKeyboardFilter() {

    return this.keyboardFilter;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setKeyboardFilter(CharFilter keyboardFilter) {

    this.keyboardFilter = keyboardFilter;
  }

  /**
   * This method adds this keyboard-filter to the given <code>widget</code>.
   * 
   * @param widget is the widget where to add this filter.
   */
  public void add(HasKeyPressHandlers widget) {

    assert (this.handlerRegistration == null);
    this.handlerRegistration = widget.addKeyPressHandler(this);
  }

  /**
   * Removes this keyboard-filter.
   */
  public void remove() {

    assert (this.handlerRegistration != null);
    if (this.handlerRegistration != null) {
      this.handlerRegistration.removeHandler();
      this.handlerRegistration = null;
    }
    this.keyboardFilter = null;
  }

  /**
   * @return <code>true</code> if never {@link #add(HasKeyPressHandlers) added} or {@link #remove() removed}.
   */
  public boolean isRemoved() {

    return (this.handlerRegistration == null);
  }

}
