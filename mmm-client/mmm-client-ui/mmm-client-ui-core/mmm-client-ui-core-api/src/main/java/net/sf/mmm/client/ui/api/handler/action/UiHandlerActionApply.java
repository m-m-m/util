/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.action;

import net.sf.mmm.client.ui.api.event.UiEvent;

/**
 * This is the {@link UiHandlerAction} for the action {@link #onApply(UiEvent) apply}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerActionApply extends UiHandlerAction {

  /**
   * This method is invoked for the action <em>apply</em>. This means that some change is immediately applied
   * to something so you will see the result. This is similar to
   * {@link UiHandlerActionSubmit#onSubmit(UiEvent) submit} but submit often indicates that a change is send to
   * the server while apply typically makes the change take effect visible locally.
   * 
   * @param event is the {@link UiEvent} that triggered this action.
   */
  void onApply(UiEvent event);

}
