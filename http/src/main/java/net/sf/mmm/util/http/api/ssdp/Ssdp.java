/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.ssdp;

import java.net.URI;

import net.sf.mmm.util.http.api.HttpVersion;
import net.sf.mmm.util.http.api.upnp.UpnpVersion;

/**
 * This is the interface for the <a href="https://tools.ietf.org/html/draft-cai-ssdp-v1-03">Simple Service Discovery
 * Protocol (SSDP)</a> as defined by {@link UpnpVersion UPnP}. It acts as
 *
 * @author hohwille
 * @since 8.5.0
 */
public interface Ssdp {

  /**
   * @return the {@link HttpVersion}.
   */
  HttpVersion getHttpVersion();

  /**
   * @return the {@link SsdpVersion}.
   */
  SsdpVersion getSsdpVersion();

  /**
   * @return the {@link UpnpVersion}.
   */
  UpnpVersion getUpnpVersion();

  /**
   * @param method the {@link SsdpRequest#getMethod() method}.
   * @param uri the {@link SsdpRequest#getUri() request URI}.
   * @return the new {@link SsdpRequest} as specified.
   */
  SsdpRequest request(String method, URI uri);

  /**
   * {@link #request(String, URI) Creates} a new {@link SsdpRequest#METHOD_NOTIFY NOTIFY} {@link SsdpRequest}.
   *
   * @return the new {@link SsdpRequest} as specified.
   */
  default SsdpRequest NOTIFY() {

    return request(SsdpRequest.METHOD_NOTIFY, SsdpRequest.SSDP_DEFAULT_URI);
  }

  /**
   * {@link #request(String, URI) Creates} a new {@link SsdpRequest#METHOD_M_SEARCH M-SEARCH} {@link SsdpRequest}.
   *
   * @return this instance for fluent API calls.
   */
  default SsdpRequest MSEARCH() {

    return request(SsdpRequest.METHOD_M_SEARCH, SsdpRequest.SSDP_DEFAULT_URI);
  }

  static Ssdp create() {

    return SsdpFactory.SSDP_1_0_UPNP_1_1;
  }

  static Ssdp version(SsdpVersion ssdpVersion, UpnpVersion upnpVersion, HttpVersion httpVersion) {

    return SsdpFactory.of(ssdpVersion, upnpVersion, httpVersion);
  }

}
