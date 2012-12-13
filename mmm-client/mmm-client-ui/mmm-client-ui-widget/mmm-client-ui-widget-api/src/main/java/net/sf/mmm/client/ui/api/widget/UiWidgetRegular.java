/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

/**
 * This is the interface for a <em>regular</em> {@link UiWidget}. Here regular means that it represents a
 * generic widget that can be placed {@link net.sf.mmm.client.ui.api.widget.UiWidgetRegularComposite almost
 * anywhere}. Examples for widgets that are NOT regular are
 * {@link net.sf.mmm.client.ui.api.widget.window.UiWidgetWindow} or
 * {@link net.sf.mmm.client.ui.api.widget.menu.UiWidgetMenuItem}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetRegular extends UiWidget {

  // just a marker interface...

}
