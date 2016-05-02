/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.concurrent.base;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This is an advanced {@link java.util.concurrent.Executor} that uses a {@link Thread}-pool. It is about the
 * same as {@link java.util.concurrent.Executors#newCachedThreadPool()} and inherits all its implementation
 * from {@link ThreadPoolExecutor}. The only reason was to make it easier to be managed as component in an
 * IoC-Container such as spring.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.2
 */
public class CachedThreadPoolExecutor extends ThreadPoolExecutor {

  /**
   * The constructor.
   */
  public CachedThreadPoolExecutor() {

    super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
  }

  /**
   * The constructor.
   * 
   * @param threadFactory is the {@link ThreadFactory} used to create new {@link Thread}s.
   */
  public CachedThreadPoolExecutor(ThreadFactory threadFactory) {

    super(0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), threadFactory);
  }

}
