/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.window;

import net.sf.mmm.client.ui.api.attribute.AttributeWriteResizable;
import net.sf.mmm.client.ui.api.widget.UiWidgetReal;

/**
 * This is the interface for a {@link UiWidgetBaseWindow base window widget} that represents a <em>popup</em>
 * window. A popup is a modal window that blocks the client application until it is closed.<br/>
 * <b>ATTENTION:</b><br/>
 * The {@link #setResizable(boolean)} feature may NOT be supported by all implementations. In that case the
 * method will have no effect.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiWidgetPopup extends UiWidgetBaseWindow, AttributeWriteResizable, UiWidgetReal {

  // nothing to add

}
