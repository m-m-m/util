/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler.object;

import net.sf.mmm.ui.toolkit.api.handler.UiHandler;

/**
 * This is the marker interface for a {@link UiHandler} that offers a action that operates on a given object
 * (the generic {@literal <O>}).
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <O> is the generic type of the object to invoke the action operation on. There is no limitation for
 *        this object. However it should typically be a business oriented transfer object but can also be a
 *        {@link java.util.Collection} of such.
 */
public abstract interface UiHandlerObject<O> extends UiHandler {

  // just a marker interface.

}
