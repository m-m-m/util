/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.gwtwidgets;

/**
 * This type contains general constants related to Google Web Toolkit (GWT).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface HtmlConstants {

  /**
   * The value for {@link com.google.gwt.user.client.ui.FocusWidget#setTabIndex(int) tabIndex} to remove from
   * tab-order. See <a href="http://code.google.com/p/google-web-toolkit/issues/detail?id=8323">GWT Bug
   * 8323</a>.
   */
  int TAB_INDEX_DISABLE = -2;

  /**
   * The {@link com.google.gwt.dom.client.InputElement#getType() type} of the
   * {@link com.google.gwt.dom.client.InputElement} for a range slider.
   */
  String INPUT_TYPE_RANGE = "range";

  /**
   * The {@link com.google.gwt.dom.client.InputElement#getType() type} of the
   * {@link com.google.gwt.dom.client.InputElement} for a number spin-box.
   */
  String INPUT_TYPE_NUMBER = "number";

  /**
   * The {@link com.google.gwt.dom.client.InputElement#getType() type} of the
   * {@link com.google.gwt.dom.client.InputElement} for a color.
   */
  String INPUT_TYPE_COLOR = "color";

  /**
   * The {@link com.google.gwt.dom.client.InputElement#getType() type} of the
   * {@link com.google.gwt.dom.client.InputElement} for a date.
   */
  String INPUT_TYPE_DATE = "date";

  /**
   * The {@link com.google.gwt.dom.client.InputElement#getType() type} of the
   * {@link com.google.gwt.dom.client.InputElement} for a time.
   */
  String INPUT_TYPE_TIME = "time";

  /**
   * The {@link com.google.gwt.dom.client.InputElement#getType() type} of the
   * {@link com.google.gwt.dom.client.InputElement} for a date with time.
   */
  String INPUT_TYPE_DATETIME = "datetime";

  /**
   * The {@link com.google.gwt.dom.client.Document#createElement(String) tag name} of a {@link DataList}.
   */
  String TAG_DATALIST = "datalist";

  /**
   * The {@link com.google.gwt.dom.client.Element#setAttribute(String, String) attribute} of an input element
   * to reference a datalist via its id.
   */
  String ATTRIBUTE_LIST = "list";

}
