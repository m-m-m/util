/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.plain;

import net.sf.mmm.client.ui.api.handler.UiHandler;

/**
 * This is the marker interface for a {@link UiHandler} that offers a <em>plain</em> action. Plain means that
 * the action is a operation that only takes an optional {@link net.sf.mmm.util.lang.api.Variant} as argument
 * and is typically invoked by a button. This is a very simple form of a {@link UiHandler}. Most plain
 * handlers have a corresponding {@link net.sf.mmm.client.ui.api.handler.object.UiHandlerObject}.<br/>
 * Typically an action triggering UI element like a button itself does not know the object on which the action
 * is invoked and therefore calls a {@link UiHandlerPlain} without arguments. This is implemented by some
 * higher level place that knows how to get the data corresponding object from the view. With this object it
 * delegates to the {@link net.sf.mmm.client.ui.api.handler.object.UiHandlerObject} that is typically
 * implemented by the controller/presenter and may invoke a remote service to save, delete, or perform
 * whatever operation with this object.<br/>
 * <b>ATTENTION:</b><br/>
 * The {@link net.sf.mmm.util.lang.api.Variant} can simply be ignored. You can however use it for more complex
 * use-cases (e.g. if you want to have two "Save" or "Search" buttons that do a similar but not exactly the
 * same thing such as "Save with backup" or "Search and get results as CSV").
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiHandlerPlain extends UiHandler {

  // just a marker interface for reasons of documentation...

}
