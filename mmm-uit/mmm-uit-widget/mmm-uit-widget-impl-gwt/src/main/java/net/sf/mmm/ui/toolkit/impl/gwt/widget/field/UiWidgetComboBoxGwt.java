/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.impl.gwt.widget.field;

import java.util.List;

import net.sf.mmm.ui.toolkit.api.widget.field.UiWidgetComboBox;
import net.sf.mmm.ui.toolkit.base.widget.AbstractUiSingleWidgetFactory;
import net.sf.mmm.ui.toolkit.impl.gwt.handler.event.FocusEventSenderGwt;

import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.FocusHandler;
import com.google.gwt.user.client.ui.ListBox;

/**
 * This is a simple implementation of {@link UiWidgetComboBox} using GWT based on {@link ListBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the {@link #getValue() value}.
 */
public class UiWidgetComboBoxGwt<VALUE> extends UiWidgetOptionsFieldGwt<VALUE, ListBox> implements
    UiWidgetComboBox<VALUE> {

  /** @see #doSetOptions(List) */
  private List<String> optionTitles;

  /**
   * The constructor.
   */
  public UiWidgetComboBoxGwt() {

    super(new ListBox());
    getWidget().setVisibleItemCount(1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setFocused(boolean focused) {

    FocusEventSenderGwt sender = getFocusEventSender();
    if (sender != null) {
      sender.setProgrammatic();
    }
    getWidget().setFocus(focused);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetOptions(List<String> titles) {

    getWidget().clear();
    this.optionTitles = titles;
    for (String item : titles) {
      getWidget().addItem(item);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected String getValueAsString() {

    if (this.optionTitles != null) {
      int selectedIndex = getWidget().getSelectedIndex();
      if ((selectedIndex >= 0) && (selectedIndex < this.optionTitles.size())) {
        return this.optionTitles.get(selectedIndex);
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void setValueAsString(String value) {

    if (this.optionTitles == null) {
      return;
    }
    for (int i = 0; i < this.optionTitles.size(); i++) {
      if (value.equals(this.optionTitles.get(i))) {
        getWidget().setSelectedIndex(i);
        break;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void registerChangeHandler(ChangeHandler handler) {

    getWidget().addChangeHandler(handler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void registerFocusHandler(FocusHandler focusHandler, BlurHandler blurHandler) {

    getWidget().addFocusHandler(focusHandler);
    getWidget().addBlurHandler(blurHandler);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doSetEnabled(boolean newEnabled) {

    getWidget().setEnabled(newEnabled);
  }

  /**
   * This inner class is the {@link AbstractUiSingleWidgetFactory factory} for this widget.
   */
  @SuppressWarnings("rawtypes")
  public static class Factory extends AbstractUiSingleWidgetFactory<UiWidgetComboBox> {

    /**
     * The constructor.
     */
    public Factory() {

      super(UiWidgetComboBox.class);
    }

    /**
     * {@inheritDoc}
     */
    public UiWidgetComboBox create() {

      return new UiWidgetComboBoxGwt();
    }

  }

}
