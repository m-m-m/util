/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.action;

import net.sf.mmm.client.ui.api.event.UiEvent;

/**
 * This is the {@link UiHandlerAction} for the action {@link #onStartEditMode(UiEvent) start edit mode}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerActionStartEdit extends UiHandlerAction {

  /**
   * This method is invoked for the action <em>start edit mode</em>, typically labeled with <em>edit</em>.
   * This means the current dialog is
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteMode#setMode(net.sf.mmm.client.ui.api.common.UiMode)
   * switched} to the {@link net.sf.mmm.client.ui.api.common.UiMode#EDIT edit mode}.
   * 
   * @param event is the {@link UiEvent} that triggered this action.
   */
  void onStartEditMode(UiEvent event);

}
