/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.menu;

import net.sf.mmm.ui.toolkit.api.feature.UiFeatureClick;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetReal;

/**
 * This is the interface for a <em>normal</em> {@link UiWidgetMenuItem menu item widget}. Normal means it can
 * be {@link #click() clicked} and causes an
 * {@link #addClickHandler(net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventClick) action}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetMenuItemClickable extends UiWidgetMenuItem, UiFeatureClick, UiWidgetReal {

  // nothing to add

}
