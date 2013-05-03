/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.handler.event;

import net.sf.mmm.client.ui.api.common.EventType;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;

import com.google.gwt.event.dom.client.BlurEvent;
import com.google.gwt.event.dom.client.BlurHandler;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.FocusEvent;
import com.google.gwt.event.dom.client.FocusHandler;

/**
 * This class is the GWT specific adapter for {@link UiHandlerEvent}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class EventAdapterGwt implements FocusHandler, BlurHandler, ClickHandler, ChangeHandler {

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
  public EventAdapterGwt(UiFeatureEvent source, UiHandlerEvent sender) {

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
   * {@inheritDoc}
   */
  @Override
  public void onBlur(BlurEvent event) {

    fireEvent(EventType.FOCUS_LOSS);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onFocus(FocusEvent event) {

    fireEvent(EventType.FOCUS_GAIN);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onClick(ClickEvent event) {

    fireEvent(EventType.CLICK);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onChange(ChangeEvent event) {

    fireEvent(EventType.VALUE_CHANGE);
  }

  /**
   * We can NOT prevent GWT from firing events such as
   * {@link com.google.gwt.user.client.ui.FocusWidget#setFocus(boolean)}. So we set the {@link EventType}
   * before we programmatically invoke the action that will cause the GWT event.
   * 
   * @param type is the expected {@link EventType} used for detection to prevent concurrent user-events to
   *        interfere.
   */
  public void setProgrammaticEventType(EventType type) {

    this.programmaticEventType = type;
  }

}
