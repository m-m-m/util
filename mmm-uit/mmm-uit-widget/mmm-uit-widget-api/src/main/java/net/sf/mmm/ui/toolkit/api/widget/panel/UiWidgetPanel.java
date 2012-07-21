/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.panel;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;
import net.sf.mmm.ui.toolkit.api.widget.UiWidgetRegularComposite;

/**
 * This is the interface for an {@link UiWidgetRegularComposite} that represents a <em>panel</em>. A panel is
 * a widget that acts as a container for other widgets organizing them in a specific layout. Such widget may
 * also be called container or layout.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <CHILD> is the generic type of the {@link #getChild(int) children}.
 */
public abstract interface UiWidgetPanel<CHILD extends UiWidget> extends UiWidgetRegularComposite<CHILD> {

  // nothing to add

}
