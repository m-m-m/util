/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.client.ui.api.feature.UiFeatureValue;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.client.ui.base.widget.adapter.RadioGroupIdManager;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterOptionsField;
import net.sf.mmm.client.ui.impl.gwt.handler.event.ChangeEventAdapterGwt;
import net.sf.mmm.client.ui.impl.gwt.handler.event.FocusEventAdapterGwt;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;

import com.google.gwt.event.dom.client.HasAllFocusHandlers;
import com.google.gwt.event.dom.client.HasChangeHandlers;
import com.google.gwt.event.dom.client.HasKeyPressHandlers;
import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.Focusable;
import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.RadioButton;

/**
 * This is the implementation of {@link UiWidgetAdapterOptionsField} using GWT based on {@link CellPanel} and
 * {@link RadioButton}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value.
 */
public abstract class UiWidgetAdapterGwtCellPanelRadios<VALUE> extends
    UiWidgetAdapterGwtField<CellPanel, VALUE, String> implements UiWidgetAdapterOptionsField<VALUE> {

  /** @see #setOptions(List) */
  private final List<RadioButton> radioButtons;

  /** The unique group ID for the {@link RadioButton}s. */
  private final String groupId;

  /** @see #setOptions(List) */
  private List<String> options;

  /** The {@link ChangeEventAdapterGwt} */
  private ChangeEventAdapterGwt<VALUE> changeEventAdapter;

  /** @see #setOptions(List) */
  private char accessKey;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtCellPanelRadios() {

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
  protected HasValue<String> getWidgetAsHasValue() {

    return null;
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
        if (this.accessKey != ACCESS_KEY_NONE) {
          rb.setAccessKey(this.accessKey);
        }
        first = false;
      }
      getToplevelWidget().add(rb);
      this.radioButtons.add(rb);
    }
    registerChangeEventAdapter();
    applyFocusEventAdapter();
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
  public void setChangeEventSender(UiFeatureValue<VALUE> source, UiHandlerEventValueChange<VALUE> sender) {

    if (this.changeEventAdapter != null) {
      throw new NlsIllegalStateException();
    }
    this.changeEventAdapter = new ChangeEventAdapterGwt<VALUE>(source, sender);
    registerChangeEventAdapter();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void applyFocusEventAdapter() {

    FocusEventAdapterGwt focusEventAdapter = getFocusEventAdapter();
    if (focusEventAdapter != null) {
      for (RadioButton rb : this.radioButtons) {
        rb.addFocusHandler(focusEventAdapter);
        rb.addBlurHandler(focusEventAdapter);
      }
    }
  }

  /**
   * Registers the {@link FocusEventAdapterGwt} in all {@link RadioButton}s.
   */
  private void registerChangeEventAdapter() {

    if (this.changeEventAdapter != null) {
      for (RadioButton rb : this.radioButtons) {
        rb.addClickHandler(this.changeEventAdapter);
      }
    }
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

}
