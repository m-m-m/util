/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.action;

import net.sf.mmm.client.ui.api.event.UiEvent;

/**
 * This is the {@link UiHandlerAction} for the action {@link #onSubmit(UiEvent) submit}.
 * 
 * @see UiHandlerActionConfirm
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerActionSubmit extends UiHandlerAction {

  /**
   * This method is invoked for the action <em>submit</em>. This means that some step or change is completed
   * and shall be submitted. This is more generic than e.g. {@link UiHandlerActionSave#onSave(UiEvent) save}
   * or {@link UiHandlerActionApprove#onApprove(UiEvent) approve}.
   * 
   * @param event is the {@link UiEvent} that triggered this action.
   */
  void onSubmit(UiEvent event);

}
