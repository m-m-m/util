/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.widget.UiWidgetNative;
import net.sf.mmm.client.ui.api.widget.core.UiWidgetButton;

/**
 * This is the interface for a lightweight {@link UiWidgetDynamicPanel dynamic panel} that groups a number of
 * {@link UiWidgetButton buttons} that have a related functionality (e.g. "Undo", "Redo"). These buttons
 * appear with less (or inside {@link UiWidgetToolbar} without) horizontal margin between each other (more
 * narrow).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetButtonGroup extends UiWidgetDynamicPanel<UiWidgetButton>, UiWidgetNative {

  /** The default {@link #getPrimaryStyle() primary style} of this widget. */
  String STYLE_PRIMARY = "ButtonGroup";

}
