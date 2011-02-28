/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.view;

import net.sf.mmm.ui.toolkit.api.attribute.UiReadPreferredSize;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteDisposed;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteEnabled;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteSize;
import net.sf.mmm.ui.toolkit.api.attribute.UiWriteTooltip;

/**
 * This is the interface for a UI component. Such object is either a
 * {@link net.sf.mmm.ui.toolkit.api.view.widget.UiWidget widget} or a
 * {@link net.sf.mmm.ui.toolkit.api.view.composite.UiComposite composite}
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiElement extends UiNode, UiWriteTooltip, UiWriteEnabled, UiWriteSize,
    UiReadPreferredSize, UiWriteDisposed {

}
