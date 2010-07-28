/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.concurrent.base;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

import javax.inject.Named;
import javax.inject.Singleton;

/**
 * This is a very simple implementation of the {@link Executor} interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SimpleExecutor implements Executor {

  /**
   * This is the singleton instance of this {@link SimpleExecutor}.
   */
  public static final SimpleExecutor INSTANCE = new SimpleExecutor();

  /** @see #execute(Runnable) */
  private final ThreadFactory threadFactory;

  /**
   * The constructor.
   */
  public SimpleExecutor() {

    super();
    this.threadFactory = Executors.defaultThreadFactory();
  }

  /**
   * The constructor.
   * 
   * @param factory is the thread-factory to use.
   */
  public SimpleExecutor(ThreadFactory factory) {

    super();
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
