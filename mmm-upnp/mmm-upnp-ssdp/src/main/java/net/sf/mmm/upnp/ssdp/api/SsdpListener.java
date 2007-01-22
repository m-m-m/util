/* $Id$ */
package net.sf.mmm.upnp.ssdp.api;


/**
 * This is the interface of a listener that wans to
 * {@link #receive(SsdpRequest) receive} {@link SsdpRequest}s.
 * 
 * @see SsdpReceiver
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public interface SsdpListener {

  /**
   * This method is called when a {@link SsdpRequest SSDP-request} was received.
   * 
   * @param request
   *        is the received request.
   */
  void receive(SsdpRequest request);

}
