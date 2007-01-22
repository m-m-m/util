/* $Id$ */
package net.sf.mmm.upnp.ssdp.api;

import net.sf.mmm.util.http.HttpRequest;

/**
 * This class represents an SSDP request. It is a specific HTTP-Request that is
 * transmitted via an multicast over UDP.
 * 
 * @see HttpRequest#METHOD_NOTIFY
 * @see HttpRequest#METHOD_M_SEARCH
 * 
 * @author Joerg Hohwiller (hohwille at users.sourceforge.net)
 */
public class SsdpRequest extends HttpRequest {

  /** the URI to use for SSDP */
  private static final String SSDP_URI = "*";

  /** the SSDP multicast address */
  public static final String MULTICAST_ADDRESS = "239.255.255.250";

  /** the SSDP multicast port */
  public static final int MULTICAST_PORT = 1900;

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>NT</code>.
   */
  public static final String HEADER_PROPERTY_NOTIFICATION_TYPE = "NT";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>NTS</code>.
   */
  public static final String HEADER_PROPERTY_NOTIFICATION_SUB_TYPE = "NTS";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>USN</code>.
   */
  public static final String HEADER_PROPERTY_UNIQUE_SERVICE_NAME = "USN";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>MAN</code>.
   */
  public static final String HEADER_PROPERTY_MAN = "MAN";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>EXT</code>.
   */
  public static final String HEADER_PROPERTY_EXT = "EXT";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>MAN</code>.
   */
  public static final String HEADER_PROPERTY_MAXIMUM_WAIT = "MX";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>ST</code>.
   */
  public static final String HEADER_PROPERTY_SEARCH_TARGET = "ST";

  /** device available (register advertisment) */
  public static final String NTS_SSDP_ALIVE = "ssdp:alive";

  /** device unavailable (de-register advertisment) */
  public static final String NTS_SSDP_BYEBYE = "ssdp:byebye";

  /** proper man header */
  public static final String MAN_SSDP_DISCOVER = "\"ssdp:discover\"";

  /** st header */
  public static final String ST_SSDP_ALL = "ssdp:all";

  /**
   * This method sets the {@link #HEADER_PROPERTY_SEARCH_TARGET search-target}.
   * 
   * @param st
   *        is the search-target to set.
   */
  public void setSearchTarget(String st) {

    setHeaderProperty(HEADER_PROPERTY_SEARCH_TARGET, st);
  }

  /**
   * This method gets the {@link #HEADER_PROPERTY_SEARCH_TARGET search-target}.
   * 
   * @return the search-target or <code>null</code> if NOT set.
   */
  public String getSearchTarget() {

    return getHeaderProperty(HEADER_PROPERTY_SEARCH_TARGET);
  }

  /**
   * The constructor
   */
  public SsdpRequest() {

    super();
    setMethod(METHOD_NOTIFY);
    setUri(SSDP_URI);
    setHeaderProperty(HEADER_PROPERTY_HOST, MULTICAST_ADDRESS + ":" + MULTICAST_PORT);
    setNotificationSubType(NTS_SSDP_ALIVE);
  }

  /**
   * @see net.sf.mmm.util.http.HttpMessage#createHeaderNameHash(java.lang.String)
   */
  @Override
  protected Object createHeaderNameHash(String name) {
  
    return name.toUpperCase();
  }

  /**
   * This method gets the {@link #HEADER_PROPERTY_NOTIFICATION_SUB_TYPE NTS}.
   * 
   * @see #NTS_SSDP_ALIVE
   * @see #NTS_SSDP_BYEBYE
   * 
   * @return the notification sub type.
   */
  public String getNotificationSubType() {

    return getHeaderProperty(HEADER_PROPERTY_NOTIFICATION_SUB_TYPE);
  }

  /**
   * This method sets the {@link #HEADER_PROPERTY_NOTIFICATION_SUB_TYPE NTS}.
   * 
   * @see #NTS_SSDP_ALIVE
   * @see #NTS_SSDP_BYEBYE
   * 
   * @param nts
   *        the notification sub type to set.
   */
  public void setNotificationSubType(String nts) {

    setHeaderProperty(HEADER_PROPERTY_NOTIFICATION_SUB_TYPE, nts);
  }

  /**
   * This method gets the {@link #HEADER_PROPERTY_NOTIFICATION_TYPE NT}.
   * 
   * @return the notification-type or <code>null</code> if NOT set.
   */
  public String getNotificationType() {

    return getHeaderProperty(HEADER_PROPERTY_NOTIFICATION_TYPE);
  }

  /**
   * This method sets the {@link #HEADER_PROPERTY_NOTIFICATION_TYPE NT}.
   * 
   * @param notificationType
   *        the notification-type to set.
   */
  public void setNotificationType(String notificationType) {

    setHeaderProperty(HEADER_PROPERTY_NOTIFICATION_TYPE, notificationType);
  }

  /**
   * This method gets the {@link #HEADER_PROPERTY_UNIQUE_SERVICE_NAME USN}.
   * 
   * @return the unique-service-name or <code>null</code> if NOT set.
   */
  public String getUniqueServiceName() {

    return getHeaderProperty(HEADER_PROPERTY_UNIQUE_SERVICE_NAME);
  }

  /**
   * This method sets the {@link #HEADER_PROPERTY_UNIQUE_SERVICE_NAME USN}.
   * 
   * @param usn
   *        is the unique-service-name to set.
   */
  public void setUniqueServiceName(String usn) {

    setHeaderProperty(HEADER_PROPERTY_UNIQUE_SERVICE_NAME, usn);
  }

  /**
   * This method sets the product for the {@link #HEADER_PROPERTY_SERVER SERVER}
   * property.
   * 
   * @param productName
   *        is the name of the product specified by the vendor.
   * @param productVersion
   *        is the version of the product specified by the vendor.
   */
  public void setServerProduct(String productName, String productVersion) {

    StringBuffer buffer = new StringBuffer();
    buffer.append(System.getProperty("os.name"));
    buffer.append('/');
    buffer.append(System.getProperty("os.version"));
    buffer.append(" UPnP/1.0 ");
    buffer.append(productName);
    buffer.append('/');
    buffer.append(productVersion);
    setHeaderProperty(HEADER_PROPERTY_SERVER, buffer.toString());
  }

}
