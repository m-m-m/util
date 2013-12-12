/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.action;

import net.sf.mmm.client.ui.api.event.UiEvent;

/**
 * This is the {@link UiHandlerAction} for the action {@link #onApprove(UiEvent) approve}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerActionApprove extends UiHandlerAction {

  /**
   * This method is invoked for the action <em>approve</em>. This means that some change or step is confirmed.
   * Approval is often workflow related and may require a particular role (following the double-check or
   * four-eye principle).
   * 
   * @param event is the {@link UiEvent} that triggered this action.
   */
  void onApprove(UiEvent event);

}
