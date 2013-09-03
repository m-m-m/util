/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.core;

import net.sf.mmm.client.ui.api.widget.UiWidgetClickable;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;

/**
 * This is the abstract interface for an {@link UiWidgetRegular regular widget} that represents a
 * <em>button</em>.
 * 
 * @see UiWidgetButton
 * @see UiWidgetToggleButton
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetAbstractButton extends UiWidgetRegular, UiWidgetClickable {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String STYLE_PRIMARY = "Button";

}
