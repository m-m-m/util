/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.event;

import net.sf.mmm.client.ui.api.feature.UiFeatureFocus;

/**
 * This is the implementation of {@link UiEvent} for {@link EventType#FOCUS_GAIN} or
 * {@link EventType#FOCUS_LOSS}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class UiEventFocus extends AbstractUiEvent {

  /** @see #getSource() */
  private final UiFeatureFocus source;

  /**
   * The constructor.
   * 
   * @param source - see {@link #getSource()}.
   * @param programmatic - see {@link #isProgrammatic()}.
   */
  public UiEventFocus(UiFeatureFocus source, boolean programmatic) {

    super(programmatic);
    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFeatureFocus getSource() {

    return this.source;
  }

}
