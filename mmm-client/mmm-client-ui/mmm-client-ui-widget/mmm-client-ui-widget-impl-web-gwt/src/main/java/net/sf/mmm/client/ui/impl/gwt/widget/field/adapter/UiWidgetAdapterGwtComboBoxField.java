/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.List;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterComboboxField;
import net.sf.mmm.client.ui.gwt.widgets.ComboBox;
import net.sf.mmm.client.ui.gwt.widgets.DataList;

import com.google.gwt.user.client.TakesValue;
import com.google.gwt.user.client.ui.ComplexPanel;
import com.google.gwt.user.client.ui.HasValue;

/**
 * This is the implementation of {@link UiWidgetAdapterComboboxField} using GWT based on a {@link ComboBox}
 * and a {@link DataList}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <VALUE> is the generic type of the changed value.
 */
public class UiWidgetAdapterGwtComboBoxField<VALUE> extends
    UiWidgetAdapterGwtFieldFocusWidgetBase<ComboBox, VALUE, String> implements UiWidgetAdapterComboboxField<VALUE> {

  /** @see #createActiveWidget() */
  private DataList dataList;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtComboBoxField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ComboBox createActiveWidget() {

    ComboBox combo = new ComboBox();
    combo.setDataList(getDataList());
    return combo;
  }

  /**
   * @return the {@link DataList} with the options.
   */
  protected DataList getDataList() {

    if (this.dataList == null) {
      this.dataList = new DataList();
    }
    return this.dataList;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void attachActiveWidget(ComplexPanel panel) {

    super.attachActiveWidget(panel);
    panel.add(this.dataList);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<String> getOptions() {

    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOptions(List<String> options) {

    getDataList().setOptions(options);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected HasValue<String> getWidgetAsTakesValue() {

    return getActiveWidget();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected TakesValue<String> getWidgetAsTakesValueString() {

    return getWidgetAsTakesValue();
  }
}
