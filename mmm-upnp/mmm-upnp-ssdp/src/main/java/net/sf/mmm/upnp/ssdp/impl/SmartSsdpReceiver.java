/* $Id$ 
 * Copyright The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.upnp.ssdp.impl;

import java.io.IOException;

import net.sf.mmm.upnp.ssdp.api.SsdpListener;
import net.sf.mmm.upnp.ssdp.api.SsdpRequest;

/**
 * This is an implemetation of the {@link net.sf.mmm.upnp.ssdp.api.SsdpReceiver}
 * interface that only keeps the multicast-socket open while there is at least
 * one {@link net.sf.mmm.upnp.ssdp.api.SsdpListener}
 * {@link #addListener(net.sf.mmm.upnp.ssdp.api.SsdpListener) registered}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SmartSsdpReceiver extends BasicSsdpReceiver {

  /**
   * The constructor
   */
  public SmartSsdpReceiver() {

    super();
  }

  /**
   * @see net.sf.mmm.upnp.ssdp.base.AbstractSsdpReceiver#addListener(net.sf.mmm.upnp.ssdp.api.SsdpListener)
   */
  @Override
  public synchronized void addListener(SsdpListener listener) {

    super.addListener(listener);
    if (!isConnected()) {
      try {
        connect();
      } catch (IOException e) {
        // TODO:
        throw new IllegalStateException(e);
      }
    }
  }

  /**
   * @see net.sf.mmm.upnp.ssdp.base.AbstractSsdpReceiver#removeListener(net.sf.mmm.upnp.ssdp.api.SsdpListener)
   */
  @Override
  public synchronized void removeListener(SsdpListener listener) {

    super.removeListener(listener);
    if (getListenerCount() == 0) {
      try {
        disconnect();
      } catch (IOException e) {
        // TODO:
        throw new IllegalStateException(e);
      }
    }
  }

  /**
   * @see net.sf.mmm.upnp.ssdp.base.AbstractSsdpReceiver#notifyListeners(net.sf.mmm.upnp.ssdp.api.SsdpRequest)
   */
  @Override
  protected synchronized void notifyListeners(SsdpRequest request) {
  
    super.notifyListeners(request);
  }
  
}
