/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

/**
 * This is the abstract interface for an atomic {@link UiWidget}. Unlike
 * {@link net.sf.mmm.client.ui.api.widget.UiWidgetComposite} such widget can not have
 * {@link net.sf.mmm.client.ui.api.widget.UiWidgetComposite#getChild(int) children}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetAtomic extends UiWidget {

  // just a marker interface...

}
