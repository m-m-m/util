/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.client.base.busy;

import net.sf.mmm.client.ui.api.busy.BusyManager;
import net.sf.mmm.client.ui.api.busy.BusySession;
import net.sf.mmm.util.component.base.AbstractLoggableComponent;

/**
 * This is the abstract base implementation of {@link BusyManager}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 * @since 1.0.0
 */
public abstract class AbstractBusyManager extends AbstractLoggableComponent implements BusyManager {

  /** @see #isBusy() */
  private volatile boolean busy;

  /** The current {@link BusySessionImpl} or <code>null</code> for no open session. */
  private BusySessionImpl currentSession;

  /** The top-level {@link BusySessionImpl} or <code>null</code> for no open session. */
  private BusySessionImpl toplevelSession;

  /**
   * The constructor.
   */
  public AbstractBusyManager() {

    super();
    this.busy = false;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isBusy() {

    return this.busy;
  }

  /**
   * This method is called whenever the client changes from idle to {@link #isBusy() busy}. It has to
   * implement the client specific behavior.
   */
  protected abstract void setBusy();

  /**
   * This method is called whenever the client changes from {@link #isBusy() busy} to idle. It has to
   * implement the client specific behavior.
   */
  protected abstract void setIdle();

  /**
   * {@inheritDoc}
   */
  @Override
  public BusySession start(String id) {

    if (!this.busy) {
      setBusy();
      this.busy = true;
    }
    this.currentSession = newBusySession(id);
    if (this.toplevelSession == null) {
      this.toplevelSession = this.currentSession;
    }
    return this.currentSession;
  }

  /**
   * @param session is the {@link BusySessionImpl} that has been {@link BusySession#stop() stopped}.
   */
  private void stopSession(BusySessionImpl session) {

    if (session != this.currentSession) {
      getLogger().debug(
          "Busy session (" + session.id + ") has been stopped, but is NOT the current session ("
              + this.currentSession.id + ")!");
    } else {
      this.currentSession = this.currentSession.parent;
    }
    if (this.toplevelSession == session) {
      this.busy = false;
      setIdle();
      this.toplevelSession = null;
    }
  }

  /**
   * This method creates a new {@link BusySessionImpl}.
   * 
   * @param id the {@link BusySession#getId() ID}.
   * @return the new {@link BusySessionImpl}.
   */
  protected BusySessionImpl newBusySession(String id) {

    return new BusySessionImpl(id, this.currentSession);
  }

  /**
   * This inner class is the implementation of {@link BusySession}.
   */
  private class BusySessionImpl implements BusySession {

    /** @see #getId() */
    private final String id;

    /** The parent session or <code>null</code> if this is the top-level session. */
    private final BusySessionImpl parent;

    /** @see #isStopped() */
    private volatile boolean stopped;

    /**
     * The constructor.
     * 
     * @param id - see {@link #getId()}.
     * @param parent is the parent {@link BusySessionImpl} or <code>null</code> if this is the top-level
     *        session.
     */
    public BusySessionImpl(String id, BusySessionImpl parent) {

      super();
      this.id = id;
      this.parent = parent;
      this.stopped = false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getId() {

      return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void stop() {

      if (this.stopped) {
        getLogger().warn("Busy session (" + this.id + ") has been stopped more than once!");
        return;
      }
      if (isStopped()) {
        getLogger().warn("Busy session (" + this.id + ") has been stopped after one of its parents!");
        this.stopped = true;
        return;
      }
      stopSession(this);
      this.stopped = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isStopped() {

      if (this.stopped) {
        return true;
      }
      if (this.parent != null) {
        return this.parent.isStopped();
      }
      return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {

      return "BusySession[" + this.id + "]";
    }

  }

}
