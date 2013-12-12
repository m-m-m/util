/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.action;

import net.sf.mmm.client.ui.api.event.UiEvent;

/**
 * This is the {@link UiHandlerAction} for the action {@link #onDelete(UiEvent) delete}.
 * 
 * @see UiHandlerActionRemove
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerActionDelete extends UiHandlerAction {

  /**
   * This method is invoked for the action <em>delete</em>. This means that something (e.g. an entity) is to
   * be deleted and will no longer exist.
   * 
   * @param event is the {@link UiEvent} that triggered this action.
   */
  void onDelete(UiEvent event);

}
