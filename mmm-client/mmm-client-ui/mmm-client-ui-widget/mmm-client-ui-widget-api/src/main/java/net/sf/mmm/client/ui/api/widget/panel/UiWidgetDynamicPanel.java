/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget.panel;

import net.sf.mmm.client.ui.api.widget.UiWidget;
import net.sf.mmm.client.ui.api.widget.UiWidgetDynamicComposite;

/**
 * This is the abstract interface for a {@link UiWidgetPanel panel widget} that represents a
 * <em>dynamic panel</em>. It is just a combination of {@link UiWidgetPanel} with
 * {@link UiWidgetDynamicComposite}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public abstract interface UiWidgetDynamicPanel<CHILD extends UiWidget> extends UiWidgetPanel<CHILD>,
    UiWidgetDynamicComposite<CHILD> {

  // nothing to add

}
