/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.base.widget.adapter;

import net.sf.mmm.client.ui.api.feature.UiFeatureClick;
import net.sf.mmm.client.ui.api.feature.UiFeatureEvent;
import net.sf.mmm.client.ui.api.handler.event.UiHandlerEvent;
import net.sf.mmm.client.ui.base.handler.event.AbstractEventAdapter;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;

/**
 * This class extends {@link AbstractUiWidgetAdapter} with #addEventHandler.
 * 
 * @param <WIDGET> is the generic type of {@link #getToplevelWidget()}.
 * @param <EVENT_ADAPTER> is the generic type of {@link #getEventAdapter()}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractUiWidgetAdapterWithEvents<WIDGET, EVENT_ADAPTER extends AbstractEventAdapter> extends
    AbstractUiWidgetAdapter<WIDGET> {

  /** @see #setEventSender(UiFeatureEvent, UiHandlerEvent) */
  private EVENT_ADAPTER eventAdapter;

  /**
   * The constructor.
   */
  public AbstractUiWidgetAdapterWithEvents() {

    super();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void setEventSender(UiFeatureEvent source, UiHandlerEvent sender) {

    if (this.eventAdapter != null) {
      throw new NlsIllegalStateException();
    }
    this.eventAdapter = createEventAdapter(source, sender);
    applyEventAdapter(this.eventAdapter);
  }

  /**
   * This method creates the {@link #getEventAdapter()} instance. It should to be implemented by the top-level
   * widget-adapter class specific for a native UI toolkit. It may further be overridden to use a sub-class
   * instead when more specific behavior is needed.
   * 
   * @param source is the source of the events (typically {@link net.sf.mmm.client.ui.api.widget.UiWidget}).
   * @param sender is the sender of events (an adapter that delegates to the individual handlers/listeners).
   * @return the new {@link AbstractEventAdapter event adapter}.
   */
  protected abstract EVENT_ADAPTER createEventAdapter(UiFeatureEvent source, UiHandlerEvent sender);

  /**
   * @see #setEventSender(UiFeatureEvent, UiHandlerEvent)
   * @see AbstractEventAdapter
   * 
   * @return the {@link AbstractEventAdapter event adapter}. May be <code>null</code>.
   */
  public EVENT_ADAPTER getEventAdapter() {

    return this.eventAdapter;
  }

  /**
   * This method applies the given {@link AbstractEventAdapter event adapter} to the widget(s) so it receive
   * native events. It will be called only once. Override only to add specific event registrations.
   * 
   * @param adapter is the {@link AbstractEventAdapter event adapter}.
   */
  protected void applyEventAdapter(EVENT_ADAPTER adapter) {

    // Click-Events...
    if (getUiWidget() instanceof UiFeatureClick) {
      applyEventAdapterForClick(adapter);
    }

    // Focus-Events...
    applyEventAdapterForFocus(adapter);

    // Change-Events...
    applyEventAdapterForChange(adapter);
  }

  /**
   * Applies for click events.
   * 
   * @param adapter is the {@link AbstractEventAdapter event adapter}.
   */
  protected void applyEventAdapterForClick(EVENT_ADAPTER adapter) {

    // nothing by default...
  }

  /**
   * Applies for focus (and blur) events.
   * 
   * @param adapter is the {@link AbstractEventAdapter event adapter}.
   */
  protected void applyEventAdapterForFocus(EVENT_ADAPTER adapter) {

    // nothing by default...
  }

  /**
   * Applies for (value) change events.
   * 
   * @param adapter is the {@link AbstractEventAdapter event adapter}.
   */
  protected void applyEventAdapterForChange(EVENT_ADAPTER adapter) {

    // nothing by default...
  }

}
