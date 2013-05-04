/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.handler.event;

import net.sf.mmm.client.ui.api.common.EventType;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;

/**
 * This is the abstract base class for a toolkit specific adapter of {@link UiHandlerEvent}. It will adapt
 * from the native events to
 * {@link UiHandlerEvent#onEvent(UiFeatureEvent, net.sf.mmm.client.ui.api.common.UiEvent, boolean)} in the
 * {@link UiHandlerEvent event handler} given at construction.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractEventAdapter {

  /** The source of the events (UiWidget). */
  private final UiFeatureEvent source;

  /** The event sender. */
  private final UiHandlerEvent sender;

  /** @see #setProgrammaticEventType(EventType) */
  private EventType programmaticEventType;

  /**
   * The constructor.
   * 
   * @param source is the source of the events (typically {@link net.sf.mmm.client.ui.api.widget.UiWidget}).
   * @param sender is the sender of events (an adapter that delegates to the individual handlers/listeners).
   */
  public AbstractEventAdapter(UiFeatureEvent source, UiHandlerEvent sender) {

    super();
    this.source = source;
    this.sender = sender;
  }

  /**
   * Fires an event of the given {@link EventType}.
   * 
   * @param type is the {@link EventType} to fire.
   */
  public void fireEvent(EventType type) {

    boolean programmtic = (this.programmaticEventType == type);
    this.sender.onEvent(this.source, type, programmtic);
    if (programmtic) {
      this.programmaticEventType = null;
    }
  }

  /**
   * We can NOT prevent all native UI toolkits from firing events (e.g. when
   * {@link net.sf.mmm.client.ui.api.attribute.AttributeWriteFocused#setFocused() programmatically setting
   * focus}. So we set the {@link EventType} before we programmatically invoke the action that will cause the
   * native event.
   * 
   * @param type is the expected {@link EventType} used for detection to prevent concurrent user-events to
   *        interfere.
   */
  public void setProgrammaticEventType(EventType type) {

    this.programmaticEventType = type;
  }

}
