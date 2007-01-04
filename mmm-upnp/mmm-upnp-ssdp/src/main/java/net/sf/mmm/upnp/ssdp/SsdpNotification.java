/* $Id$ */
package net.sf.mmm.upnp.ssdp;

/**
 * TODO: this class ...
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SsdpNotification {

  private String uuid;

  private String location;

  /**
   * The constructor
   */
  public SsdpNotification() {

    super();
  }

  /**
   * @return the uuid
   */
  public String getUuid() {

    return this.uuid;
  }

  /**
   * @param uuid
   *        the uuid to set
   */
  public void setUuid(String uuid) {

    this.uuid = uuid;
  }

  /**
   * This method gets the location what is the URL where to get the description
   * XML of the service.
   * 
   * @return the location URL.
   */
  public String getLocation() {

    return this.location;
  }

  /**
   * This method sets the {@link #getLocation() location}.
   * 
   * @param locationUrl
   *        the location URL to set
   */
  public void setLocation(String locationUrl) {

    this.location = locationUrl;
  }

}
