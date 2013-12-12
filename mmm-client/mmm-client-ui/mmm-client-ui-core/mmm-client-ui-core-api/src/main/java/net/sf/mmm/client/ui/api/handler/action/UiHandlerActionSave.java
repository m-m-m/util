/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.action;

import net.sf.mmm.client.ui.api.event.UiEvent;

/**
 * This is the {@link UiHandlerAction} for the action {@link #onSave(UiEvent) save}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerActionSave extends UiHandlerAction {

  /**
   * This method is invoked for the action <em>save</em>. This means that a step, change or creation is saved
   * to a persistent store.
   * 
   * @param event is the {@link UiEvent} that triggered this action.
   */
  void onSave(UiEvent event);

}
