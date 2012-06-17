/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
/**
 * Contains interfaces for handlers of the UI.
 * <a name="documentation"/><h2>UI Handlers API</h2> 
 * This package and it sub-packages contain {@link net.sf.mmm.ui.toolkit.api.handler.UiHandler}-interfaces defining
 * <em>action operations</em> for events triggered from the UI (user interface). These are call-back interfaces that 
 * will be implemented with the custom behavior for the particular event.<br/>
 * <b>ATTENTION:</b><br/>
 * Typically these interfaces are implemented by a controller or presenter following the <em>MVC</em> or
 * <em>MVP</em> paradigm. However, this should only be done if it helps to increase code quality (e.g. for separation 
 * of concerns). In various cases there are recurring <em>UI patterns</em> in an application client that are best 
 * addressed directly by reusable view logic. E.g. if you want to have a edit button to switch your UI to the edit mode,
 * you will not increase your code quality if this has to be implemented redundantly by every controller or presenter.
 * In other words we do not believe in strict MVC or MVP. See the documentation of <code>UiElement</code> for further
 * details. On the other hand we strongly recommend that handler operations that perform advanced business logic or 
 * even invoke remote services should only be implemented in the controller/presenter. See also
 * {@link net.sf.mmm.ui.toolkit.api.handler.object.UiHandlerObject}.
 */
package net.sf.mmm.ui.toolkit.api.handler;

