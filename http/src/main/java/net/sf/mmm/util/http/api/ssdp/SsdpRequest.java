/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.ssdp;

import java.net.URI;

import net.sf.mmm.util.http.api.HttpRequest;
import net.sf.mmm.util.http.api.UriHelper;
import net.sf.mmm.util.version.api.NameVersion;

/**
 * This is the interface for an {@link SsdpRequest}. It is the {@link HttpRequest} send from a client as multicast
 * request via UDP.
 *
 * @author hohwille
 * @since 8.4.0
 */
public interface SsdpRequest extends HttpRequest, SsdpMessage {

  /** The default URI for {@link SsdpRequest}s. */
  URI SSDP_DEFAULT_URI = UriHelper.newUri("*");

  /** The NOTIFY {@link #getMethod() method} of an {@link SsdpRequest}. */
  String METHOD_NOTIFY = "NOTIFY";

  /** The M-SEARCH {@link #getMethod() method} of an {@link SsdpRequest}. */
  String METHOD_M_SEARCH = "M-SEARCH";

  /** The SUBSCRIBE {@link #getMethod() method} of an {@link SsdpRequest}. */
  String METHOD_SUBSCRIBE = "SUBSCRIBE";

  /** the SSDP multicast address for IPv4. */
  String MULTICAST_IPV4_ADDRESS = "239.255.255.250"; // NOSONAR This IP is specified by SSDP

  /** the SSDP multicast address for IPv6. */
  String MULTICAST_IPV6_ADDRESS = ""; // NOSONAR This IP is specified by SSDP

  /** the SSDP multicast port */
  int MULTICAST_PORT = 1900;

  @Override
  SsdpRequest product(NameVersion product);

}
