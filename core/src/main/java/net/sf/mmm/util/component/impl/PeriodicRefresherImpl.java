/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.component.impl;

import java.io.Closeable;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

import net.sf.mmm.util.component.api.PeriodicRefresher;
import net.sf.mmm.util.component.api.Refreshable;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.concurrent.base.SimpleExecutor;
import net.sf.mmm.util.exception.api.NlsIllegalStateException;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This is the implementation of {@link PeriodicRefresher}.
 *
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class PeriodicRefresherImpl extends AbstractLoggableComponent implements PeriodicRefresher, Runnable, Closeable {

  /** The default {@link #setRefreshDelayInSeconds(int) refresh-delay}. */
  private static final int DEFAULT_REFRESH_DELAY_IN_SECONDS = 5 * 60;

  /** The minimum value allowed for {@link #refreshDelayInSeconds}. */
  private static final Integer MIN_DELAY = Integer.valueOf(1);

  private Executor executor;

  private int refreshDelayInSeconds;

  private boolean active;

  private volatile boolean shutdown;

  /** The {@link Set} of {@link Refreshable}s. */
  private Set<Refreshable> refreshableSet;

  private Thread refreshThread;

  /**
   * The constructor.
   */
  public PeriodicRefresherImpl() {

    super();
    this.refreshDelayInSeconds = DEFAULT_REFRESH_DELAY_IN_SECONDS;
    this.refreshableSet = new CopyOnWriteArraySet<>();
    this.shutdown = false;
  }

  @Override
  protected void doInitialize() {

    super.doInitialize();

    if (this.executor == null) {
      this.executor = new SimpleExecutor();
    }
  }

  /**
   * This method will initialize and startup this refresher. On the first call of this method a new thread
   * will be started, that periodically performs a refresh. <br>
   * Multiple calls of this method have no further effect unless the refresher is {@link #close() closed}.
   * <br>
   * <b>NOTE:</b><br>
   * This is intentionally NOT performed automatically via
   * {@link net.sf.mmm.util.component.base.AbstractComponent#initialize()} so the startup only happens if
   * explicitly required and not accidently because this component if found and managed by some container.
   */
  public void startup() {

    if (this.shutdown) {
      throw new NlsIllegalStateException();
    }
    synchronized (this) {
      if (this.active) {
        return;
      }
      getLogger().info("starting " + getThreadName() + "...");
      Thread thread = new Thread(this);
      thread.setName(getThreadName());
      this.executor.execute(thread); // NOSONAR
      this.active = true;
    }
  }

  @Override
  public void addRefreshable(Refreshable refreshable) {

    this.refreshableSet.add(refreshable);
    startup();
  }

  @Override
  public void removeRefreshable(Refreshable refreshable) {

    this.refreshableSet.remove(refreshable);
  }

  @Override
  @PreDestroy
  public void close() {

    this.shutdown = true;
    if (this.refreshThread != null) {
      this.refreshThread.interrupt();
    }
  }

  /**
   * This method gets the thread-name.
   *
   * @return the thread-name for debugging.
   */
  protected String getThreadName() {

    return PeriodicRefresherImpl.class.getSimpleName() + "-Thread";
  }

  @Override
  public void run() {

    this.refreshThread = Thread.currentThread();
    try {
      getLogger().info(getThreadName() + " started.");
      while (!this.shutdown) {
        long sleepTime = TimeUnit.SECONDS.toMillis(this.refreshDelayInSeconds);
        Thread.sleep(sleepTime);
        if (!this.shutdown) {
          for (Refreshable engine : this.refreshableSet) {
            engine.refresh();
          }
        }
      }
      getLogger().info(getThreadName() + " ended.");
    } catch (InterruptedException e) {
      if (!this.shutdown) {
        getLogger().error("Illegal interrupt!", e);
      }
    } catch (RuntimeException e) {
      getLogger().error(getThreadName() + " crashed!", e);
    }
    this.active = false;
  }

  /**
   * @return the executor
   */
  protected Executor getExecutor() {

    return this.executor;
  }

  /**
   * @param executor is the executor to set
   */
  @Inject
  public void setExecutor(Executor executor) {

    getInitializationState().requireNotInitilized();
    this.executor = executor;
  }

  /**
   * @return the refreshDelaySeconds
   */
  public int getRefreshDelaySeconds() {

    return this.refreshDelayInSeconds;
  }

  /**
   * This method sets the refresh-delay in seconds. A reasonable value should be at least 5 seconds but better
   * in the range of minutes.
   *
   * @param refreshDelayInSeconds is the refreshDelayInSeconds to set
   */
  public void setRefreshDelayInSeconds(int refreshDelayInSeconds) {

    ValueOutOfRangeException.checkRange(Integer.valueOf(refreshDelayInSeconds), MIN_DELAY,
        Integer.valueOf(Integer.MAX_VALUE), getClass().getSimpleName() + ".refreshDelayInSeconds");
    this.refreshDelayInSeconds = refreshDelayInSeconds;
  }

}
