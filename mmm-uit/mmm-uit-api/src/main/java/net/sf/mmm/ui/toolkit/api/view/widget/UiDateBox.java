/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import java.util.Date;

import net.sf.mmm.ui.toolkit.api.attribute.UiWriteLocale;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteValue;

/**
 * This is the interface of a date box. It is a widget where the user can
 * specify a date (without time) in a comfortable way.<br>
 * The widget itself should be lightweight and require only little space to fit
 * into a single line (e.g. a text-field with a button beside). If more space is
 * required, a pull-down, pop-up, or dialog may be opened via a button.
 * 
 * @see UiTimeBox
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiDateBox extends UiWidget, UiWriteLocale, UiWriteValue<Date> {

  /** the type of this object */
  String TYPE = "DateEditor";

  /**
   * This method sets the date value of this editor.
   * 
   * @param newDate is the new date value.
   */
  void setValue(Date newDate);

  /**
   * This method gets the currently selected date value of this editor.
   * 
   * @return the date value.
   */
  Date getValue();

}
