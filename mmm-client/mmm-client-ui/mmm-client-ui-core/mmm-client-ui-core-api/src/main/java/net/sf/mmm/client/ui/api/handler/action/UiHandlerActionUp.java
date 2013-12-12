/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.action;

import net.sf.mmm.client.ui.api.event.UiEvent;

/**
 * This is the {@link UiHandlerAction} for the action {@link #onUp(UiEvent) up}.
 * 
 * @see UiHandlerActionNext
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerActionUp extends UiHandlerAction {

  /**
   * This method is invoked for the action <em>up</em>. This means that an object is moved upwards (typically
   * to re-order).
   * 
   * @param event is the {@link UiEvent} that triggered this action.
   */
  void onUp(UiEvent event);

}
