/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field.adapter;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureFocus;
import net.sf.mmm.ui.toolkit.api.feature.UiFeatureValue;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventFocus;
import net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventValueChange;
import net.sf.mmm.ui.toolkit.base.widget.adapter.RadioGroupIdManager;
import net.sf.mmm.ui.toolkit.base.widget.field.adapter.UiWidgetAdapterOptionsField;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.ChangeEventAdapterGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.FocusEventAdapterGwt;
import net.sf.mmm.ui.toolkit.impl.gwt.widget.adapter.UiWidgetAdapterGwtWidget;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;

import com.google.gwt.user.client.ui.CellPanel;
import com.google.gwt.user.client.ui.RadioButton;

/**
 * This is the implementation of {@link UiWidgetAdapterOptionsField} using GWT based on {@link CellPanel} and
 * {@link RadioButton}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value.
 */
public abstract class UiWidgetAdapterGwtCellPanelRadios<VALUE> extends UiWidgetAdapterGwtWidget<CellPanel> implements
    UiWidgetAdapterOptionsField<CellPanel, VALUE> {

  /** @see #setOptions(List) */
  private final List<RadioButton> radioButtons;

  /** @see #setOptions(List) */
  private List<String> options;

  /** The {@link FocusEventAdapterGwt} */
  private FocusEventAdapterGwt focusEventAdapter;

  /** The {@link ChangeEventAdapterGwt} */
  private ChangeEventAdapterGwt<VALUE> changeEventAdapter;

  /** The unique group ID for the {@link RadioButton}s. */
  private final String groupId;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtCellPanelRadios() {

    super();
    this.radioButtons = new ArrayList<RadioButton>();
    this.groupId = RadioGroupIdManager.getInstance().getUniqueId();
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
    for (String title : options) {
      RadioButton rb = new RadioButton(this.groupId, title);
      getWidget().add(rb);
      this.radioButtons.add(rb);
    }
    registerChangeEventAdapter();
    registerFocusEventAdapter();
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
  public void setFocused(boolean focused) {

    if (this.radioButtons.size() > 0) {
      if (this.focusEventAdapter != null) {
        this.focusEventAdapter.setProgrammatic();
      }
      if (!this.radioButtons.isEmpty()) {
        this.radioButtons.get(0).setFocus(focused);
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
  public void setFocusEventSender(UiFeatureFocus source, UiHandlerEventFocus sender) {

    if (this.focusEventAdapter != null) {
      throw new NlsIllegalStateException();
    }
    this.focusEventAdapter = new FocusEventAdapterGwt(source, sender);
    registerFocusEventAdapter();
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
   * Registers the {@link FocusEventAdapterGwt} in all {@link RadioButton}s.
   */
  private void registerFocusEventAdapter() {

    if (this.focusEventAdapter != null) {
      for (RadioButton rb : this.radioButtons) {
        rb.addFocusHandler(this.focusEventAdapter);
        rb.addBlurHandler(this.focusEventAdapter);
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isFocused() {

    // dummy, never called
    return false;
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

}
