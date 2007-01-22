/* $Id$ */
package net.sf.mmm.upnp.ssdp.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

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
   */
  public SimpleSsdpReceiver() {

    super();
  }

  /**
   * 
   * @throws IOException
   *         if the operation failed I/O problem occured.
   */
  @PostConstruct
  public void initialize() throws IOException {

    connect();
  }

  /**
   * 
   * @throws IOException
   */
  @PreDestroy
  public void dispose() throws IOException {

    disconnect();
  }

}
