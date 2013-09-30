/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.core;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;

/**
 * This is the interface for an {@link UiWidgetRegular regular widget} that represents a
 * <em>button separator</em>. Such widget has no function but a visual representation as a (vertical) bar to
 * separate {@link UiWidgetAbstractButton buttons}, e.g. in a
 * {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetButtonGroup} as part of a
 * {@link net.sf.mmm.client.ui.api.widget.panel.UiWidgetToolbar}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetButtonSeparator extends UiWidgetRegular, UiWidgetNative {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String STYLE_PRIMARY = CssStyles.BUTTON_SEPARATOR;

}
