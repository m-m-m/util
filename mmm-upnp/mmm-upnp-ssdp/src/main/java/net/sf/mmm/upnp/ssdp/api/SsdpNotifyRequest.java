/* $Id$ */
package net.sf.mmm.upnp.ssdp.api;

/**
 * This is class represents an SSDP notification request that advertises an UPnP
 * device.
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SsdpNotifyRequest extends SsdpRequest {

  /**
   * The constructor
   * 
   */
  public SsdpNotifyRequest() {

    super();
    setMethod(METHOD_NOTIFY);
  }

}
