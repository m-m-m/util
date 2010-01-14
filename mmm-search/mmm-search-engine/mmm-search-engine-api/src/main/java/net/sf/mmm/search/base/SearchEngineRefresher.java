/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.base;

import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.util.component.base.AbstractLoggable;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This class is a component that runs a {@link Thread} that
 * {@link ManagedSearchEngine#refresh() refreshes} the
 * {@link ManagedSearchEngine search-engine} in a every
 * {@link #setRefreshDelayInSeconds(int) delay} seconds.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public class SearchEngineRefresher extends AbstractLoggable implements Runnable {

  /** The minimum value allowed for {@link #refreshDelayInSeconds}. */
  private static final int MIN_DELAY = 1;

  /** The maximum sleep delay in seconds. */
  private static final int MAX_SLEEP_SECONDS = 5;

  /** The maximum sleep delay in milliseconds. */
  private static final long MAX_SLEEP_MILLISECONDS = TimeUnit.SECONDS.toMillis(MAX_SLEEP_SECONDS);

  /** @see #getSearchEngine() */
  private ManagedSearchEngine searchEngine;

  /** @see #getExecutor() */
  private Executor executor;

  /** @see #getRefreshDelaySeconds() */
  private int refreshDelayInSeconds;

  /** @see #dispose() */
  private boolean active;

  /**
   * The constructor.
   */
  public SearchEngineRefresher() {

    super();
    this.refreshDelayInSeconds = 5 * 60;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    if (this.refreshDelayInSeconds < MIN_DELAY) {
      throw new ValueOutOfRangeException(Integer.valueOf(this.refreshDelayInSeconds), Integer
          .valueOf(MIN_DELAY), Integer.valueOf(Integer.MAX_VALUE));
    }
    this.active = true;
    getLogger().info("starting " + getThreadName() + "...");
    Thread thread = new Thread(this);
    thread.setName(getThreadName());
    this.executor.execute(thread);
  }

  /**
   * This method disposes this object. The refresh-thread will be terminated.
   */
  @PreDestroy
  public void dispose() {

    this.active = false;
  }

  /**
   * This method gets the thread-name.
   * 
   * @return the thread-name for debugging.
   */
  private String getThreadName() {

    return SearchEngineRefresher.class.getSimpleName() + "-Thread";
  }

  /**
   * This method sleeps the given number of <code>milliseconds</code>.
   * 
   * @param milliseconds is the sleep-delay.
   */
  private void sleep(long milliseconds) {

    try {
      Thread.sleep(milliseconds);
    } catch (InterruptedException e) {
      getLogger().error(getThreadName() + " was interruped!", e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public void run() {

    getLogger().info(getThreadName() + " started.");
    while (this.active) {
      int seconds = this.refreshDelayInSeconds;
      while ((seconds > 0) && (this.active)) {
        if (seconds > MAX_SLEEP_SECONDS) {
          sleep(MAX_SLEEP_MILLISECONDS);
          seconds = seconds - MAX_SLEEP_SECONDS;
        } else {
          sleep(seconds * TimeUnit.SECONDS.toMillis(seconds));
          seconds = 0;
        }
      }
      if (this.active) {
        this.searchEngine.refresh();
      }
    }
    getLogger().info(getThreadName() + " ended.");
  }

  /**
   * @return the searchEngine
   */
  public ManagedSearchEngine getSearchEngine() {

    return this.searchEngine;
  }

  /**
   * @param searchEngine is the searchEngine to set
   */
  @Resource
  public void setSearchEngine(ManagedSearchEngine searchEngine) {

    this.searchEngine = searchEngine;
  }

  /**
   * @return the executor
   */
  public Executor getExecutor() {

    return this.executor;
  }

  /**
   * @param executor is the executor to set
   */
  @Resource
  public void setExecutor(Executor executor) {

    this.executor = executor;
  }

  /**
   * @return the refreshDelaySeconds
   */
  public int getRefreshDelaySeconds() {

    return this.refreshDelayInSeconds;
  }

  /**
   * @param refreshDelaySeconds is the refreshDelaySeconds to set
   */
  public void setRefreshDelayInSeconds(int refreshDelaySeconds) {

    this.refreshDelayInSeconds = refreshDelaySeconds;
  }

}
