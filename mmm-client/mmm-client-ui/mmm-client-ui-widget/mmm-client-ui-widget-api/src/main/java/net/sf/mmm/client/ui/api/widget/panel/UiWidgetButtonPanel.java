/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;

/**
 * This is the interface for a {@link UiWidgetDynamicPanel dynamic panel} that shows a number of
 * {@link UiWidgetButton buttons} horizontally (e.g. "Save", "Reset", "Cancel"). It shall be used instead of a
 * {@link UiWidgetHorizontalPanel} to get control of the layout (margins) for button panels.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetButtonPanel extends UiWidgetDynamicPanel<UiWidgetButton>, UiWidgetNative {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String STYLE_PRIMARY = "ButtonPanel";

}
