/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler.object;

import net.sf.mmm.ui.toolkit.api.handler.UiHandler;

/**
 * This is the marker interface for a {@link UiHandler} that offers a action that operates on a given object
 * (&lt;VALUE&gt;).
 * 
 * @author hohwille
 * @since 1.0.0
 * @param <VALUE> is the generic type of the object to invoke the action operation on.
 */
public abstract interface UiHandlerObject<VALUE> extends UiHandler {

  // just a marker interface.

}
