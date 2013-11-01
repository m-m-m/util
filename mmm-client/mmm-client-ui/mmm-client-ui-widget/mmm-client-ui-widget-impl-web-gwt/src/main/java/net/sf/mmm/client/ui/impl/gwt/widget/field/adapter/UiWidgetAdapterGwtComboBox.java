/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.List;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterComboboxField;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.ComboBox;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.DataList;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;

import com.google.gwt.user.client.ui.HasValue;
import com.google.gwt.user.client.ui.TextBox;

/**
 * This is the implementation of {@link UiWidgetAdapterComboboxField} using GWT based on a {@link TextBox} and
 * a {@link Datalist}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value.
 */
public class UiWidgetAdapterGwtComboBox<VALUE> extends UiWidgetAdapterGwtFieldFocusWidgetBase<ComboBox, VALUE, String>
    implements UiWidgetAdapterComboboxField<VALUE> {

  /** @see #createActiveWidget() */
  private DataList datalist;

  /** @see #setOptions(List) */
  private List<String> options;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtComboBox() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ComboBox createActiveWidget() {

    ComboBox combo = new ComboBox();
    this.datalist = new DataList();
    if (this.options != null) {
      this.datalist.setOptions(this.options);
    }
    combo.setDataList(this.datalist);
    return combo;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void attachActiveWidget() {

    super.attachActiveWidget();
    getToplevelWidget().add(this.datalist);
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

    this.options = options;
    if (this.datalist != null) {
      this.datalist.setOptions(options);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String getValue() {

    return getActiveWidget().getText();
    // if (this.options != null) {
    // int selectedIndex = getActiveWidget().getSelectedIndex();
    // if ((selectedIndex >= 0) && (selectedIndex < this.options.size())) {
    // return this.options.get(selectedIndex);
    // }
    // }
    // return null;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setValue(String value) {

    getActiveWidget().setText(value);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasValue<String> getWidgetAsTakesValue() {

    throw new NlsIllegalStateException();
  }
}
