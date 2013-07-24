/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.event;

import net.sf.mmm.client.ui.api.feature.UiFeatureMode;

/**
 * This is the implementation of {@link UiEvent} for {@link EventType#MODE}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiEventMode extends AbstractUiEvent {

  /** @see #getSource() */
  private final UiFeatureMode source;

  /**
   * The constructor.
   * 
   * @param source - see {@link #getSource()}.
   * @param programmatic - see {@link #isProgrammatic()}.
   */
  public UiEventMode(UiFeatureMode source, boolean programmatic) {

    super(programmatic);
    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFeatureMode getSource() {

    return this.source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EventType getType() {

    return EventType.MODE;
  }

}
