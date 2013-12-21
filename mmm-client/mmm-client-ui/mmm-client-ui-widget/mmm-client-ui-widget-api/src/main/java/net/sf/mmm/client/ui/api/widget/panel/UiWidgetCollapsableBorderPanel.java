/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.feature.UiFeatureCollapse;
import net.sf.mmm.client.ui.api.widget.UiWidgetNative;

/**
 * This is the interface for a {@link UiWidgetAbstractBorderPanel border panel} that can be
 * {@link #setCollapsed(boolean) collapsed and expanded}.
 * 
 * @see UiWidgetAbstractBorderPanel
 * @see UiWidgetBorderPanel
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetCollapsableBorderPanel extends UiWidgetAbstractBorderPanel, UiFeatureCollapse, UiWidgetNative {

  // nothing to add...

}
