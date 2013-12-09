/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.client.ui.api.color.Color;
import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterColorField;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.ColorBox;
import net.sf.mmm.client.ui.impl.gwt.gwtwidgets.DataList;

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

    return new ColorBox();
  }

  /**
   * @return the {@link DataList} with the options.
   */
  protected DataList getDataList() {

    if (this.dataList == null) {
      this.dataList = new DataList();
      getActiveWidget().setDataList(this.dataList);
      getToplevelWidget().add(this.dataList);
    }
    return this.dataList;
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

    List<String> stringOptions = new ArrayList<String>(options.size());
    for (Color color : options) {
      stringOptions.add(color.toString());
    }
    getDataList().setOptions(stringOptions);
  }
}
