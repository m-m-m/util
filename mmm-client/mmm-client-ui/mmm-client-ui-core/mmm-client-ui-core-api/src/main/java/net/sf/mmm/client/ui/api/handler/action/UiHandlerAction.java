/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.action;

import net.sf.mmm.client.ui.api.handler.UiHandler;

/**
 * This is the marker interface for a {@link UiHandler} of a specific <em>action</em>. The action is invoked
 * by a triggering widget, typically a button. It takes the {@link net.sf.mmm.client.ui.api.event.UiEvent}
 * (will be e.g. an instance of {@link net.sf.mmm.client.ui.api.event.UiEventClick}) as a single argument.
 * Therefore a {@link UiHandlerAction} can be used to create a triggering widget (typically a button) with all
 * its aspects (label with {@link net.sf.mmm.util.nls.api.NlsMessage NLS}, icon, tooltip) to make your life
 * easier. Further these aspects can be maintained at a single place in the code and allows a consistent user
 * experience throughout your entire application.<br/>
 * Most action handlers have a corresponding {@link net.sf.mmm.client.ui.api.handler.object.UiHandlerObject}.
 * Typically an action triggering UI element like a button itself does not know the object on which the action
 * is invoked and therefore calls a {@link UiHandlerAction} without any data. This is implemented by some
 * higher level place that knows how to get the data corresponding data from the view. With this data it
 * delegates to the {@link net.sf.mmm.client.ui.api.handler.object.UiHandlerObject} that may invoke a remote
 * service to save, delete, or perform whatever operation with this object.<br/>
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiHandlerAction extends UiHandler {

  // just a marker interface for reasons of documentation...

}
