/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.base;

import java.util.Collection;

import net.sf.mmm.util.collection.api.CollectionFactory;
import net.sf.mmm.util.collection.base.ArrayListFactory;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.event.api.Event;
import net.sf.mmm.util.event.api.EventListener;
import net.sf.mmm.util.event.api.EventSource;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.util.event.api.EventListener} interface.
 * 
 * @param <E> is the templated type of the events to send.
 * @param <L> is the templated type of the listeners that can be
 *        {@link #addListener(EventListener) registered} here and that will
 *        {@link net.sf.mmm.util.event.api.EventListener#handleEvent(Event)
 *        receive} the sent events.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.1
 */
public abstract class AbstractEventSource<E extends Event, L extends EventListener<E>> extends
    AbstractLoggable implements EventSource<E, L> {

  /** the registered listeners */
  private final Collection<L> listeners;

  /**
   * The constructor.
   */
  public AbstractEventSource() {

    this(ArrayListFactory.INSTANCE);
  }

  /**
   * The constructor.
   * 
   * @param collectionFactory is the collection factory used to create the
   *        internal {@link Collection} for the listeners.
   */
  @SuppressWarnings("unchecked")
  protected AbstractEventSource(CollectionFactory<? extends Collection> collectionFactory) {

    super();
    this.listeners = collectionFactory.create();
  }

  /**
   * {@inheritDoc}
   */
  public void addListener(L listener) {

    this.listeners.add(listener);
  }

  /**
   * {@inheritDoc}
   */
  public boolean removeListener(L listener) {

    return this.listeners.remove(listener);
  }

  /**
   * This method sends the given <code>event</code> to all
   * {@link #addListener(EventListener) registered} listeners.
   * 
   * @param event the event to set.
   */
  protected void fireEvent(E event) {

    for (L listener : this.listeners) {
      try {
        fireEvent(event, listener);
      } catch (RuntimeException e) {
        handleListenerError(listener, event, e);
      }
    }
  }

  /**
   * This method sends the given <code>event</code> to the given
   * <code>listener</code>.
   * 
   * @param event the event to set.
   * @param listener the listener that should receive the <code>event</code>.
   */
  protected void fireEvent(E event, L listener) {

    listener.handleEvent(event);
  }

  /**
   * This method is called if a listener throws something while handling an
   * event.<br>
   * The default implementation is log the error. Override this method to change
   * the behaviour (e.g. ignore the problem, remove the "evil" listener, throw
   * the error anyways).
   * 
   * @param listener is the listener that caused the error.
   * @param event is the event that could not be handled.
   * @param error is the throwable caused by the <code>listener</code> while
   *        handling the <code>event</code>.
   */
  protected void handleListenerError(L listener, E event, Throwable error) {

    getLogger().debug("The listener (" + listener + ") failed to handle event (" + event + "):",
        error);
  }

}
