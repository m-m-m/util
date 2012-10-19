/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

import net.sf.mmm.client.ui.api.attribute.AttributeReadSizeInPixel;

/**
 * This is the interface for the display where the UI objects are shown.<br/>
 * <b>ATTENTION:</b><br/>
 * Be aware of multi-monitor setups. This object will represent the main screen OR the current screen where
 * the {@link net.sf.mmm.client.ui.api.widget.window.UiWidgetMainWindow} is located. This can depend on the
 * underlying implementation or in case of a web-application on the browser.
 * 
 * @see UiWidgetFactory#getDisplay()
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiDisplay extends AttributeReadSizeInPixel {

  // currently nothing to add...

}
