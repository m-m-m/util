/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.handler.event;

import net.sf.mmm.client.ui.api.event.UiEvent;

/**
 * An implementation of {@link UiHandlerEvent} that ignores all {@link UiEvent}s.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public final class UiHandlerEventIgnore implements UiHandlerEvent {

  /** The singleton instance. */
  public static final UiHandlerEventIgnore INSTANCE = new UiHandlerEventIgnore();

  /**
   * The constructor.
   */
  private UiHandlerEventIgnore() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onEvent(UiEvent event) {

    // ignore
  }

}
