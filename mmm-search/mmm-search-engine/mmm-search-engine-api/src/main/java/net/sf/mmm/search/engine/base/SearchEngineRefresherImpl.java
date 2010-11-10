/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.search.engine.base;

import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import net.sf.mmm.search.engine.api.ManagedSearchEngine;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;
import net.sf.mmm.util.concurrent.base.SimpleExecutor;
import net.sf.mmm.util.nls.api.NlsIllegalStateException;
import net.sf.mmm.util.value.api.ValueOutOfRangeException;

/**
 * This class is a component that runs a {@link Thread} that
 * {@link ManagedSearchEngine#refresh() refreshes} the
 * {@link ManagedSearchEngine search-engine} every
 * {@link #setRefreshDelayInSeconds(int) delay} seconds.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
@Singleton
@Named
public class SearchEngineRefresherImpl extends AbstractLoggableComponent implements SearchEngineRefresher,
    Runnable {

  /** The default {@link #setRefreshDelayInSeconds(int) refresh-delay}. */
  private static final int DEFAULT_REFRESH_DELAY_IN_SECONDS = 5 * 60;

  /** The minimum value allowed for {@link #refreshDelayInSeconds}. */
  private static final int MIN_DELAY = 1;

  /** The maximum sleep delay in seconds. */
  private static final int MAX_SLEEP_SECONDS = 5;

  /** The maximum sleep delay in milliseconds. */
  private static final long MAX_SLEEP_MILLISECONDS = TimeUnit.SECONDS.toMillis(MAX_SLEEP_SECONDS);

  /** @see #getExecutor() */
  private Executor executor;

  /** @see #getRefreshDelaySeconds() */
  private int refreshDelayInSeconds;

  /** @see #startup() */
  private boolean active;

  /** @see #close() */
  private boolean shutdown;

  /** The {@link Set} of {@link ManagedSearchEngine}s. */
  private Set<ManagedSearchEngine> searchEngineSet;

  /**
   * The constructor.
   */
  public SearchEngineRefresherImpl() {

    super();
    this.refreshDelayInSeconds = DEFAULT_REFRESH_DELAY_IN_SECONDS;
    this.searchEngineSet = new CopyOnWriteArraySet<ManagedSearchEngine>();
    this.shutdown = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  protected void doInitialize() {

    super.doInitialize();
    ValueOutOfRangeException.checkRange(Integer.valueOf(this.refreshDelayInSeconds),
        Integer.valueOf(MIN_DELAY), Integer.valueOf(Integer.MAX_VALUE), getClass().getSimpleName()
            + ".refreshDelayInSeconds");
    if (this.executor == null) {
      this.executor = new SimpleExecutor();
    }
  }

  /**
   * This method will initialize and startup this refresher. On the first call
   * of this method a new thread will be started, that periodically performs a
   * refresh.<br/>
   * Multiple calls of this method have no further effect unless the refresher
   * is {@link #close() closed}.<br/>
   * <b>NOTE:</b><br>
   * This is intentionally NOT performed automatically via
   * {@link net.sf.mmm.util.component.base.AbstractComponent#initialize()} so
   * the startup only happens if explicitly required and not accidently because
   * this component if found and managed by some container.
   */
  public synchronized void startup() {

    if (this.shutdown) {
      throw new NlsIllegalStateException();
    }
    if (!this.active) {
      getLogger().info("starting " + getThreadName() + "...");
      Thread thread = new Thread(this);
      thread.setName(getThreadName());
      this.executor.execute(thread);
      this.active = true;
    }
  }

  /**
   * {@inheritDoc}
   */
  public void addSearchEngine(ManagedSearchEngine searchEngine) {

    this.searchEngineSet.add(searchEngine);
    startup();
  }

  /**
   * {@inheritDoc}
   */
  public void removeSearchEngine(ManagedSearchEngine searchEngine) {

    this.searchEngineSet.remove(searchEngine);
  }

  /**
   * {@inheritDoc}
   */
  public void close() {

    this.shutdown = true;
  }

  /**
   * This method gets the thread-name.
   * 
   * @return the thread-name for debugging.
   */
  protected String getThreadName() {

    return SearchEngineRefresherImpl.class.getSimpleName() + "-Thread";
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

    try {
      getLogger().info(getThreadName() + " started.");
      while (!this.shutdown) {
        int seconds = this.refreshDelayInSeconds;
        while ((seconds > 0) && (!this.shutdown)) {
          // we only sleep for short times so we can shutdown quickly if
          // shutdown
          // gets false. As we use an Executor that might be a thread-pool
          if (seconds > MAX_SLEEP_SECONDS) {
            sleep(MAX_SLEEP_MILLISECONDS);
            seconds = seconds - MAX_SLEEP_SECONDS;
          } else {
            sleep(seconds * TimeUnit.SECONDS.toMillis(seconds));
            seconds = 0;
          }
        }
        if (!this.shutdown) {
          for (ManagedSearchEngine engine : this.searchEngineSet) {
            engine.refresh();
          }
        }
      }
      getLogger().info(getThreadName() + " ended.");
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
   * @param refreshDelaySeconds is the refreshDelaySeconds to set
   */
  public void setRefreshDelayInSeconds(int refreshDelaySeconds) {

    this.refreshDelayInSeconds = refreshDelaySeconds;
  }

}
