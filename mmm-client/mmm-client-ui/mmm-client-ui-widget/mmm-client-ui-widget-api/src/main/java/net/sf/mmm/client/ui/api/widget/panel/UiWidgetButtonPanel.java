/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.common.CssStyles;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetAbstractButtonContainer button container} that shows a number of
 * {@link net.sf.mmm.client.ui.api.widget.core.UiWidgetButton buttons} horizontally (e.g. "Save", "Reset",
 * "Cancel"). It shall be used instead of a {@link UiWidgetHorizontalPanel} to get control of the layout
 * (margins) for button panels. For additional features please see {@link UiWidgetAbstractButtonContainer}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetButtonPanel extends UiWidgetAbstractButtonContainer, UiWidgetNative {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String STYLE_BUTTON_PANEL = CssStyles.BUTTON_PANEL;

}
