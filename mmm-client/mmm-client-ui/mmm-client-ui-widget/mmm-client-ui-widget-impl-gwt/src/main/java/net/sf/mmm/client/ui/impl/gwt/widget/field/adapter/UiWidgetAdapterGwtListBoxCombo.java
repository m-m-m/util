/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.List;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterOptionsField;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.ListBox;

/**
 * This is the implementation of {@link UiWidgetAdapterOptionsField} using GWT based on {@link ListBox} to
 * build a simple combobox.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value.
 */
public class UiWidgetAdapterGwtListBoxCombo<VALUE> extends
    UiWidgetAdapterGwtFieldFocusWidgetBase<ListBox, VALUE, String> implements UiWidgetAdapterOptionsField<VALUE> {

  /** @see #setOptions(List) */
  private List<String> options;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtListBoxCombo() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ListBox createActiveWidget() {

    ListBox listBox = new ListBox();
    listBox.setVisibleItemCount(1);
    return listBox;
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

    getToplevelWidget().clear();
    this.options = options;
    for (String item : options) {
      getActiveWidget().addItem(item);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    if (this.options != null) {
      int selectedIndex = getActiveWidget().getSelectedIndex();
      if ((selectedIndex >= 0) && (selectedIndex < this.options.size())) {
        return this.options.get(selectedIndex);
      }
    }
    return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(String value) {

    if (this.options == null) {
      return;
    }
    for (int i = 0; i < this.options.size(); i++) {
      if (value.equals(this.options.get(i))) {
        getActiveWidget().setSelectedIndex(i);
        break;
      }
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasValue<String> getWidgetAsHasValue() {

    throw new NlsIllegalStateException();
  }
}
