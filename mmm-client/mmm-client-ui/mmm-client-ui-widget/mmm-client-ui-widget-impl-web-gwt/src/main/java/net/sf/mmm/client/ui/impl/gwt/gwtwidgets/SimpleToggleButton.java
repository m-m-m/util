/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import net.sf.mmm.client.ui.api.widget.core.UiWidgetToggleButton;

import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.HasValue;

/**
 * This is a simple GWT toggle button. Unlike {@link com.google.gwt.user.client.ui.ToggleButton} it does not
 * contain internal hacks such as a text input. It is completely styled with CSS and needs no temporary hover
 * styles or different down/up icons.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SimpleToggleButton extends Button implements HasValue<Boolean>, HasChangeHandlers {

  /** @see #addValueChangeHandler(ValueChangeHandler) */
  private HandlerRegistration changeHandlerRegistration;

  /** @see #getValue() */
  private boolean value;

  /**
   * The constructor.
   */
  public SimpleToggleButton() {

    super();
    addClickHandler(new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        setValue(Boolean.valueOf(!SimpleToggleButton.this.value), true);
      }
    });
  }

  /**
   * The constructor.
   * 
   * @param text is the {@link #setText(String) text to set}.
   */
  public SimpleToggleButton(String text) {

    this();
    setText(text);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HandlerRegistration addChangeHandler(ChangeHandler handler) {

    return addDomHandler(handler, ChangeEvent.getType());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {

    if (this.changeHandlerRegistration == null) {
      this.changeHandlerRegistration = addChangeHandler(new ChangeHandler() {

        @Override
        public void onChange(ChangeEvent event) {

          ValueChangeEvent.fire(SimpleToggleButton.this, getValue());
        }
      });
    }
    return addHandler(handler, ValueChangeEvent.getType());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getValue() {

    return Boolean.valueOf(this.value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(Boolean value) {

    setValue(value, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(Boolean newValue, boolean fireEvents) {

    if (newValue.booleanValue() == this.value) {
      return;
    }
    this.value = newValue.booleanValue();
    if (this.value) {
      addStyleName(UiWidgetToggleButton.STYLE_PRESSED);
    } else {
      removeStyleName(UiWidgetToggleButton.STYLE_PRESSED);
    }
    if (fireEvents) {
      ValueChangeEvent.fire(this, newValue);
    }
  }

}
