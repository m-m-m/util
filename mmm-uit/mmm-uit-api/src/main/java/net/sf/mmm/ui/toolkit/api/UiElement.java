/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api;

import net.sf.mmm.ui.toolkit.api.attribute.UiReadPreferredSize;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteDisposed;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteEnabled;
import net.sf.mmm.ui.toolkit.api.attribute.UiWritePosition;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteSize;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteTooltip;

/**
 * This is the interface for a UI component. Such object is either a
 * {@link net.sf.mmm.ui.toolkit.api.widget.UIWidget widget} or a
 * {@link net.sf.mmm.ui.toolkit.api.composite.UIComposite composite}
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface UiElement extends UINodeRenamed, UiWriteTooltip, UiWriteEnabled, UiWriteSize,
    UiReadPreferredSize, UiWritePosition, UiWriteDisposed {

  // get/set cursor type

}
