/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.upnp.ssdp.base;

import java.util.ArrayList;
import java.util.List;

import net.sf.mmm.upnp.ssdp.api.SsdpListener;
import net.sf.mmm.upnp.ssdp.api.SsdpReceiver;
import net.sf.mmm.upnp.ssdp.api.SsdpRequest;

/**
 * This is the abstract base implementation of the {@link SsdpReceiver}
 * interface.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public abstract class AbstractSsdpReceiver implements SsdpReceiver {

  /** @see #addListener(SsdpListener) */
  private final List<SsdpListener> listeners;

  /**
   * The constructor
   */
  public AbstractSsdpReceiver() {

    super();
    this.listeners = new ArrayList<SsdpListener>();
  }

  /**
   * {@inheritDoc}
   */
  public void addListener(SsdpListener listener) {

    this.listeners.add(listener);
  }

  /**
   * {@inheritDoc}
   */
  public void removeListener(SsdpListener listener) {

    this.listeners.remove(listener);
  }

  /**
   * This method gets the number of listeners currently
   * {@link #addListener(SsdpListener) registered}.
   * 
   * @return the listener count.
   */
  public int getListenerCount() {

    return this.listeners.size();
  }

  /**
   * This method {@link SsdpListener#receive(SsdpRequest) sends} the given
   * <code>request</code> to all {@link #addListener(SsdpListener) registered}
   * {@link SsdpListener listeners}.
   * 
   * @param request
   *        is the request to be sent to the listeners.
   */
  protected void notifyListeners(SsdpRequest request) {

    for (SsdpListener listener : this.listeners) {
      try {
        listener.receive(request);
      } catch (Exception e) {
        handleListenerException(e);
      }
    }
  }

  /**
   * This method is called when a listener throws an <code>exception</code>
   * while {@link SsdpListener#receive(SsdpRequest) receiving} a
   * {@link SsdpRequest request}.<br>
   * This implementation ignores the given <code>exception</code>. Override
   * to change this behaviour.
   * 
   * @param exception
   *        is the catched exception.
   */
  protected void handleListenerException(Exception exception) {

  }

}
