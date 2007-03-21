/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * This is a very simple implementation of the {@link Executor} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleExecutor implements Executor {

  /** @see #getThreadFactory() */
  private ThreadFactory threadFactory;

  /**
   * The constructor
   */
  public SimpleExecutor() {

    super();
    this.threadFactory = Executors.defaultThreadFactory();
  }

  /**
   * The constructor
   * 
   * @param factory
   *        is the thread-factory to use.
   */
  public SimpleExecutor(ThreadFactory factory) {

    super();
    this.threadFactory = factory;
  }

  /**
   * @return the threadFactory
   */
  public ThreadFactory getThreadFactory() {

    return this.threadFactory;
  }

  /**
   * @param factory
   *        the threadFactory to set
   */
  public void setThreadFactory(ThreadFactory factory) {

    this.threadFactory = factory;
  }

  /**
   * {@inheritDoc}
   */
  public void execute(Runnable command) {

    Thread thread = this.threadFactory.newThread(command);
    thread.start();
  }

}
