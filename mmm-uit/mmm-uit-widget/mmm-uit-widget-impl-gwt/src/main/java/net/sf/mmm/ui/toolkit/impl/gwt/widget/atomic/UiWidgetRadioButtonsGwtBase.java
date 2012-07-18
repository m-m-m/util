/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.atomic;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.FocusEventSenderGwt;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.RadioButton;

/**
 * This is the implementation of {@link UiWidgetOptionsFieldGwt} based on {@link RadioButton}s using GWT.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 * @param <WIDGET> is the generic type of {@link #getWidget()}.
 */
public abstract class UiWidgetRadioButtonsGwtBase<VALUE, WIDGET extends CellPanel> extends
    UiWidgetOptionsFieldGwt<VALUE, WIDGET> {

  /** @see #doSetOptions(List) */
  private final List<RadioButton> radioButtons;

  /** The unique group ID for the {@link RadioButton}s. */
  private final String groupId;

  /** A counter to ensure unique {@link #groupId}. */
  private static int groupIdCounter;

  /**
   * The constructor.
   * 
   * @param widget is the {@link #getWidget() widget}.
   */
  public UiWidgetRadioButtonsGwtBase(WIDGET widget) {

    super(widget);
    this.radioButtons = new ArrayList<RadioButton>();
    this.groupId = "group" + groupIdCounter++;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocused(boolean focused) {

    if (this.radioButtons.size() > 0) {
      FocusEventSenderGwt sender = getFocusEventSender();
      if (sender != null) {
        sender.setProgrammatic();
      }
      this.radioButtons.get(0).setFocus(focused);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetOptions(List<String> titles) {

    for (RadioButton rb : this.radioButtons) {
      rb.removeFromParent();
    }
    this.radioButtons.clear();
    for (String title : titles) {
      RadioButton rb = new RadioButton(this.groupId, title);
      getWidget().add(rb);
      this.radioButtons.add(rb);
    }
    ChangeHandler changeHandler = getChangeEventSender();
    if (changeHandler != null) {
      registerChangeHandler(changeHandler);
    }
    FocusEventSenderGwt focusEventSender = getFocusEventSender();
    if (focusEventSender != null) {
      registerFocusHandler(focusEventSender, focusEventSender);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getValueAsString() {

    for (RadioButton rb : this.radioButtons) {
      if (Boolean.TRUE.equals(rb.getValue())) {
        return rb.getText();
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setValueAsString(String value) {

    for (RadioButton rb : this.radioButtons) {
      if (value.equals(rb.getText())) {
        rb.setValue(Boolean.TRUE, false);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void registerChangeHandler(final ChangeHandler handler) {

    if (this.radioButtons.size() == 0) {
      return;
    }
    ClickHandler clickHandler = new ClickHandler() {

      @Override
      public void onClick(ClickEvent event) {

        handler.onChange(null);
      }
    };
    for (RadioButton rb : this.radioButtons) {
      rb.addClickHandler(clickHandler);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void registerFocusHandler(FocusHandler focusHandler, BlurHandler blurHandler) {

    for (RadioButton rb : this.radioButtons) {
      rb.addFocusHandler(focusHandler);
      rb.addBlurHandler(blurHandler);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean newEnabled) {

    for (RadioButton rb : this.radioButtons) {
      rb.setEnabled(newEnabled);
    }
  }

}
