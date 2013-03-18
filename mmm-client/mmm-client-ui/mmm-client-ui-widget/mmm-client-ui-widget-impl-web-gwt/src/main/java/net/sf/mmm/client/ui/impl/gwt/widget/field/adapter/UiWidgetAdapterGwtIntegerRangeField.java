/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.widget.field.adapter;

import net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterIntegerRangeField;
import net.sf.mmm.util.gwt.api.JavaScriptUtil;

import com.google.gwt.text.client.IntegerParser;
import com.google.gwt.text.client.IntegerRenderer;
import com.google.gwt.user.client.ui.IntegerBox;
import com.google.gwt.user.client.ui.ValueBox;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.base.widget.field.adapter.UiWidgetAdapterField}
 * using GWT based on {@link IntegerRangeBox}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiWidgetAdapterGwtIntegerRangeField extends UiWidgetAdapterGwtFieldValueBox<IntegerBox, Integer, Integer>
    implements UiWidgetAdapterIntegerRangeField {

  /**
   * The constructor.
   */
  public UiWidgetAdapterGwtIntegerRangeField() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected IntegerBox createActiveWidget() {

    return new IntegerBox();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMinimumValue(Integer minimum) {

    getToplevelWidget().getElement().setAttribute("min", minimum.toString());
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setMaximumValue(Integer maximum) {

    getToplevelWidget().getElement().setAttribute("max", maximum.toString());
  }

  /**
   * This inner class is a custom {@link com.google.gwt.user.client.ui.Widget} that represents a
   * {@link IntegerRangeBox} (input of type range).
   */
  protected static class IntegerRangeBox extends ValueBox<Integer> {

    /**
     * The constructor.
     */
    public IntegerRangeBox() {

      super(JavaScriptUtil.getInstance().createInputElement("range"), IntegerRenderer.instance(), IntegerParser
          .instance());
    }

  }

}
