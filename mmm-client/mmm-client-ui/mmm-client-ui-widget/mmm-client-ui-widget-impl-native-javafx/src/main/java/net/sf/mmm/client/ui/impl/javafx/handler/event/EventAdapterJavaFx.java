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
import net.sf.mmm.client.ui.base.handler.event.AbstractEventAdapter;

/**
 * This class is the JavaFx specific adapter for {@link UiHandlerEvent}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class EventAdapterJavaFx extends AbstractEventAdapter implements ChangeListener<Boolean>,
    EventHandler<ActionEvent> {

  /**
   * The constructor.
   * 
   * @param source is the source of the events (typically {@link net.sf.mmm.client.ui.api.widget.UiWidget}).
   * @param sender is the sender of events (an adapter that delegates to the individual handlers/listeners).
   */
  public EventAdapterJavaFx(UiFeatureEvent source, UiHandlerEvent sender) {

    super(source, sender);
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

}
