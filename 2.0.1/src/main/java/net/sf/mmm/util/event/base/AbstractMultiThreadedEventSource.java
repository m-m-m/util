/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event.base;

import java.util.concurrent.Executor;

import javax.inject.Inject;

import net.sf.mmm.util.component.api.AlreadyInitializedException;
import net.sf.mmm.util.concurrent.base.SimpleExecutor;
import net.sf.mmm.util.event.api.Event;
import net.sf.mmm.util.event.api.EventListener;

/**
 * This class extends {@link AbstractSynchronizedEventSource} with the ability
 * to send events asynchronous in separate {@link Thread}s.
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
public abstract class AbstractMultiThreadedEventSource<E extends Event, L extends EventListener<E>>
    extends AbstractSynchronizedEventSource<E, L> {

  /** @see #getExecutor() */
  private Executor executor;

  /**
   * The constructor.
   */
  public AbstractMultiThreadedEventSource() {

    super();
    this.executor = null;
  }

  /**
   * This method sets the {@link #getExecutor() executor} to use.
   * 
   * @param threadPool is used to dispatch events in separate threads.
   */
  @Inject
  public void setExecutor(Executor threadPool) {

    if (this.executor != null) {
      throw new AlreadyInitializedException();
    }
    this.executor = threadPool;
  }

  /**
   * This method gets the {@link Executor} used to run asynchronous tasks. It
   * may use a thread-pool.
   * 
   * @return the executor.
   */
  public Executor getExecutor() {

    return this.executor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.executor == null) {
      this.executor = SimpleExecutor.INSTANCE;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void fireEvent(final E event, final L listener) {

    this.executor.execute(new Runnable() {

      public void run() {

        listener.handleEvent(event);
      }
    });
  }

}
