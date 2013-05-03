/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.javafx.handler.event;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import net.sf.mmm.client.ui.api.common.EventType;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;

/**
 * This class is the JavaFx specific adapter for {@link UiHandlerEvent}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class EventAdapterJavaFx implements ChangeListener<Boolean>, EventHandler<ActionEvent> {

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
  public EventAdapterJavaFx(UiFeatureEvent source, UiHandlerEvent sender) {

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
  public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {

    if (Boolean.TRUE.equals(newValue)) {
      fireEvent(EventType.FOCUS_GAIN);
    } else {
      fireEvent(EventType.FOCUS_LOSS);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void handle(ActionEvent event) {

    fireEvent(EventType.CLICK);
  }

  /**
   * We can NOT prevent JavaFx from firing some events. So we set the {@link EventType} before we
   * programmatically invoke the action that will cause the JavaFx event.
   * 
   * @param type is the expected {@link EventType} used for detection to prevent concurrent user-events to
   *        interfere.
   */
  public void setProgrammaticEventType(EventType type) {

    this.programmaticEventType = type;
  }

}
