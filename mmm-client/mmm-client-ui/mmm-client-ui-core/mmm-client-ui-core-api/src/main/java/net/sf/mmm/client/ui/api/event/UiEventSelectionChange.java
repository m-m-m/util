/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.api.event;

import net.sf.mmm.client.ui.api.feature.UiFeatureSelectedValue;

/**
 * This is the implementation of {@link UiEvent} for {@link EventType#SELECTION_CHANGE}.
 * 
 * @param <VALUE> is the generic type of the {@link UiFeatureSelectedValue#getSelectedValue() selected value}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class UiEventSelectionChange<VALUE> extends AbstractUiEvent {

  /** @see #getSource() */
  private final UiFeatureSelectedValue<VALUE> source;

  /**
   * The constructor.
   * 
   * @param source - see {@link #getSource()}.
   * @param programmatic - see {@link #isProgrammatic()}.
   */
  public UiEventSelectionChange(UiFeatureSelectedValue<VALUE> source, boolean programmatic) {

    super(programmatic);
    this.source = source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public UiFeatureSelectedValue<VALUE> getSource() {

    return this.source;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public EventType getType() {

    return EventType.SELECTION_CHANGE;
  }

}
