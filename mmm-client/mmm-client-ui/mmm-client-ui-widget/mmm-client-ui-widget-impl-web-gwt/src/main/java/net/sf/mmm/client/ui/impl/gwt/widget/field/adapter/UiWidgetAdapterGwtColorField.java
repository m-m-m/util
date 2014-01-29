/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterColorField;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.ColorBox;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.DataList;
import net.sf.mmm.util.datatype.api.color.Color;

import com.google.gwt.user.client.ui.ComplexPanel;

/**
 * This is the implementation of {@link UiWidgetAdapterColorField} using GWT based on {@link ColorBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtColorField extends UiWidgetAdapterGwtFieldValueBoxBase<ColorBox, Color, Color> implements
    UiWidgetAdapterColorField {

  /** @see #getDataList() */
  private DataList dataList;

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtColorField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected ColorBox createActiveWidget() {

    ColorBox colorBox = new ColorBox();
    colorBox.setDataList(getDataList());
    return colorBox;
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
    panel.add(getDataList());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public List<Color> getOptions() {

    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setOptions(List<Color> options) {

    if (isViewOnly()) {
      return;
    }
    List<String> stringOptions = new ArrayList<String>(options.size());
    for (Color color : options) {
      stringOptions.add(color.toString());
    }
    getDataList().setOptions(stringOptions);
  }
}
