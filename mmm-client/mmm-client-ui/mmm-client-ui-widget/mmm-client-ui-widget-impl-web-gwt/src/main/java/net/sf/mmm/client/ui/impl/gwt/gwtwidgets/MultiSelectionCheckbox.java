/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.logical.shared.ValueChangeEvent;
import com.google.gwt.event.logical.shared.ValueChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.Widget;

/**
 * This is a very simple checkbox for {@link net.sf.mmm.client.ui.api.common.SelectionMode#MULTIPLE_SELECTION
 * multi-selection}. Unlike {@link com.google.gwt.user.client.ui.CheckBox} it has no label and cannot directly
 * gain focus.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class MultiSelectionCheckbox extends Widget implements HasValue<Boolean>, HasChangeHandlers {

  /** The HTML input element. */
  private final InputElement inputElement;

  /** @see #addValueChangeHandler(ValueChangeHandler) */
  private HandlerRegistration changeHandlerRegistration;

  /**
   * The constructor.
   */
  public MultiSelectionCheckbox() {

    super();
    this.inputElement = InputElement.as(DOM.createInputCheck());
    this.inputElement.setTabIndex(-1);
    setElement(this.inputElement);
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
      addClickHandler(new ClickHandler() {

        @Override
        public void onClick(ClickEvent event) {

          ValueChangeEvent.fire(MultiSelectionCheckbox.this, getValue());
        }
      });
      this.changeHandlerRegistration = addChangeHandler(new ChangeHandler() {

        @Override
        public void onChange(ChangeEvent event) {

          ValueChangeEvent.fire(MultiSelectionCheckbox.this, getValue());
        }
      });
    }
    return addHandler(handler, ValueChangeEvent.getType());
  }

  /**
   * @see com.google.gwt.user.client.ui.CheckBox#addClickHandler(ClickHandler)
   * 
   * @param handler is the {@link ClickHandler}.
   * @return the {@link HandlerRegistration}.
   */
  public HandlerRegistration addClickHandler(ClickHandler handler) {

    return addDomHandler(handler, ClickEvent.getType());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Boolean getValue() {

    return Boolean.valueOf(this.inputElement.isChecked());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(Boolean checked) {

    setValue(checked, false);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(Boolean checked, boolean fireEvents) {

    boolean fire = fireEvents && (checked.booleanValue() != this.inputElement.isChecked());
    this.inputElement.setChecked(checked.booleanValue());
    // this.inputElement.setDefaultChecked(checked.booleanValue());
    if (fire) {
      ValueChangeEvent.fire(this, checked);
    }
  }
}
