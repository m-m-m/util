/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.adapter;

import com.google.gwt.dom.client.InputElement;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
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
public class MultiSelectionCheckbox extends Widget implements HasValue<Boolean> {

  /** The HTML input element. */
  private final InputElement inputElement;

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
  public HandlerRegistration addValueChangeHandler(ValueChangeHandler<Boolean> handler) {

    // TODO Auto-generated method stub
    return null;
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

    this.inputElement.setChecked(checked.booleanValue());
    // this.inputElement.setDefaultChecked(checked.booleanValue());
  }

}
