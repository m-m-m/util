/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.event;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * This class extends {@link AbstractSynchronizedEventSource} with the ability
 * to send events asynchronous in separate threads.
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
public class AbstractMultiThreadedEventSource<E extends Event, L extends EventListener<E>> extends
    AbstractSynchronizedEventSource<E, L> {

  /** the thread-pool */
  private Executor executor;

  /**
   * The constructor
   */
  public AbstractMultiThreadedEventSource() {

    super();
  }

  /**
   * This method sets the thread-pool to use.
   * 
   * @param threadPool
   *        is used to dispatch events in separate threads.
   */
  @Resource
  public void setThreadPool(Executor threadPool) {

    assert (this.executor == null);
    this.executor = threadPool;
  }

  /**
   * This method gets the thread-pool
   * 
   * @return the thread-pool.
   */
  public Executor getThreadPool() {

    return this.executor;
  }

  /**
   * This method initializes this class.
   */
  @PostConstruct
  public void initialize() {

    if (this.executor == null) {
      this.executor = Executors.newCachedThreadPool();
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
