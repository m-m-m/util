/* $Id$
 * Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.upnp.ssdp.api;

import net.sf.mmm.util.StringParser;
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
  public static final String SSDP_URI = "*";

  /** the SSDP multicast address */
  public static final String MULTICAST_ADDRESS = "239.255.255.250";

  /** the SSDP multicast port */
  public static final int MULTICAST_PORT = 1900;

  /** device available (register advertisement) */
  public static final String NTS_SSDP_ALIVE = "ssdp:alive";

  /** device unavailable (de-register advertisement) */
  public static final String NTS_SSDP_BYEBYE = "ssdp:byebye";

  /** proper man header */
  public static final String MAN_SSDP_DISCOVER = "\"ssdp:discover\"";

  /** st header */
  public static final String ST_SSDP_ALL = "ssdp:all";

  /**
   * the default value for {@link #setCacheControlMaxAge(int) max-age}: 1800
   * seconds (30 minutes).
   */
  public static final int MAX_AGE_DEFAULT = 30 * 60;

  /**
   * The identifier for the root-device.
   * 
   * @see #HEADER_PROPERTY_NOTIFICATION_TYPE
   * @see #HEADER_PROPERTY_UNIQUE_SERVICE_NAME
   */
  public static final String UPNP_ROOT_DEVICE = "upnp:rootdevice";

  /**
   * The prefix for the UUID.
   * 
   * @see #HEADER_PROPERTY_NOTIFICATION_TYPE
   * @see #HEADER_PROPERTY_UNIQUE_SERVICE_NAME
   */
  public static final String PREFIX_UUID = "uuid:";

  /**
   * The prefix for the device followed by <code>deviceType</code>:<code>version</code>.
   * 
   * @see #HEADER_PROPERTY_NOTIFICATION_TYPE
   * @see #HEADER_PROPERTY_UNIQUE_SERVICE_NAME
   */
  public static final String PREFIX_DEVICE = "urn:schemas-upnp-org:device:";

  /**
   * The prefix for the service followed by <code>serviceType</code>:<code>version</code>.
   * 
   * @see #HEADER_PROPERTY_NOTIFICATION_TYPE
   * @see #HEADER_PROPERTY_UNIQUE_SERVICE_NAME
   */
  public static final String PREFIX_SERVICE = "urn:schemas-upnp-org:service:";

  /**
   * The separator for the {@link #HEADER_PROPERTY_UNIQUE_SERVICE_NAME USN}.
   */
  public static final String USN_SEPARATOR = "::";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>NT</code>.
   */
  public static final String HEADER_PROPERTY_NOTIFICATION_TYPE = "NT";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>NTS</code>.
   * Yes, it is NOT <code>NST</code> - this is correct.
   */
  public static final String HEADER_PROPERTY_NOTIFICATION_SUB_TYPE = "NTS";

  /**
   * The {@link #getHeaderProperty(String) header-property} <code>USN</code>.
   * The value must be in the following form:<br>
   * 
   * <pre>
   * {@link #PREFIX_UUID uuid:}<i>UUID</i>[{@link #USN_SEPARATOR ::}<i>suffix</i>]
   * </pre>
   * 
   * where <code>UUID</code> is {@link java.util.UUID UUID} of the offered
   * device or service and the <code>suffix</code> is one of
   * {@link #UPNP_ROOT_DEVICE}, {@link #PREFIX_DEVICE}, or
   * {@link #PREFIX_SERVICE}.
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
   * The constructor. 
   * 
   * @see #initializeDefaults()
   */
  public SsdpRequest() {

    super();
  }

  /**
   * This methods initializes the default settings for a typical SSDP request.
   */
  public void initializeDefaults() {

    setMethod(METHOD_NOTIFY);
    setUri(SSDP_URI);
    setHeaderProperty(HEADER_PROPERTY_HOST, MULTICAST_ADDRESS + ":" + MULTICAST_PORT);
    setNotificationSubType(NTS_SSDP_ALIVE);
    setCacheControlMaxAge(1800);
  }

  /**
   * {@inheritDoc}
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

  /**
   * This method gets the product-name from the {@link #HEADER_PROPERTY_SERVER}.
   * 
   * @return the product-name or <code>null</code> if NOT set/available.
   */
  public String getServerProductName() {

    // the SSDP specification is very un precise here:
    // we assume that names do NOT contain the character '/'
    // and versions do NOT contain the whitespace character ' '.
    String serverText = getHeaderProperty(HEADER_PROPERTY_SERVER);
    if (serverText != null) {
      StringParser parser = new StringParser(serverText);
      boolean okay = parser.skipOver(" UPnP/", true);
      if (okay) {
        okay = parser.skipUntil(' ');
        if (okay) {
          return parser.readUntil('/', false);
        }
      }
    }
    return null;
  }

  /**
   * This method gets the product-version from the
   * {@link #HEADER_PROPERTY_SERVER}.
   * 
   * @return the product-version or <code>null</code> if NOT set/available.
   */
  public String getServerProductVersion() {

    // the SSDP specification is very un precise here:
    // we assume that names do NOT contain the character '/'
    // and versions do NOT contain the whitespace character ' '.
    String serverText = getHeaderProperty(HEADER_PROPERTY_SERVER);
    if (serverText != null) {
      StringParser parser = new StringParser(serverText);
      boolean okay = parser.skipOver(" UPnP/", true);
      if (okay) {
        okay = parser.skipUntil(' ');
        if (okay) {
          okay = parser.skipUntil('/');
          if (okay) {
            return parser.read(Integer.MAX_VALUE).trim();
          }
        }
      }
    }
    return null;
  }

}
