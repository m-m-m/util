/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.panel;

import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegular;

/**
 * This is the interface for a {@link UiWidgetDynamicPanel dynamic panel} that shows splitter-bars between
 * each of its {@link #getChild(int) children} and allows the user to slide these splitter-bars to dynamically
 * adjust the size of the {@link #getChild(int) children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetSplitPanel extends UiWidgetDynamicPanel<UiWidgetRegular> {

  // nothing to add

}
