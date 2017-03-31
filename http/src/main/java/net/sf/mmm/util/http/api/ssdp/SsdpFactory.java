/* Copyright (c) The m-m-m Team, Licensed under the Apache License, Version 2.0
 * http://www.apache.org/licenses/LICENSE-2.0 */
package net.sf.mmm.util.http.api.ssdp;

import java.net.URI;

import net.sf.mmm.util.http.api.HttpVersion;
import net.sf.mmm.util.http.api.upnp.UpnpVersion;

/**
 * TODO: this class ...
 *
 * @author hohwille
 * @since 8.5.0
 */
class SsdpFactory implements Ssdp {

  static final SsdpFactory SSDP_1_0_UPNP_1_0 = new SsdpFactory(SsdpVersion.SSDP_1_0, UpnpVersion.UPNP_1_0, HttpVersion.HTTP_1_1);

  static final SsdpFactory SSDP_1_0_UPNP_1_1 = new SsdpFactory(SsdpVersion.SSDP_1_0, UpnpVersion.UPNP_1_1, HttpVersion.HTTP_1_1);

  private final SsdpVersion ssdpVersion;

  private final UpnpVersion upnpVersion;

  private final HttpVersion httpVersion;

  /**
   * The constructor.
   */
  private SsdpFactory(SsdpVersion ssdpVersion, UpnpVersion upnpVersion, HttpVersion httpVersion) {
    super();
    this.ssdpVersion = ssdpVersion;
    this.upnpVersion = upnpVersion;
    this.httpVersion = httpVersion;
  }

  @Override
  public SsdpVersion getSsdpVersion() {

    return this.ssdpVersion;
  }

  @Override
  public UpnpVersion getUpnpVersion() {

    return this.upnpVersion;
  }

  @Override
  public HttpVersion getHttpVersion() {

    return this.httpVersion;
  }

  @Override
  public SsdpRequest request(String method, URI uri) {

    return new SsdpRequestImpl(this, method, uri);
  }

  public static SsdpFactory of(SsdpVersion ssdpVersion, UpnpVersion upnpVersion, HttpVersion httpVersion) {

    return new SsdpFactory(ssdpVersion, upnpVersion, httpVersion);
  }

}
