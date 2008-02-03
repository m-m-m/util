/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the abstract base implementation of the
 * {@link net.sf.mmm.util.event.EventListener} interface.
 * 
 * @param <E> is the templated type of the events to send.
 * @param <L> is the templated type of the listeners that can be
 *        {@link #addListener(EventListener) registered} here and that will
 *        {@link net.sf.mmm.util.event.EventListener#handleEvent(Event) receive}
 *        the sent events.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractEventSource<E extends Event, L extends EventListener<E>> implements
    EventSource<E, L> {

  /** the registered listeners */
  private final List<L> listeners;

  /**
   * The constructor.
   */
  public AbstractEventSource() {

    super();
    this.listeners = new ArrayList<L>();
  }

  /**
   * The constructor
   * 
   * @param listenerList is the list used to store the listeners.
   */
  protected AbstractEventSource(List<L> listenerList) {

    super();
    this.listeners = listenerList;
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
  public void removeListener(L listener) {

    this.listeners.remove(listener);
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
   * The default implementation is to do nothing. Override this method to change
   * the behaviour (e.g. log the problem, remove the "evil" listener, throw the
   * error anyways).
   * 
   * @param listener is the listener that caused the error.
   * @param event is the event that could not be handled.
   * @param error is the throwable caused by the <code>listener</code> while
   *        handling the <code>event</code>.
   */
  protected void handleListenerError(L listener, E event, Throwable error) {

  // by default do nothing
  }

}
