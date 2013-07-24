/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.event;

import net.sf.mmm.client.ui.api.feature.UiFeatureClick;

/**
 * This is the implementation of {@link UiEvent} for {@link EventType#CLICK}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiEventClick extends AbstractUiEvent {

  /** @see #getSource() */
  private final UiFeatureClick source;

  /**
   * The constructor.
   * 
   * @param source - see {@link #getSource()}.
   * @param programmatic - see {@link #isProgrammatic()}.
   */
  public UiEventClick(UiFeatureClick source, boolean programmatic) {

    super(programmatic);
    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFeatureClick getSource() {

    return this.source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EventType getType() {

    return EventType.CLICK;
  }

}
