/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler;

/**
 * This is the marker interface for a <em>handler</em> of action(s) in the context of a user-interface (UI).
 * An actual handler is a particular interface extending this one that specifies <em>action operations</em>
 * for events triggered from the <em>view</em> (UI). These are typically implemented by the controller or
 * presenter following the <em>MVC</em> or <em>MVP</em> paradigm. Such action operations shall be methods that
 * start with the prefix <code>on</code> and are followed with the name of the event that has been triggered.<br/>
 * For examples see e.g. {@link net.sf.mmm.ui.toolkit.api.handler.event.UiHandlerEventClick#onClick(boolean)},
 * {@link net.sf.mmm.ui.toolkit.api.handler.plain.UiHandlerPlainCancel#onCancel()},
 * {@link net.sf.mmm.ui.toolkit.api.handler.object.UiHandlerObjectDelete#onDelete(Object)}, or
 * {@link net.sf.mmm.ui.toolkit.api.handler.plain.UiHandlerPlainSave#onSave()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract interface UiHandler {

  // just a marker interface for reasons of documentation...

}
