/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.upnp.ssdp.api;

import java.io.IOException;

/**
 * This is the interface for a sender of {@link SsdpRequest}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SsdpSender {

  /**
   * This method sends the given <code>request</code> as multicast via UDP.
   * 
   * @param request is the request to send.
   * @throws IOException if the operation failed with an I/O problem.
   */
  void send(SsdpRequest request) throws IOException;

}
