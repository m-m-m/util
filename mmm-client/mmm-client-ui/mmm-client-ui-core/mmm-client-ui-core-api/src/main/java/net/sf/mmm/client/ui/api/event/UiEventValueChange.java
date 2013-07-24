/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.event;

import net.sf.mmm.client.ui.api.feature.UiFeatureValue;

/**
 * This is the implementation of {@link UiEvent} for {@link EventType#VALUE_CHANGE}.
 * 
 * @param <VALUE> is the generic type of the {@link UiFeatureValue#getValue() value} that may change.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiEventValueChange<VALUE> extends AbstractUiEvent {

  /** @see #getSource() */
  private final UiFeatureValue<VALUE> source;

  /**
   * The constructor.
   * 
   * @param source - see {@link #getSource()}.
   * @param programmatic - see {@link #isProgrammatic()}.
   */
  public UiEventValueChange(UiFeatureValue<VALUE> source, boolean programmatic) {

    super(programmatic);
    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFeatureValue<VALUE> getSource() {

    return this.source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EventType getType() {

    return EventType.VALUE_CHANGE;
  }

}
