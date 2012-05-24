/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler.object;

/**
 * This is the {@link UiHandlerObject} for the action {@link #onDelete(Object) delete}.
 * 
 * @see net.sf.mmm.ui.toolkit.api.handler.plain.UiHandlerPlainDelete
 * 
 * @author hohwille
 * @since 1.0.0
 * @param <O> is the generic type of the object to invoke the action operation on.
 */
public interface UiHandlerObjectDelete<O> extends UiHandlerObject<O> {

  /**
   * This method is invoked for the action
   * {@link net.sf.mmm.ui.toolkit.api.handler.plain.UiHandlerPlainDelete#onDelete() delete}.
   * 
   * @param object is the actual object to delete.
   */
  void onDelete(O object);

}
