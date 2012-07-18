/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.widget.atomic;

import net.sf.mmm.ui.toolkit.api.widget.UiWidget;

/**
 * This is the interface for an atomic {@link UiWidget}. Unlike
 * {@link net.sf.mmm.ui.toolkit.api.widget.composite.UiWidgetComposite} such widget can not have
 * {@link net.sf.mmm.ui.toolkit.api.widget.composite.UiWidgetComposite#getChild(int) children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetAtomic extends UiWidget {

  // just a marker interface...

}
