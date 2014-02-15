/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;
import net.sf.mmm.client.ui.base.widget.adapter.RadioGroupIdManager;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterRadioButtonsField;
import net.sf.mmm.client.ui.impl.gwt.handler.event.EventAdapterGwt;
import net.sf.mmm.client.ui.impl.gwt.handler.event.EventAdapterGwtClickToChange;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.RadioButton;

/**
 * This is the base implementation of {@link UiWidgetAdapterRadioButtonsField} using GWT based on a
 * {@link ComplexPanel} and {@link RadioButton}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value.
 */
public abstract class UiWidgetAdapterGwtRadioButtons<VALUE> extends
    UiWidgetAdapterGwtField<ComplexPanel, VALUE, String> implements UiWidgetAdapterRadioButtonsField<VALUE>,
    ClickHandler, TakesValue<String> {

  /** @see #setOptions(List) */
  private final List<RadioButton> radioButtons;

  /** The unique group ID for the {@link RadioButton}s. */
  private final String groupId;

  /** @see #setOptions(List) */
  private List<String> options;

  /** @see #setOptions(List) */
  private char accessKey;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtRadioButtons() {

    super();
    this.radioButtons = new ArrayList<RadioButton>();
    this.groupId = RadioGroupIdManager.getInstance().getUniqueId();
    this.accessKey = ACCESS_KEY_NONE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasChangeHandlers getWidgetAsHasChangeHandlers() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasKeyPressHandlers getWidgetAsKeyPressHandlers() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected Focusable getWidgetAsFocusable() {

    if (this.radioButtons.size() > 0) {
      return this.radioButtons.get(0);
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasAllFocusHandlers getWidgetAsHasAllFocusHandlers() {

    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TakesValue<String> getWidgetAsTakesValue() {

    return this;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TakesValue<String> getWidgetAsTakesValueString() {

    return getWidgetAsTakesValue();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getOptions() {

    return this.options;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected final ComplexPanel createActiveWidget() {

    ComplexPanel panel = doCreateActiveWidget();
    // Element element = panel.getElement();
    // element.setAttribute("tabindex", "0");
    // Roles.getRadiogroupRole().set(element);
    return panel;
  }

  /**
   * @see #createActiveWidget()
   * @return the new {@link #getActiveWidget() active widget}.
   */
  protected abstract ComplexPanel doCreateActiveWidget();

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOptions(List<String> options) {

    for (RadioButton rb : this.radioButtons) {
      rb.removeFromParent();
    }
    this.radioButtons.clear();
    this.options = options;
    boolean first = true;
    for (String title : options) {
      RadioButton rb = new RadioButton(this.groupId, title);
      if (first) {
        rb.setTabIndex(0);
        if (this.accessKey != ACCESS_KEY_NONE) {
          rb.setAccessKey(this.accessKey);
        }
        first = false;
      } else {
        rb.setTabIndex(-2);
      }
      getActiveWidget().add(rb);
      this.radioButtons.add(rb);
      rb.addClickHandler(this);
    }
    EventAdapterGwt eventAdapter = getEventAdapter();
    // applyEventAdapter(eventAdapter);
    applyEventAdapterForChange(eventAdapter);
    applyEventAdapterForFocus(eventAdapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

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
  public void setValue(String value) {

    for (RadioButton rb : this.radioButtons) {
      if (value.equals(rb.getText())) {
        rb.setValue(Boolean.TRUE, false);
        break;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected EventAdapterGwt createEventAdapter(UiFeatureEvent source, UiHandlerEvent sender) {

    return new EventAdapterGwtClickToChange(source, sender);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void applyEventAdapterForFocus(EventAdapterGwt adapter) {

    for (RadioButton rb : this.radioButtons) {
      rb.addFocusHandler(adapter);
      rb.addBlurHandler(adapter);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void applyEventAdapterForChange(EventAdapterGwt adapter) {

    for (RadioButton rb : this.radioButtons) {
      rb.addClickHandler(adapter);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void applyEventAdapterForClick(EventAdapterGwt adapter) {

    // nothing to do...
    // TODO hohwille test and remove...
    super.applyEventAdapterForClick(adapter);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEnabled(boolean enabled) {

    for (RadioButton rb : this.radioButtons) {
      rb.setEnabled(enabled);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setAccessKey(char accessKey) {

    this.accessKey = accessKey;
    if (this.radioButtons.size() > 0) {
      this.radioButtons.get(0).setAccessKey(accessKey);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onClick(ClickEvent event) {

    for (RadioButton rb : this.radioButtons) {
      int tabIndex = -1;
      if (rb.getValue().booleanValue()) {
        tabIndex = 0;
      }
      rb.setTabIndex(tabIndex);
    }
  }

}
