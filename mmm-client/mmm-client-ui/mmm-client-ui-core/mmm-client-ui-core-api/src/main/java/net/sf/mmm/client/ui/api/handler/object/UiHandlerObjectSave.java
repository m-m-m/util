/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.object;

import net.sf.mmm.client.ui.api.event.UiEvent;

/**
 * This is the {@link UiHandlerObject} for the action {@link #onSave(Object, UiEvent) save}.
 * 
 * @see net.sf.mmm.client.ui.api.handler.action.UiHandlerActionSave
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 * @param <O> is the generic type of the object to invoke the action operation on.
 */
public interface UiHandlerObjectSave<O> extends UiHandlerObject<O> {

  /**
   * This method is invoked for the action
   * {@link net.sf.mmm.client.ui.api.handler.action.UiHandlerActionSave#onSave(UiEvent) save}.
   * 
   * @param object is the actual object to save.
   * @param event is the {@link UiEvent} that triggered this action.
   */
  void onSave(O object, UiEvent event);

}
