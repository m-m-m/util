/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.widget.UiWidgetReal;
import net.sf.mmm.client.ui.api.widget.UiWidgetRegular;
import net.sf.mmm.client.ui.api.widget.UiWidgetSwitchComposite;

/**
 * This is the interface for a {@link UiWidgetDynamicPanel dynamic panel} that only
 * {@link #showChild(UiWidgetRegular) shows} one of its {@link #getChild(int) children} at a time. All other
 * {@link #getChild(int) children} are hidden. Unlike {@link UiWidgetTabPanel}
 * {@link #showChild(UiWidgetRegular) switching the visible child} can NOT be performed by the end-user but
 * only be done programatically.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetSwitchPanel extends UiWidgetDynamicPanel<UiWidgetRegular>,
    UiWidgetSwitchComposite<UiWidgetRegular>, UiWidgetReal {

  // nothing to add

}
