/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.action;

import net.sf.mmm.client.ui.api.event.UiEvent;

/**
 * This is the {@link UiHandlerAction} for the action {@link #onClose(UiEvent) close}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerActionClose extends UiHandlerAction {

  /**
   * This method is invoked for the action <em>close</em>. This means the current window, panel, hover, or the
   * like will be closed.
   * 
   * @param event is the {@link UiEvent} that triggered this action.
   */
  void onClose(UiEvent event);

}
