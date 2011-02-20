/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view.widget;

/**
 * This is the interface for a date and time box. It is a widget that allows the
 * user to specify a date and time in a comfortable way. By default the timezone
 * should be set to the users locale. An advanced implementation will allow the
 * user to specify the timezone as well.<br/>
 * The widget itself should be lightweight and require only little space to fit
 * into a single line (e.g. a text-field with a button beside). If more space is
 * required, a pull-down, pop-up, or dialog may be opened via a button.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiDateTimeBox extends UiDateBox, UiTimeBox {

  // String getTimezone();
  //
  // void setTimezone(String timezone);

}
