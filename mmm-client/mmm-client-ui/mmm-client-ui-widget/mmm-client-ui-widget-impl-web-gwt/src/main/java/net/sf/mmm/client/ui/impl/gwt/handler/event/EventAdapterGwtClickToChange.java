/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.handler.event;

import net.sf.mmm.client.ui.api.common.EventType;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;

import com.google.gwt.event.dom.client.ClickEvent;

/**
 * This class extends {@link EventAdapterGwt} but fires a {@link EventType#VALUE_CHANGE} in case of
 * {@link #onClick(ClickEvent)}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class EventAdapterGwtClickToChange extends EventAdapterGwt {

  /**
   * The constructor.
   * 
   * @param source is the source of the events (typically {@link net.sf.mmm.client.ui.api.widget.UiWidget}).
   * @param sender is the sender of events (an adapter that delegates to the individual handlers/listeners).
   */
  public EventAdapterGwtClickToChange(UiFeatureEvent source, UiHandlerEvent sender) {

    super(source, sender);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void onClick(ClickEvent event) {

    fireEvent(EventType.VALUE_CHANGE);
  }

}
