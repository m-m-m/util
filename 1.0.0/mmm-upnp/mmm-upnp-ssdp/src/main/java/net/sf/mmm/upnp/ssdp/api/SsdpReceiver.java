/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.upnp.ssdp.api;

/**
 * This is the interface for a receiver of
 * {@link net.sf.mmm.upnp.ssdp.api.SsdpRequest}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SsdpReceiver {

  /**
   * This method adds the given <code>listener</code> to this receiver. After
   * the registration all received requests will be sent to the
   * <code>listener</code>.
   * 
   * @param listener is the listener to add.
   */
  void addListener(SsdpListener listener);

  /**
   * This method adds the given <code>listener</code>
   * 
   * @param listener
   */
  void removeListener(SsdpListener listener);

}
