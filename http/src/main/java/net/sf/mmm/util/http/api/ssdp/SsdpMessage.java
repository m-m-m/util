/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.ssdp;

import java.net.URI;

import net.sf.mmm.util.http.api.HttpMessage;
import net.sf.mmm.util.http.api.UriHelper;
import net.sf.mmm.util.version.api.NameVersion;

/**
 * This is the interface for an {@link SsdpMessage}. It is a {@link HttpMessage} for {@link Ssdp}.
 *
 * @see SsdpRequest
 * @see SsdpResponse
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface SsdpMessage extends HttpMessage {

  /** The default URI for {@link SsdpMessage}s. */
  URI SSDP_DEFAULT_URI = UriHelper.newUri("*");

  /** the SSDP multicast address for IPv4. */
  String MULTICAST_IPV4_ADDRESS = "239.255.255.250"; // NOSONAR This IP is specified by SSDP

  /** the SSDP multicast address for IPv6. */
  String MULTICAST_IPV6_ADDRESS = ""; // NOSONAR This IP is specified by SSDP

  /** the SSDP multicast port */
  int MULTICAST_PORT = 1900;

  Ssdp getSsdp();

  /**
   * @param product the {@link NameVersion} for the SSDP/UPnP product (client or server).
   * @return this object itself for fluent API calls.
   */
  SsdpMessage product(NameVersion product);

}
