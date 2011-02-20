/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

import java.util.Date;

import net.sf.mmm.ui.toolkit.api.attribute.UiWriteValue;

/**
 * This is the interface of a time box. It is a widget where the user can
 * specify a time in a comfortable way.<br/>
 * The widget itself should be lightweight and require only little space to fit
 * into a single line (e.g. a text-field with a button beside). If more space is
 * required, a pull-down, pop-up, or dialog may be opened via a button.<br/>
 * A {@link Date} is used as {@link #getValue() value} for a convenience
 * container type. A plain time-box will ignore the year, month and day
 * attributes.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiTimeBox extends UiWidget, UiWriteValue<Date> {
  //
  // int getHour();
  //
  // int getMinute();
  //
  // int getSecond();
  //
  // void setTime(int hour, int minute, int second);

}
