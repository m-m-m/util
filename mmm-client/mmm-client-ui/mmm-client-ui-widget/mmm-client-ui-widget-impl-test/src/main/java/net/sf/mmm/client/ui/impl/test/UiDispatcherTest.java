/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.ui.impl.test;

import java.util.concurrent.Callable;
import java.util.concurrent.Executor;

import javax.inject.Inject;
import javax.inject.Named;

import net.sf.mmm.client.ui.base.widget.AbstractUiDispatcher;

/**
 * This is the implementation of {@link net.sf.mmm.client.ui.api.UiDispatcher} for testing.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Named
public class UiDispatcherTest extends AbstractUiDispatcher {

  /** @see #getExecutor() */
  private Executor executor;

  /**
   * The constructor.
   */
  public UiDispatcherTest() {

    super();
  }

  /**
   * @return the executor
   */
  public Executor getExecutor() {

    return this.executor;
  }

  /**
   * @param executor is the {@link Executor} to {@link Inject}.
   */
  @Inject
  public void setExecutor(Executor executor) {

    getInitializationState().requireNotInitilized();
    this.executor = executor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean sleep() {

    try {
      Thread.sleep(1000);
      return false;
    } catch (InterruptedException e) {
      throw new IllegalStateException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void invokeAsynchron(final Runnable task) {

    this.executor.execute(task);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void invokeTimer(final Callable<Boolean> task, final int delayMilliseconds) {

    Runnable runnable = new Runnable() {

      private long time = System.currentTimeMillis();

      @Override
      public void run() {

        boolean todo = true;
        while (todo) {
          long delay = System.currentTimeMillis() - this.time;
          if (delay > delayMilliseconds) {
            try {
              Boolean repeat = task.call();
              if (Boolean.TRUE.equals(repeat)) {
                this.time = this.time + delayMilliseconds;
              } else {
                todo = false;
              }
            } catch (Exception e) {
              getLogger().error("Timer failed.", e);
              todo = false;
            }
          }
          try {
            Thread.sleep(100);
          } catch (InterruptedException e) {
            getLogger().error("Timer thread was interrupted.", e);
          }
        }
      }
    };
    invokeAsynchron(runnable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void invokeTimer(final Runnable task, int delayMilliseconds) {

    Callable<Boolean> callable = new Callable<Boolean>() {

      /**
       * {@inheritDoc}
       */
      @Override
      public Boolean call() throws Exception {

        task.run();
        return Boolean.FALSE;
      }
    };
    invokeTimer(callable, delayMilliseconds);
  }

}
