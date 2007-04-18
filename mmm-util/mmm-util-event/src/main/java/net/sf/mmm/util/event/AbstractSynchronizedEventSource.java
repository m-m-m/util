/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event;

import java.util.ArrayList;
import java.util.List;

/**
 * This class extends {@link AbstractEventSource} with the ability of
 * synchronization and therefore is thread-safe.
 * 
 * @param <E>
 *        is the templated type of the events to send.
 * @param <L>
 *        is the templated type of the listeners that can be
 *        {@link #addListener(EventListener) registered} here and that will
 *        {@link net.sf.mmm.util.event.EventListener#handleEvent(Event) receive}
 *        the sent events.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class AbstractSynchronizedEventSource<E extends Event, L extends EventListener<E>> extends
    AbstractEventSource<E, L> {

  /** the lock used to synchronize */
  private final Object lock;

  /**
   * The constructor. 
   */
  public AbstractSynchronizedEventSource() {

    this(new ArrayList<L>());
  }

  /**
   * The constructor. 
   * 
   * @param listeners 
   *        is the list used to store the listeners.
   */
  protected AbstractSynchronizedEventSource(List<L> listeners) {

    super(listeners);
    this.lock = listeners;
  }  
  
  /**
   * {@inheritDoc}
   */
  @Override
  public void addListener(L listener) {

    synchronized (this.lock) {
      super.addListener(listener);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void removeListener(L listener) {

    synchronized (this.lock) {
      super.removeListener(listener);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fireEvent(E event) {

    synchronized (this.lock) {
      super.fireEvent(event);
    }
  }

}
