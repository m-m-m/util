/* $Id$ */
package net.sf.mmm.upnp.ssdp.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import net.sf.mmm.upnp.ssdp.api.SsdpListener;
import net.sf.mmm.upnp.ssdp.api.SsdpRequest;

/**
 * This is a simple implemetation of the
 * {@link net.sf.mmm.upnp.ssdp.api.SsdpReceiver} interface. It opens the
 * multicast-socket once when {@link #initialize() initialized} and closes it
 * when it is {@link #dispose() disposed}. This way the socket stays open even
 * if there is no listener
 * {@link #addListener(net.sf.mmm.upnp.ssdp.api.SsdpListener) registered}.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SimpleSsdpReceiver extends BasicSsdpReceiver {

  /**
   * The constructor
   * 
   * @see #initialize()
   * @see #dispose()
   */
  public SimpleSsdpReceiver() {

    super();
  }

  /**
   * This method initializes this object. It needs to be called after
   * construction and before the object can be used.
   * 
   * @throws IOException
   *         if the operation failed with an I/O problem.
   */
  @PostConstruct
  public void initialize() throws IOException {

    connect();
  }

  /**
   * This method disposes this object. It should be called when this object is
   * NOT used anymore. It will free allocated resources.
   * 
   * @throws IOException
   *         if the operation failed with an I/O problem.
   */
  @PreDestroy
  public void dispose() throws IOException {

    disconnect();
  }

  /**
   * @see net.sf.mmm.upnp.ssdp.base.AbstractSsdpReceiver#addListener(net.sf.mmm.upnp.ssdp.api.SsdpListener)
   */
  @Override
  public synchronized void addListener(SsdpListener listener) {

    super.addListener(listener);
  }

  /**
   * @see net.sf.mmm.upnp.ssdp.base.AbstractSsdpReceiver#notifyListeners(net.sf.mmm.upnp.ssdp.api.SsdpRequest)
   */
  @Override
  protected synchronized void notifyListeners(SsdpRequest request) {

    super.notifyListeners(request);
  }

  /**
   * @see net.sf.mmm.upnp.ssdp.base.AbstractSsdpReceiver#removeListener(net.sf.mmm.upnp.ssdp.api.SsdpListener)
   */
  @Override
  public synchronized void removeListener(SsdpListener listener) {

    super.removeListener(listener);
  }

}
