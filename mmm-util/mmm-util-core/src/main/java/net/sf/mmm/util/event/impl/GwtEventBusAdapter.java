/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.impl;

import javax.inject.Inject;

import net.sf.mmm.util.event.api.EventBus;
import net.sf.mmm.util.event.api.EventListener;

import com.google.web.bindery.event.shared.Event;
import com.google.web.bindery.event.shared.Event.Type;
import com.google.web.bindery.event.shared.HandlerRegistration;

/**
 * This is an implementation of {@link com.google.web.bindery.event.shared.EventBus} that delegates to an
 * implementation of the {@link EventBus} interface. So you can adapt a GWT event bus usage to
 * {@link EventBus}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 5.0.0
 */
public class GwtEventBusAdapter extends com.google.web.bindery.event.shared.EventBus {

  /** Is the {@link EventBus} to delegate to. */
  private EventBus eventBus;

  /**
   * The constructor.
   */
  public GwtEventBusAdapter() {

    this(null);
  }

  /**
   * The constructor.
   *
   * @param eventBus is the {@link EventBus} to set.
   */
  public GwtEventBusAdapter(EventBus eventBus) {

    super();
    this.eventBus = eventBus;
  }

  /**
   * @param eventBus is the {@link EventBus} to inject.
   */
  @Inject
  public void setEventBus(EventBus eventBus) {

    this.eventBus = eventBus;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <H> HandlerRegistration addHandler(final Type<H> type, final H handler) {

    @SuppressWarnings("rawtypes")
    final EventListener<Event> listener = new EventListener<Event>() {

      /**
       * {@inheritDoc}
       */
      @SuppressWarnings("unchecked")
      @Override
      public void handleEvent(Event event) {

        if (event.getAssociatedType().equals(type)) {
          dispatchEvent(event, handler);
        }
      }
    };
    this.eventBus.addListener(Event.class, listener);
    return new HandlerRegistration() {

      @Override
      public void removeHandler() {

        GwtEventBusAdapter.this.eventBus.removeListener(listener);
      }
    };
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public <H> HandlerRegistration addHandlerToSource(Type<H> type, Object source, H handler) {

    throw new UnsupportedOperationException();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void fireEvent(Event<?> event) {

    this.eventBus.sendEvent(event);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void fireEventFromSource(Event<?> event, Object source) {

    throw new UnsupportedOperationException();
  }

}
