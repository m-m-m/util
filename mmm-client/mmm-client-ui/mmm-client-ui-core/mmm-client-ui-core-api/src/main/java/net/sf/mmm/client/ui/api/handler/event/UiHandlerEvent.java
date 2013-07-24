/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.event.UiEvent;
import net.sf.mmm.client.ui.api.handler.UiHandler;

/**
 * This is the interface for a {@link UiHandler} that represents an <em>event listener</em>. It receives
 * {@link UiEvent}s via the action {@link #onEvent(UiEvent)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public interface UiHandlerEvent extends UiHandler {

  /**
   * This method is invoked for each {@link UiEvent} (of any {@link UiEvent#getType() type}) fired by the
   * widget.
   * 
   * @param event is the {@link UiEvent} that has been sent.
   */
  void onEvent(UiEvent event);

}
