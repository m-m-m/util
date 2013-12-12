/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.action;

import net.sf.mmm.client.ui.api.event.UiEvent;

/**
 * This is the {@link UiHandlerAction} for the action {@link #onConfirm(UiEvent) confirm}.
 * 
 * @see UiHandlerActionApprove
 * @see UiHandlerActionSubmit
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerActionConfirm extends UiHandlerAction {

  /**
   * This method is invoked for the action <em>confirm</em> often also labeled with <code>OK</code>. This
   * means the current step or data is acknowledged.
   * 
   * @param event is the {@link UiEvent} that triggered this action.
   */
  void onConfirm(UiEvent event);

}
