/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.widget;

/**
 * This is the marker interface for native {@link UiWidget}s that can be {@link UiWidgetFactory#create(Class)
 * created by interface} via {@link UiWidgetFactory}.
 * 
 * @see net.sf.mmm.client.ui.api.UiContext#getWidgetFactory()
 * @see UiWidgetFactory#create(Class)
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiWidgetNative extends UiWidget {

  // nothing to add

}
