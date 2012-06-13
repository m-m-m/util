/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.ui.toolkit.api.handler.object;

/**
 * This is the {@link UiHandlerObject} for the action {@link #onApprove(Object) approve}.
 * 
 * @see net.sf.mmm.ui.toolkit.api.handler.plain.UiHandlerPlainSave
 * 
 * @author hohwille
 * @since 1.0.0
 * @param <O> is the generic type of the object to invoke the action operation on.
 */
public interface UiHandlerObjectApprove<O> extends UiHandlerObject<O> {

  /**
   * This method is invoked for the action
   * {@link net.sf.mmm.ui.toolkit.api.handler.plain.UiHandlerPlainApprove#onApprove() approve}.
   * 
   * @param object is the actual object to approve.
   */
  void onApprove(O object);

}
