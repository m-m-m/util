/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.gwt.handler.event;

import net.sf.mmm.client.ui.api.common.EventType;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;
import net.sf.mmm.client.ui.base.handler.event.AbstractEventAdapter;

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
public class EventAdapterGwt extends AbstractEventAdapter implements FocusHandler, BlurHandler, ClickHandler,
    ChangeHandler {

  /**
   * The constructor.
   * 
   * @param source is the source of the events (typically {@link net.sf.mmm.client.ui.api.widget.UiWidget}).
   * @param sender is the sender of events (an adapter that delegates to the individual handlers/listeners).
   */
  public EventAdapterGwt(UiFeatureEvent source, UiHandlerEvent sender) {

    super(source, sender);
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

}
