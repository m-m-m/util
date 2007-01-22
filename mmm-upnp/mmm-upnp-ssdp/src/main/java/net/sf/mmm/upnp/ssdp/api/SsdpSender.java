/* $Id$ */
package net.sf.mmm.upnp.ssdp.api;


/**
 * This is the interface for a sender of {@link SsdpRequest}s.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SsdpSender {

  /**
   * This method sents the given <code>request</code> as multicast via UDP.
   * 
   * @param request
   */
  void send(SsdpRequest request);

}
